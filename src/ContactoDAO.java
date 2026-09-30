import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactoDAO {
    private final Map<Integer, Contacto> contactos = new HashMap<>();
    private int siguienteId = 1;

    // Registra un contacto nuevo.
    public synchronized Contacto registrarContacto(Contacto contacto) {
        if (contacto == null) {
            throw new IllegalArgumentException("El contacto no puede ser null.");
        }

        Contacto guardado = copiar(contacto);
        guardado.setId(siguienteId++);
        contactos.put(guardado.getId(), guardado);
        return copiar(guardado);
    }

    // Muestra todos los contactos registrados.
    public synchronized List<Contacto> mostrarContactos() {
        List<Contacto> resultado = new ArrayList<>();
        for (Contacto contacto : contactos.values()) {
            resultado.add(copiar(contacto));
        }
        resultado.sort(Comparator.comparingInt(Contacto::getId));
        return resultado;
    }

    public synchronized Contacto buscarPorId(int id) {
        Contacto contacto = contactos.get(id);
        return contacto == null ? null : copiar(contacto);
    }

    // Edita un contacto existente.
    public synchronized boolean editarContacto(Contacto contacto) {
        if (contacto == null || !contactos.containsKey(contacto.getId())) {
            return false;
        }

        contactos.put(contacto.getId(), copiar(contacto));
        return true;
    }

    // Elimina un contacto por su ID.
    public synchronized boolean eliminarContacto(int id) {
        return contactos.remove(id) != null;
    }

    private Contacto copiar(Contacto contacto) {
        Contacto copia = new Contacto(contacto.getNombre(), contacto.getApellido(), contacto.getTelefono(),
                contacto.getCorreo(), contacto.getDireccion(), contacto.getGrupoId());
        copia.setId(contacto.getId());
        return copia;
    }
}