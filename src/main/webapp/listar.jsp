<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*,models.*" %>
<%
    List<Productos> productos = (List<Productos>) request.getAttribute("productos");
    Optional<String> username = (Optional<String>) request.getAttribute("username");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Productos</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">

<h1 class="text-center mb-4">Listado de Productos</h1>

<% if (username.isPresent()) { %>
<div class="alert alert-success" role="alert">
    Hola <%= username.get() %>, bienvenido a la aplicación
</div>
<div class="mb-3">
    <a href="${pageContext.request.contextPath}/productos/form" class="btn btn-primary">Ingrese el producto</a>
</div>
<% } %>

<table class="table table-bordered">
    <thead>
    <tr>
        <th>ID PRODUCTO</th>
        <th>NOMBRE PRODUCTO</th>
        <th>CATEGORIA</th>
        <% if (username.isPresent()) { %>
        <th>PRECIO</th>
        <th>OPCIONES</th>
        <% } %>
    </tr>
    </thead>
    <tbody>
    <% for (Productos p : productos) { %>
    <tr>
        <td><%= p.getIdProducto() %></td>
        <td><%= p.getNombre() %></td>
        <td><%= p.getCategoria() %></td>
        <% if (username.isPresent()) { %>
        <td><%= p.getPrecio() %></td>
        <td>
            <a href="<%= request.getContextPath() %>/agregar-carro?idProducto=<%= p.getIdProducto() %>" class="btn btn-success btn-sm">Agregar</a>
            <a href="<%= request.getContextPath() %>/productos/form?idProducto=<%= p.getIdProducto() %>" class="btn btn-warning btn-sm">Editar</a>
        </td>
        <% } %>
    </tr>
    <% } %>
    </tbody>
</table>

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
