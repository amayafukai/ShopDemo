<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý sản phẩm</title>
    <link rel="stylesheet" href="CSS/style.css">
</head>
<body>
    <div class="container">
        <h1>Quản lý sản phẩm</h1>
        
        <% if (request.getParameter("success") != null) { %>
            <div class="alert alert-success"><%= request.getParameter("success") %></div>
        <% } %>
        
        <h2>Thêm sản phẩm mới</h2>
        <form action="AdminServlet" method="post">
            <input type="hidden" name="action" value="addproduct">
            
            <div class="form-group">
                <label for="name">Tên sản phẩm:</label>
                <input type="text" id="name" name="name" required>
            </div>
            
            <div class="form-group">
                <label for="description">Mô tả:</label>
                <textarea id="description" name="description" required></textarea>
            </div>
            
            <div class="form-group">
                <label for="price">Giá:</label>
                <input type="number" id="price" name="price" step="0.01" required>
            </div>
            
            <div class="form-group">
                <label for="quantity">Số lượng:</label>
                <input type="number" id="quantity" name="quantity" required>
            </div>
            
            <div class="form-group">
                <label for="imageUrl">URL hình ảnh:</label>
                <input type="text" id="imageUrl" name="imageUrl" required>
            </div>
            
            <button type="submit" class="btn btn-primary">Thêm sản phẩm</button>
        </form>
        
        <h2>Danh sách sản phẩm</h2>
        <%
            List<Product> products = (List<Product>) request.getAttribute("products");
            if (products != null && products.size() > 0) {
        %>
        
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Tên</th>
                <th>Mô tả</th>
                <th>Giá</th>
                <th>Số lượng</th>
                <th>Hành động</th>
            </tr>
            <%
                for (Product product : products) {
            %>
            <tr>
                <td><%= product.getId() %></td>
                <td><%= product.getName() %></td>
                <td><%= product.getDescription() %></td>
                <td><%= product.getPrice() %> VND</td>
                <td><%= product.getQuantity() %></td>
                <td>
                    <a href="AdminServlet?action=editproduct&productId=<%= product.getId() %>">Sửa</a> |
                    <a href="AdminServlet?action=deleteproduct&productId=<%= product.getId() %>" onclick="return confirm('Bạn chắc chắn muốn xóa?')">Xóa</a>
                </td>
            </tr>
            <% 
                }
            %>
        </table>
        
        <% } else { %>
        <p>Không có sản phẩm nào!</p>
        <% } %>
        
        <a href="admin.jsp">Quay lại</a>
    </div>
</body>
</html>