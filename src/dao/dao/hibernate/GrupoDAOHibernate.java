package dao.dao.hibernate;

import dao.interfaces.IGrupoDAO;

// Implementação Hibernate de IGrupoDAO
// Para ativar: trocar PERSISTENCIA para "HIBERNATE" na DAOFactory
// Requer: hibernate.cfg.xml configurado e anotações @Entity nas classes model
public class GrupoDAOHibernate implements IGrupoDAO {
    // TODO: implementar usando Session do HibernateUtil — mesmo padrão do ClubeDAOHibernate
}
