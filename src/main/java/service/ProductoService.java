package service;

import models.Categoria;
import models.Productos;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Productos> listar();
    //implementamos el metodo para añadir
    Optional<Productos> agregarPorId(Long idProducto);
    //Implementamos los metodos guardar, eliminar y listar de categoria
    void guardar(Productos producto);
    //Metodo eliminar
    void eliminar(Long id);
    //Listaremos al Categoria
    List<Categoria> listarCategorias();
    Optional<Categoria> buscarPorIdCategoria(Long idCategoria);
}
