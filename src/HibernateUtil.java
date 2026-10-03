import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

// Punto único de acceso al ORM: una SessionFactory compartida por todos los DAO.
@WebListener
public class HibernateUtil implements ServletContextListener {
    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    @Override
    public void contextDestroyed(ServletContextEvent evento) {
        SESSION_FACTORY.close();
    }
}
