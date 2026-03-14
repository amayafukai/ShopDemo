<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ShopDemo - Cửa hàng trực tuyến</title>
    <link rel="stylesheet" href="CSS/style.css">
</head>
<body>
    <div class="container">
        <h1>Chào mừng đến với ShopDemo</h1>
        <p>Cửa hàng bán hàng trực tuyến với các sản phẩm chất lượng cao</p>
        
        <div class="menu">
            <a href="ProductServlet" class="btn btn-primary">Xem sản phẩm</a>
            <a href="UserServlet?action=login" class="btn btn-primary">Đăng nhập</a>
            <a href="UserServlet?action=register" class="btn btn-primary">Đăng ký</a>
        </div>
    </div>
</body>
</html>
