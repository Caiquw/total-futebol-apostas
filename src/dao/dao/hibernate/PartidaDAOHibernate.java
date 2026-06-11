package dao.dao.hibernate;

import dao.interfaces.IPartidaDAO;

// Implementação Hibernate de IPartidaDAO
// Para ativar: trocar PERSISTENCIA para "HIBERNATE" na DAOFactory
// Requer: hibernate.cfg.xml configurado e anotações @Entity nas classes model
public class PartidaDAOHibernate implements IPartidaDAO {
    // TODO: implementar usando Session do HibernateUtil — mesmo padrão do ClubeDAOHibernate
}
