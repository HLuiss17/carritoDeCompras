package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Categoria;
import repositories.CategoriaRepositoryJdbcImplement;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/productos/agregarCategoria")
public class CategoriaServlet extends HttpServlet {
    private CategoriaRepositoryJdbcImplement categoriaRepo;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Mostrar formulario para agregar nueva categoría
        request.getRequestDispatcher("/agregarCategoria.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombreCategoria = request.getParameter("nombreCategoria");

        // Obtener la conexión proporcionada por el filtro
        Connection conn = (Connection) request.getAttribute("conn");

        if (conn != null) {
            try {
                // Crear y guardar la nueva categoría
                categoriaRepo = new CategoriaRepositoryJdbcImplement(conn);
                Categoria categoria = new Categoria();
                categoria.setNombre(nombreCategoria);
                categoria.setEstado(1); // Por ejemplo, estado activo
                categoriaRepo.guardar(categoria);

                // Redirigir de nuevo al formulario de productos
                response.sendRedirect("/carro_compras/productos/form");
            } catch (Exception e) {
                throw new ServletException("Error al guardar la categoría", e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo obtener la conexión a la base de datos.");
        }
    }
}
