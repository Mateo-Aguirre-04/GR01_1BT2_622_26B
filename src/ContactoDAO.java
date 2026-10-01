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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
