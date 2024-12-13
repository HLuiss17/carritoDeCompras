<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*,models.*" %>
<%
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
    Productos productos = (Productos) request.getAttribute("productos");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Ingreso de Productos</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
<h1 class="mb-4">Formulario de Productos</h1>
<div class="card p-4">
    <form action="<%= request.getContextPath() %>/productos/form" method="post">
        <div class="mb-3">
            <label for="nombre" class="form-label">Ingrese el nombre del producto</label>
            <input type="hidden" name="idProducto" value="<%= productos.getIdProducto() %>">
            <input type="text" id="nombre" name="nombre" class="form-control"
                   value="<%= productos.getNombre() != null ? productos.getNombre() : "" %>" required>
        </div>
        <div class="mb-3">
            <label for="categoria" class="form-label">Ingrese la categoría</label>
            <select name="categoria" id="categoria" class="form-select" required>
                <option value="">---Seleccione la Categoría---</option>
                <% for (Categoria c : categorias) { %>
                <option value="<%= c.getIdCategoria() %>"
                        <%= c.getIdCategoria().equals(productos.getCategoria().getIdCategoria()) ? "selected" : "" %>>
                    <%= c.getNombre() %>
                </option>
                <% } %>
            </select>
        </div>
        <div class="mb-3">
            <label for="precio" class="form-label">Ingrese el precio</label>
            <input type="number" name="precio" id="precio" step="0.01" class="form-control"
                   value="<%= productos.getPrecio() != 0 ? productos.getPrecio() : "" %>" required>
        </div>
        <div class="d-grid">
            <button type="submit" class="btn btn-primary">Enviar</button>
        </div>
    </form>
</div>
<!-- Enlace para agregar una nueva categoría -->
<p class="mt-4">
    ¿No encuentras la categoría que necesitas?
    <a href="<%= request.getContextPath() %>/productos/agregarCategoria" class="btn btn-link">Agregar nueva categoría</a>
</p>
<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
