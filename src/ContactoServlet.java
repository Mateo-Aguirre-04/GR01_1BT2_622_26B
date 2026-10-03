import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/contactos")
public class ContactoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ContactoDAO contactoDAO = new ContactoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("1".equals(request.getParameter("nuevo"))) {
            request.getRequestDispatcher("/WEB-INF/formulario-contacto.jsp").forward(request, response);
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isBlank()) {
            int id = parseId(idParam, response);
            if (id <= 0) {
                return;
            }

            Contacto contacto = contactoDAO.buscarPorId(id);
            if (contacto == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No existe el contacto solicitado.");
                return;
            }
            request.setAttribute("contacto", contacto);
            request.getRequestDispatcher("/WEB-INF/formulario-contacto.jsp").forward(request, response);
            return;
        }

        String busqueda = request.getParameter("busqueda");
        request.setAttribute("busqueda", busqueda == null ? "" : busqueda.trim());
        request.setAttribute("contactos", contactoDAO.buscarContactos(busqueda));
        request.setAttribute("mensaje", request.getParameter("mensaje"));
        request.getRequestDispatcher("/WEB-INF/contactos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        try {
            switch (accion == null ? "" : accion) {
                case "crear":
                    Contacto nuevoContacto = leerContacto(request);
                    if (!contactoValido(nuevoContacto)) {
                        mostrarFormularioConError(request, response, nuevoContacto);
                        return;
                    }
                    contactoDAO.registrarContacto(nuevoContacto);
                    redirigir(response, request, "creado");
                    break;
                case "actualizar":
                    Contacto contacto = leerContacto(request);
                    contacto.setId(parseId(request.getParameter("id"), response));
                    if (contacto.getId() <= 0) {
                        return;
                    }
                    if (!contactoValido(contacto)) {
                        mostrarFormularioConError(request, response, contacto);
                        return;
                    }
                    if (!contactoDAO.editarContacto(contacto)) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "No existe el contacto solicitado.");
                        return;
                    }
                    redirigir(response, request, "actualizado");
                    break;
                case "eliminar":
                    int id = parseId(request.getParameter("id"), response);
                    if (id <= 0) {
                        return;
                    }
                    if (!contactoDAO.eliminarContacto(id)) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "No existe el contacto solicitado.");
                        return;
                    }
                    redirigir(response, request, "eliminado");
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción de contacto no válida.");
            }
        } catch (NumberFormatException exception) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El identificador del contacto no es válido.");
        }
    }

    private Contacto leerContacto(HttpServletRequest request) {
        return new Contacto(request.getParameter("nombre"), request.getParameter("apellido"),
                request.getParameter("telefono"), request.getParameter("correo"),
                request.getParameter("direccion"), null);
    }

    private boolean contactoValido(Contacto contacto) {
        String correo = contacto.getCorreo();
        return contacto.getNombre() != null && !contacto.getNombre().isBlank()
                && contacto.getTelefono() != null && !contacto.getTelefono().isBlank()
                && (correo == null || correo.isBlank() || correo.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+"));
    }

    private void mostrarFormularioConError(HttpServletRequest request, HttpServletResponse response,
            Contacto contacto) throws ServletException, IOException {
        request.setAttribute("contacto", contacto);
        request.setAttribute("errorFormulario", "Ingresa el nombre y el teléfono, y verifica el formato del correo.");
        request.getRequestDispatcher("/WEB-INF/formulario-contacto.jsp").forward(request, response);
    }

    private int parseId(String valor, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(valor);
            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El identificador debe ser positivo.");
                return -1;
            }
            return id;
        } catch (NumberFormatException | NullPointerException exception) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El identificador del contacto no es válido.");
            return -1;
        }
    }

    private void redirigir(HttpServletResponse response, HttpServletRequest request, String mensaje)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/contactos?mensaje=" + mensaje);
    }
}