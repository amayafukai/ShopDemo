package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cart;
import model.Order;
import util.DBConnection;

public class OrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        if ("myorders".equals(action)) {
            getMyOrders(request, response, userId);
        } else if ("vieworder".equals(action)) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            viewOrderDetails(request, response, orderId, userId);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        if ("placeorder".equals(action)) {
            placeOrder(request, response, session, userId);
        }
    }

    private void placeOrder(HttpServletRequest request, HttpServletResponse response,
                            HttpSession session, Integer userId)
            throws ServletException, IOException {
        String shippingAddress = request.getParameter("shippingAddress");
        Cart cart = (Cart) session.getAttribute("cart");
        
        if (cart == null || cart.getItems().isEmpty()) {
            request.setAttribute("error", "Giỏ hàng trống!");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }
        
        try {
            Connection conn = DBConnection.getConnection();
            
            // Lưu đơn hàng
            String insertOrderQuery = "INSERT INTO orders (user_id, total_price, status, order_date, shipping_address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertOrderPstmt = conn.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS);
            insertOrderPstmt.setInt(1, userId);
            insertOrderPstmt.setDouble(2, cart.getTotalPrice());
            insertOrderPstmt.setString(3, "pending");
            insertOrderPstmt.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            insertOrderPstmt.setString(5, shippingAddress);
            insertOrderPstmt.executeUpdate();
            
            ResultSet generatedKeys = insertOrderPstmt.getGeneratedKeys();
            int orderId = 0;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            }
            
            // Lưu chi tiết đơn hàng
            String insertDetailQuery = "INSERT INTO order_details (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
            PreparedStatement insertDetailPstmt = conn.prepareStatement(insertDetailQuery);
            
            for (Cart.CartItem item : cart.getItems().values()) {
                insertDetailPstmt.setInt(1, orderId);
                insertDetailPstmt.setInt(2, item.getProduct().getId());
                insertDetailPstmt.setInt(3, item.getQuantity());
                insertDetailPstmt.setDouble(4, item.getProduct().getPrice());
                insertDetailPstmt.executeUpdate();
            }
            
            conn.close();
            
            // Xóa giỏ hàng
            cart.clear();
            session.setAttribute("cart", cart);
            
            request.setAttribute("success", "Đặt hàng thành công! Mã đơn hàng: " + orderId);
            request.getRequestDispatcher("order.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi đặt hàng!");
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
        }
    }

    private void getMyOrders(HttpServletRequest request, HttpServletResponse response,
                             Integer userId)
            throws ServletException, IOException {
        List<Order> orders = new ArrayList<>();
        
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    null,
                    rs.getDouble("total_price"),
                    rs.getString("status"),
                    rs.getTimestamp("order_date"),
                    rs.getString("shipping_address")
                );
                orders.add(order);
            }
            
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("order.jsp").forward(request, response);
    }

    private void viewOrderDetails(HttpServletRequest request, HttpServletResponse response,
                                  int orderId, Integer userId)
            throws ServletException, IOException {
        try {
            Connection conn = DBConnection.getConnection();
            
            // Lấy thông tin đơn hàng
            String orderQuery = "SELECT * FROM orders WHERE id = ? AND user_id = ?";
            PreparedStatement orderPstmt = conn.prepareStatement(orderQuery);
            orderPstmt.setInt(1, orderId);
            orderPstmt.setInt(2, userId);
            ResultSet orderRs = orderPstmt.executeQuery();
            
            if (orderRs.next()) {
                Order order = new Order(
                    orderRs.getInt("id"),
                    orderRs.getInt("user_id"),
                    null,
                    orderRs.getDouble("total_price"),
                    orderRs.getString("status"),
                    orderRs.getTimestamp("order_date"),
                    orderRs.getString("shipping_address")
                );
                
                // Lấy chi tiết đơn hàng
                String detailQuery = "SELECT * FROM order_details WHERE order_id = ?";
                PreparedStatement detailPstmt = conn.prepareStatement(detailQuery);
                detailPstmt.setInt(1, orderId);
                ResultSet detailRs = detailPstmt.executeQuery();
                
                request.setAttribute("order", order);
                request.setAttribute("orderDetails", detailRs);
            }
            
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("orderdetail.jsp").forward(request, response);
    }
}