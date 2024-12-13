package repositories;

import java.sql.SQLException;
import java.util.List;

/*
*<T> Es un parámetro generico que permite que la interfaz se utilizada
* como se desee o cualquier tipo de objeto(entidad) que se desees manejar
* Esto hace que la Interfaz sea flexible y reutilizable
* para cualquier tipo de dato*/
public interface Repository <T>{
    /*
    *El método listar retornaa una lista de objetos de tipo generico T
    *Se usa lara obtener todos los registros de una entidad desde la base de datos
    *  */
    List<T> listar() throws SQLException;
    /*
    * El método para Id recibe identificador único y retorno un objeto de tipo T
    * correspondiente a ese identificador
    * Se usa para buscar un registro específico por su id*/
    T porId(Long id) throws SQLException;
    /*Recibe un objeto de tipo T y lo guarda en la base de datos
    este método puede ser utilizado para crear un registro dependiendo
    si el objeto ya existe en la base de datos
     */
    void guardar(T t) throws SQLException;
    /*
    * Recibe un identificador único y si existe el identoficaador lo borra de la base de datos
    * */
    void eliminar(Long id)throws SQLException;
}
