package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Order;
import model.Product;
import model.User;
import util.DBConnection;

public class AdminServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        // Kiểm tra quyền admin
        if (user == null || !isAdmin(user.getId())) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("products".equals(action)) {
            getAllProducts(request, response);
        } else if ("users".equals(action)) {
            getAllUsers(request, response);
        } else if ("orders".equals(action)) {
            getAllOrders(request, response);
        } else if ("editproduct".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            getProductById(request, response, productId);
        } else {
            request.getRequestDispatcher("admin.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || !isAdmin(user.getId())) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("addproduct".equals(action)) {
            addProduct(request, response);
        } else if ("updateproduct".equals(action)) {
            updateProduct(request, response);
        } else if ("deleteproduct".equals(action)) {
            deleteProduct(request, response);
        } else if ("updateorderstatus".equals(action)) {
            updateOrderStatus(request, response);
        }
    }

    private void getAllProducts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> products = new ArrayList<>();
        
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM products";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getString("image_url")
                );
                products.add(product);
            }
            
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.setAttribute("products", products);
        request.getRequestDispatcher("adminproducts.jsp").forward(request, response);
    }

    private void getAllUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = new ArrayList<>();
        
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM users";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("fullname"),
                    rs.getString("address"),
                    rs.getString("phone")
                );
                users.add(user);
            }
            
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.setAttribute("users", users);
        request.getRequestDispatcher("adminusers.jsp").forward(request, response);
    }

    private void getAllOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Order> orders = new ArrayList<>();
        
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM orders ORDER BY order_date DESC";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
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
        request.getRequestDispatcher("adminorders.jsp").forward(request, response);
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String imageUrl = request.getParameter("imageUrl");
        
        try {
            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO products (name, description, price, quantity, image_url) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, quantity);
            pstmt.setString(5, imageUrl);
            pstmt.executeUpdate();
            
            conn.close();
            response.sendRedirect("AdminServlet?action=products&success=Thêm sản phẩm thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String imageUrl = request.getParameter("imageUrl");
        
        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE products SET name = ?, description = ?, price = ?, quantity = ?, image_url = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, quantity);
            pstmt.setString(5, imageUrl);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
            
            conn.close();
            response.sendRedirect("AdminServlet?action=products&success=Cập nhật sản phẩm thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("productId"));
        
        try {
            Connection conn = DBConnection.getConnection();
            String query = "DELETE FROM products WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            
            conn.close();
            response.sendRedirect("AdminServlet?action=products&success=Xóa sản phẩm thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateOrderStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String status = request.getParameter("status");
        
        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE orders SET status = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, status);
            pstmt.setInt(2, orderId);
            pstmt.executeUpdate();
            
            conn.close();
            response.sendRedirect("AdminServlet?action=orders&success=Cập nhật trạng thái đơn hàng thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getProductById(HttpServletRequest request, HttpServletResponse response,
                                int productId)
            throws ServletException, IOException {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM products WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getString("image_url")
                );
                request.setAttribute("product", product);
            }
            
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("editproduct.jsp").forward(request, response);
    }
}
