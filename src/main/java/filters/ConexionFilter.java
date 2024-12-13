package filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import service.ServiceJdbcException;
import utils.Conexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

//Implementamos un anotacion
//Esta anotacion me sirve para poder utilizar la conexión en cualquier parte de mi aplicación
@WebFilter("/*")
public class ConexionFilter implements Filter {
    /*
    *Una clase filter en Java es un objeto que realiza tareas de filtrado en las solicitudes
    * respuestas a un recurso. Los filtros se pueden ejecutar en servidores compatibles con Jakarta EE
    * los filtros interceptan solicitudes y respuestas de manera dinámica para transformar o utilizar
    * la información que contiene. El filtrado se realiza en el metodo doFilter
    * */

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /*
        *request: petición que hace el cliente.
        * response: respuesta del servidor
        * filterchain: clase d efiltro que representa el flujo de procesamiento
        * llama al método chain.doFilter(request, response), dentro de un filtro pasa la solicitud al siguiente filtro o al recurso
        * destino( servlet o un jsp)
        * */

        //Obtener la conexión
        try (Connection conn = Conexion.getConnection()){
            //Verificar si la conexón realiza un autocommit(configuracion automatica para cada instrucción SQL)
            if (conn.getAutoCommit()){
                //Si esta activa desactivamos el autocommit
                conn.setAutoCommit(false);
            }
            try {
                //Agregamos la conexión como un atributo en la solicitud
                //esto permite que otros componentes (servlet o daos) puedan acceeder a la conexión
                // desde el objeto request
                request.setAttribute("conn", conn);
                //pasa la solicitud y la respuesta al siguiente filtro o al recurso
                //destino(sevlet o jsp)
                chain.doFilter(request, response);
                //si el procesamiento se realizo  correctamente sin lanzar excepciones, se confirma
                //la solicitud, y se aplica todos los cambios a la base de datos
                conn.commit();
                //Si ocurre algun error durante el procesamiento (dentro del doFilter)
                //se captura la excepción
            }catch (SQLException | ServiceJdbcException e) {
                //se deshacen los cambios con un rollback y de3 esa forma se mantien la integridad de los datos
                conn.rollback();
                //Enviamos un  codigo dee error HTTP 500 al cliente, indicando un problema interno en el servidor
                ((HttpServletResponse)response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                e.printStackTrace();
            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
