<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bảng điều khiển Admin</title>
    <link rel="stylesheet" href="CSS/style.css">
</head>
<body>
    <div class="container">
        <h1>Bảng điều khiển Admin</h1>
        
        <%
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>
        
        <div class="admin-menu">
            <div class="menu-item">
                <h2><a href="AdminServlet?action=products">Quản lý sản phẩm</a></h2>
                <p>Thêm, sửa, xóa sản phẩm</p>
            </div>
            
            <div class="menu-item">
                <h2><a href="AdminServlet?action=users">Quản lý người dùng</a></h2>
                <p>Xem danh sách người dùng</p>
            </div>
            
            <div class="menu-item">
                <h2><a href="AdminServlet?action=orders">Quản lý đơn hàng</a></h2>
                <p>Xem và cập nhật trạng thái đơn hàng</p>
            </div>
        </div>
        
        <a href="UserServlet?action=logout">Đăng xuất</a>
    </div>
</body>
</html>
