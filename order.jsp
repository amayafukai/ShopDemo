<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Order" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đơn hàng của tôi</title>
    <link rel="stylesheet" href="CSS/style.css">
</head>
<body>
    <div class="container">
        <h1>Đơn hàng của tôi</h1>
        
        <% if (request.getAttribute("success") != null) { %>
            <div class="alert alert-success"><%= request.getAttribute("success") %></div>
        <% } %>
        
        <%
            List<Order> orders = (List<Order>) request.getAttribute("orders");
            if (orders != null && orders.size() > 0) {
        %>
        
        <table border="1">
            <tr>
                <th>Mã đơn hàng</th>
                <th>Ngày đặt hàng</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Hành động</th>
            </tr>
            <%
                for (Order order : orders) {
        %>
            <tr>
                <td><%= order.getId() %></td>
                <td><%= order.getOrderDate() %></td>
                <td><%= order.getTotalPrice() %> VND</td>
                <td><%= order.getStatus() %></td>
                <td>
                    <a href="OrderServlet?action=vieworder&orderId=<%= order.getId() %>">Xem chi tiết</a>
                </td>
            </tr>
            <%
                }
            %>
        </table>
        
        <% } else { %>
        <p>Bạn chưa có đơn hàng nào!</p>
        <% } %>
        
        <a href="products.jsp">Tiếp tục mua sắm</a>
    </div>
</body>
</html>