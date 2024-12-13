package repositories;

import models.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepositoryJdbcImplement implements Repository<Categoria> {
    private Connection conn;

    public CategoriaRepositoryJdbcImplement(Connection conn) {
        this.conn = conn;
    }


    @Override
    public Categoria porId(Long idCategoria) throws SQLException {
        Categoria categoria=null;
        try(PreparedStatement stmt = conn.prepareStatement(
                "select * from categoria where idcategoria =?")){
            stmt.setLong(1, idCategoria);
            try(ResultSet rs = stmt.executeQuery()){
                categoria=getCategoria(rs);
            }
        }
        return categoria;
    }

    @Override
    public List<Categoria> listar() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from categoria")){
            while(rs.next()){
                Categoria c = getCategoria(rs);
                categorias.add(c);
            }
        }
        return categorias;
    }


    @Override
    public void guardar(Categoria categoria) throws SQLException {
        String sql;
        if (categoria.getIdCategoria() == null) {
            // Si el id es null, realizamos una inserción
            sql = "INSERT INTO categoria (nombre, estado) VALUES (?, ?)";
        } else {
            // Si el id no es null, realizamos una actualización
            sql = "UPDATE categoria SET nombre = ?, estado = ? WHERE idcategoria = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombre());
            stmt.setInt(2, categoria.getEstado());

            // Si es una actualización, agregamos el idCategoria al final
            if (categoria.getIdCategoria() != null) {
                stmt.setLong(3, categoria.getIdCategoria());
            }

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM categoria WHERE idcategoria = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private static Categoria getCategoria(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setIdCategoria(rs.getLong("idcategoria"));
        c.setNombre(rs.getString("nombre"));
        c.setEstado(rs.getInt("estado"));
        return c;
    }
}
