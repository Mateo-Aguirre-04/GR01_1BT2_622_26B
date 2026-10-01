import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class GrupoDAO {

    // Crear grupo
    public boolean crearGrupo(Grupo grupo) {
        if (grupo == null) return false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                grupo.setId(0);
                session.persist(grupo);
                tx.commit();
                return true;
            } catch (RuntimeException e) {
                tx.rollback();
                throw e;
            }
        }
    }

    // Listar grupos
    public List<Grupo> listarGrupos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Grupo g order by g.id", Grupo.class).list();
        }
    }

    // Editar grupo
    public boolean editarGrupo(Grupo grupo) {
        if (grupo == null) return false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Grupo existente = session.get(Grupo.class, grupo.getId());
                if (existente == null) {
                    tx.rollback();
                    return false;
                }
                existente.setNombre(grupo.getNombre());
                tx.commit();
                return true;
            } catch (RuntimeException e) {
                tx.rollback();
                throw e;
            }
        }
    }

    // Comprobar si tiene contactos asociados
    private boolean tieneContactosAsociados(Session session, int grupoId) {
        Long total = session.createQuery("select count(c) from Contacto c where c.grupoId = :id", Long.class)
                .setParameter("id", grupoId).uniqueResult();
        return total != null && total > 0;
    }

    // Eliminar grupo validando dependencias (contactoDAO se mantiene por compatibilidad con GrupoServlet)
    public boolean eliminarGrupo(int grupoId, ContactoDAO contactoDAO) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Grupo grupo = session.get(Grupo.class, grupoId);
                if (grupo == null || tieneContactosAsociados(session, grupoId)) {
                    tx.rollback();
                    return false; // Bloquea la eliminación si hay contactos
                }
                session.remove(grupo);
                tx.commit();
                return true;
            } catch (RuntimeException e) {
                tx.rollback();
                throw e;
            }
        }
    }

    // Buscar un grupo por ID (útil para el Servlet)
    public Grupo buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Grupo.class, id);
        }
    }
}
