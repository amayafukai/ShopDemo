package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cart;
import model.Product;

public class CartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        
        if ("add".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            // Lấy sản phẩm từ database (code đơn giản hóa)
            Product product = new Product(productId, "Product Name", "Description", 100.0, 10, "image.jpg");
            cart.addProduct(product, quantity);
        } else if ("remove".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            cart.removeProduct(productId);
        }
        
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
}