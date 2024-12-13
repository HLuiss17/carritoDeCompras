<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" import="models.*" %>
<%
    Carro carro = (Carro) session.getAttribute("carro");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Carro de compras</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
<h1 class="mb-4">Carro de compras</h1>
<%
    if (carro == null || carro.getItems().isEmpty()) {
%>
<div class="alert alert-warning" role="alert">
    Lo sentimos, no hay productos en el carro de compras.
</div>
<% } else { %>
<table class="table table-bordered table-striped">
    <thead class="table-dark">
    <tr>
        <th>ID Producto</th>
        <th>Nombre</th>
        <th>Precio</th>
        <th>Cantidad</th>
        <th>Total</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (ItemCarro item : carro.getItems()) {
    %>
    <tr>
        <td><%= item.getProductos().getIdProducto() %></td>
        <td><%= item.getProductos().getNombre() %></td>
        <td><%= item.getProductos().getPrecio() %></td>
        <td><%= item.getCantidad() %></td>
        <td><%= item.getSbtotal() %></td>
    </tr>
    <% } %>
    <tr>
        <td colspan="4" class="text-end fw-bold">Total</td>
        <td class="fw-bold"><%= carro.getTotal() %></td>
    </tr>
    </tbody>
</table>
<% } %>
<div class="mt-4">
    <a href="<%=request.getContextPath()%>/productos" class="btn btn-primary me-2">Seguir comprando</a>
    <a href="<%=request.getContextPath()%>/index.html" class="btn btn-secondary">Ir al inicio</a>
</div>
<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
