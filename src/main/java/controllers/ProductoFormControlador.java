package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Categoria;
import models.Productos;
import service.ProductoService;
import service.ProductoServiceJdbcImplement;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/productos/form")
public class ProductoFormControlador extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Presentamos el formulario
        //necesitamos la conexión
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImplement(conn);
        req.setAttribute("categorias", service.listarCategorias());
        Long id;
        try {
            id = Long.parseLong(req.getParameter("idProducto"));
        } catch (NumberFormatException e) {
            id=0L;
        }
        Productos productos= new Productos();
        productos.setCategoria(new Categoria());
        if(id>0){
            Optional<Productos> o = service.agregarPorId(id);
            if (o.isPresent()) {
                productos = o.get();
            }
        }
        req.setAttribute("productos", productos);
        getServletContext().getRequestDispatcher("/formularioProducto.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImplement(conn);
        String nombre = req.getParameter("nombre");
        Double precio;
        try {
            precio = Double.valueOf(req.getParameter("precio"));
        }catch (NumberFormatException e){
            precio=0.0;
        }
        Long idCategoria;
        try {
            idCategoria = Long.valueOf(req.getParameter("categoria"));
        }catch (NumberFormatException e){
            idCategoria=0L;
        }
        //Voy a obtener el idProducto
        Long idProducto;
        try {
            idProducto=Long.valueOf(req.getParameter("idProducto"));
        }catch (NumberFormatException e){
            idProducto=0L;
        }
        Productos productos= new Productos();
        productos.setIdProducto(idProducto);
        productos.setNombre(nombre);
        Categoria categoria= new Categoria();
        categoria.setIdCategoria(idCategoria);
        productos.setCategoria(categoria);
        productos.setPrecio(precio);
        service.guardar(productos);
        //Redireccionar a un listado para que no ejecute el metodo doPost
        //nuevamente y se guarde los datos duplicados
        resp.sendRedirect(req.getContextPath()+"/productos");
    }
}
