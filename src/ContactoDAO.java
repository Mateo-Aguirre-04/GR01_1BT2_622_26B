import java.util.Locale;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ContactoDAO {

    // Registra un contacto nuevo.
    public Contacto registrarContacto(Contacto contacto) {
        if (contacto == null) {
            throw new IllegalArgumentException("El contacto no puede ser null.");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                contacto.setId(0);
                session.persist(contacto);
                tx.commit();
                return contacto;
            } catch (RuntimeException e) {
                tx.rollback();
                throw e;
            }
        }
    }

    // Muestra todos los contactos registrados.
    public List<Contacto> mostrarContactos() {
        return buscarContactos(null);
    }

    public List<Contacto> buscarContactos(String busqueda) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (busqueda != null && !busqueda.isBlank()) {
                String termino = busqueda.trim().toLowerCase(Locale.ROOT)
                        .replace("!", "!!")
                        .replace("%", "!%")
                        .replace("_", "!_");
                return session.createQuery(
                        "from Contacto c where "
                                + "lower(concat(coalesce(c.nombre, ''), ' ', coalesce(c.apellido, ''))) like :busqueda escape '!' "
                                + "or lower(c.telefono) like :busqueda escape '!' "
                                + "or lower(c.correo) like :busqueda escape '!' "
                                + "or lower(c.direccion) like :busqueda escape '!' "
                                + "order by c.id",
                        Contacto.class)
                        .setParameter("busqueda", "%" + termino + "%")
                        .list();
            }
            return session.createQuery("from Contacto c order by c.id", Contacto.class).list();
        }
    }

    public Contacto buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Contacto.class, id);
        }
    }

    // Edita un contacto existente.
    public boolean editarContacto(Contacto contacto) {
        if (contacto == null) {
            return false;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                if (session.get(Contacto.class, contacto.getId()) == null) {
                    tx.rollback();
                    return false;
                }
                session.merge(contacto);
                tx.commit();
                return true;
            } catch (RuntimeException e) {
                tx.rollback();
                throw e;
            }
        }
    }

    // Elimina un contacto por su ID.
    public boolean eliminarContacto(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Contacto contacto = session.get(Contacto.class, id);
                if (contacto == null) {
                    tx.rollback();
                    return false;
                }
                session.remove(contacto);
                tx.commit();
                return true;
            } catch (RuntimeException e) {
                tx.rollback();
                throw e;
            }
        }
    }
}
