import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// 1. Pon la ruta aquí arriba
@WebServlet("/grupos") 
public class GrupoServlet extends HttpServlet {
    private final GrupoDAO grupoDAO = GrupoDAO.getInstance();
    private ContactoDAO contactoDAO;

    // Sobrescribir doGet: obtener la lista de grupos desde GrupoDAO, guardarla en el request como "listaGrupos" y despachar a grupos.jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Inicializar ContactoDAO si aún no está inicializado
        if (contactoDAO == null) {
            contactoDAO = new ContactoDAO();
        }

        // Obtener la lista de grupos desde GrupoDAO
        request.setAttribute("listaGrupos", grupoDAO.listarGrupos());

        // Despachar a grupos.jsp
        request.getRequestDispatcher("/WEB-INF/grupos.jsp").forward(request, response);
    }

    // Sobrescribir doPost: obtener el parámetro "action".
    // Si action es "crear", obtener el parámetro "nombre", crear el objeto Grupo, llamar a grupoDAO.crearGrupo y redirigir a "/grupos".
    // Si action es "editar", obtener "id" y "nombre", crear el objeto Grupo, llamar a grupoDAO.editarGrupo y redirigir a "/grupos".
    // Si action es "eliminar", obtener "id", llamar a grupoDAO.eliminarGrupo(id, contactoDAO) y redirigir a "/grupos".
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Inicializar ContactoDAO si aún no está inicializado
        if (contactoDAO == null) {
            contactoDAO = new ContactoDAO();
        }

        String action = request.getParameter("action");
        if ("crear".equals(action)) {
            String nombre = request.getParameter("nombre");
            Grupo grupo = new Grupo();
            grupo.setNombre(nombre);
            grupoDAO.crearGrupo(grupo);
        } else if ("editar".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            Grupo grupo = new Grupo(id, nombre);
            grupoDAO.editarGrupo(grupo);
        } else if ("eliminar".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            grupoDAO.eliminarGrupo(id, contactoDAO);
        }
        response.sendRedirect(request.getContextPath() + "/grupos");
    }
    
}
