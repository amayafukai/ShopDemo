<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Cart" %>
<%@ page import="model.Cart.CartItem" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng</title>
    <link rel="stylesheet" href="CSS/style.css">
</head>
<body>
    <div class="container">
        <h1>Giỏ hàng của bạn</h1>
        <%
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart != null && cart.getItems().size() > 0) {
        %>
        <table border="1">
            <tr>
                <th>Tên sản phẩm</th>
                <th>Giá</th>
                <th>Số lượng</th>
                <th>Tổng</th>
                <th>Hành động</th>
            </tr>
            <%
                for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
                    CartItem item = entry.getValue();
            %>
            <tr>
                <td><%= item.getProduct().getName() %></td>
                <td><%= item.getProduct().getPrice() %> VND</td>
                <td><%= item.getQuantity() %></td>
                <td><%= item.getProduct().getPrice() * item.getQuantity() %> VND</td>
                <td>
                    <a href="CartServlet?action=remove&productId=<%= item.getProduct().getId() %>">Xóa</a>
                </td>
            </tr>
            <%
                }
            %>
        </table>
        <h2>Tổng tiền: <%= cart.getTotalPrice() %> VND</h2>
        <a href="checkout.jsp">Thanh toán</a>
        <% } else { %>
        <p>Giỏ hàng trống!</p>
        <% } %>
        <a href="products.jsp">Tiếp tục mua sắm</a>
    </div>
</body>
</html>
