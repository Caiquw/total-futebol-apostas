package dao.dao.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// Classe utilitária do Hibernate — cria e fornece a SessionFactory (equivalente ao ConexaoDB do JDBC)
// A SessionFactory é cara de criar, por isso é singleton (criada uma vez só)
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            // lê as configurações do arquivo hibernate.cfg.xml na raiz do projeto
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void fechar() {
        if (sessionFactory != null) sessionFactory.close();
    }
}
