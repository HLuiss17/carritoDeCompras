package repositories;

import models.Categoria;
import models.Productos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositoryJdbcImplement implements Repository<Productos> {
    /* Necesitamos una conexion a la base de datos , la conexxión se tiene que pasar al repository, luego
    * se lo pasa al Service y a su veez el servlet lo obtiene del objeto request de los atributos
    * que se setearon por request vuelve a oasar al seervice y el service pasa al repository*/
    private Connection conn;
    //Implementamos un contructor para inicializar la conexion


    public ProductoRepositoryJdbcImplement(Connection conn) {
        this.conn = conn;
    }
    //Sobreescribimos los metodos de la clase interfaz

    @Override
    public List<Productos> listar() throws SQLException {
        //Declaramos e inicializamos la lista de Productos
        List<Productos> productos = new ArrayList<>();
        try (Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery("SELECT p.*, c.nombre as categoria FROM producto as p"+
                     " inner join categoria as c ON (p.idcategoria = c.idcategoria) order by p.idproducto ASC")) {
            while (rs.next()){
                Productos p = getProductos(rs);
                productos.add(p);
            }
        }
        return productos;
    }



    @Override
    public Productos porId(Long idProducto) throws SQLException {
        Productos productos = null;
        try(PreparedStatement stnt= conn.prepareStatement("SELECT p.*, c.nombre as categoria FROM " +
                "producto as p inner join categoria as c ON(p.idcategoria=c.idcategoria) WHERE p.idproducto=?")){
            stnt.setLong(1, idProducto);
            try(ResultSet rs = stnt.executeQuery()){
                if(rs.next()){
                    productos = getProductos(rs);
                }
            }
        }
        return productos;
    }

    @Override
    public void guardar(Productos productos) throws SQLException {
        String sql;
        if(productos.getIdProducto()!=null && productos.getIdProducto()>0){
            sql="update producto set idcategoria=?, nombre=?, precio=? where idproducto=?";
        }else{
            sql="INSERT INTO producto(idcategoria, nombre,precio) VALUES(?,?,?)";
        }
        try (PreparedStatement stnt=conn.prepareStatement(sql)){
            stnt.setLong(1, productos.getCategoria().getIdCategoria());
            stnt.setString(2, productos.getNombre());
            stnt.setDouble(3, productos.getPrecio());
            if(productos.getIdProducto()!=null && productos.getIdProducto()>0){
                stnt.setLong(4, productos.getIdProducto());
            }
            stnt.executeUpdate();

        }
    }

    @Override
    public void eliminar(Long idProducto) throws SQLException {
        String sql="delete from productos where idproducto=?";
        try(PreparedStatement stnt=conn.prepareStatement(sql)){
            stnt.setLong(1, idProducto);
            stnt.executeUpdate();
        }
    }
    private static Productos getProductos(ResultSet rs) throws SQLException {
        Productos p = new Productos();
        p.setIdProducto(rs.getLong("idproducto"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getDouble("precio"));
        Categoria c = new Categoria();
        c.setIdCategoria(rs.getLong("idcategoria"));
        c.setNombre(rs.getString("categoria"));
        p.setCategoria(c);
        return p;
    }
}
