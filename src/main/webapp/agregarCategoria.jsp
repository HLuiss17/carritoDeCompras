<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Agregar Categoría</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
<h1 class="mb-4">Agregar Nueva Categoría</h1>
<form action="<%=request.getContextPath()%>/productos/agregarCategoria" method="post" class="needs-validation" novalidate>
    <div class="mb-3">
        <label for="nombreCategoria" class="form-label">Nombre de la Categoría:</label>
        <input type="text" id="nombreCategoria" name="nombreCategoria" class="form-control" required>
        <div class="invalid-feedback">
            Por favor, ingrese el nombre de la categoría.
        </div>
    </div>
    <button type="submit" class="btn btn-primary">Guardar</button>
</form>
<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
