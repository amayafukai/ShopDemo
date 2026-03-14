<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Order" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý đơn hàng</title>
    <link rel="stylesheet" href="CSS/style.css">
</head>
<body>
    <div class="container">
        <h1>Quản lý đơn hàng</h1>
        
        <% if (request.getParameter("success") != null) { %>
            <div class="alert alert-success"><%= request.getParameter("success") %></div>
        <% } %>
        
        <%
            List<Order> orders = (List<Order>) request.getAttribute("orders");
            if (orders != null && orders.size() > 0) {
        %>
        
        <table border="1">
            <tr>
                <th>Mã đơn</th>
                <th>ID người dùng</th>
                <th>Ngày đặt</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Hành động</th>
            </tr>
            <%
                for (Order order : orders) {
            %>
            <tr>
                <td><%= order.getId() %></td>
                <td><%= order.getUserId() %></td>
                <td><%= order.getOrderDate() %></td>
                <td><%= order.getTotalPrice() %> VND</td>
                <td>
                    <form action="AdminServlet" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="updateorderstatus">
                        <input type="hidden" name="orderId" value="<%= order.getId() %>">
                        <select name="status" onchange="this.form.submit()">
                            <option value="pending" <%= "pending".equals(order.getStatus()) ? "selected" : "" %>>Chờ xử lý</option>
                            <option value="processing" <%= "processing".equals(order.getStatus()) ? "selected" : "" %>>Đang xử lý</option>
                            <option value="shipped" <%= "shipped".equals(order.getStatus()) ? "selected" : "" %>>Đã gửi</option>
                            <option value="delivered" <%= "delivered".equals(order.getStatus()) ? "selected" : "" %>>Đã giao</option>
                        </select>
                    </form>
                </td>
                <td>
                    <a href="OrderServlet?action=vieworder&orderId=<%= order.getId() %>">Xem chi tiết</a>
                </td>
            </tr>
            <% 
                }
            %>
        </table>
        
        <% } else { %>
        <p>Không có đơn hàng nào!</p>
        <% } %>
        
        <a href="admin.jsp">Quay lại</a>
    </div>
</body>
</html>
