<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thanh toán</title>
    <link rel="stylesheet" href="CSS/style.css">
</head>
<body>
    <div class="container">
        <h1>Thanh toán</h1>
        
        <%
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>
        
        <div class="checkout-container">
            <form action="OrderServlet" method="post">
                <input type="hidden" name="action" value="placeorder">
                
                <div class="form-group">
                    <label>Tên khách hàng:</label>
                    <input type="text" value="<%= user.getFullName() %>" disabled>
                </div>
                
                <div class="form-group">
                    <label>Email:</label>
                    <input type="email" value="<%= user.getEmail() %>" disabled>
                </div>
                
                <div class="form-group">
                    <label>Số điện thoại:</label>
                    <input type="text" value="<%= user.getPhone() %>" disabled>
                </div>
                
                <div class="form-group">
                    <label for="shippingAddress">Địa chỉ giao hàng:</label>
                    <textarea id="shippingAddress" name="shippingAddress" required><%= user.getAddress() %></textarea>
                </div>
                
                <button type="submit" class="btn btn-primary">Đặt hàng</button>
                <a href="cart.jsp" class="btn btn-secondary">Quay lại giỏ hàng</a>
            </form>
        </div>
    </div>
</body>
</html>
