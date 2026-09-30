import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrupoDAO {
    private static final GrupoDAO INSTANCIA = new GrupoDAO();

    private final Map<Integer, Grupo> grupos = new HashMap<>();
    private int siguienteId = 1;

    private GrupoDAO() {
    }

    public static GrupoDAO getInstance() {
        return INSTANCIA;
    }

    // Crear grupo
    public synchronized boolean crearGrupo(Grupo grupo) {
        if (grupo == null) return false;
        grupo.setId(siguienteId++);
        grupos.put(grupo.getId(), grupo);
        return true;
    }

    // Listar grupos
    public synchronized List<Grupo> listarGrupos() {
        return new ArrayList<>(grupos.values());
    }

    // Editar grupo
    public synchronized boolean editarGrupo(Grupo grupo) {
        if (grupo == null || !grupos.containsKey(grupo.getId())) {
            return false;
        }
        grupos.put(grupo.getId(), grupo);
        return true;
    }

    // Comprobar si tiene contactos asociados
    private boolean tieneContactosAsociados(int grupoId, ContactoDAO contactoDAO) {
        if (contactoDAO == null) return false;
        
        List<Contacto> listaContactos = contactoDAO.mostrarContactos();
        for (Contacto contacto : listaContactos) {
            // Verificamos si el contacto pertenece al grupo
            if (contacto.getGrupoId() != null && contacto.getGrupoId() == grupoId) {
                return true; 
            }
        }
        return false;
    }

    // Eliminar grupo validando dependencias
    public synchronized boolean eliminarGrupo(int grupoId, ContactoDAO contactoDAO) {
        if (tieneContactosAsociados(grupoId, contactoDAO)) {
            return false; // Bloquea la eliminación si hay contactos
        }
        return grupos.remove(grupoId) != null;
    }
    
    // Método extra para buscar un grupo por ID (útil para el Servlet)
    public synchronized Grupo buscarPorId(int id) {
        return grupos.get(id);
    }
}