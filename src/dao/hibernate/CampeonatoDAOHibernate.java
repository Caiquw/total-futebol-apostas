package dao.hibernate;

import dao.interfaces.ICampeonatoDAO;

// Implementação Hibernate de ICampeonatoDAO
// Para ativar: trocar PERSISTENCIA para "HIBERNATE" na DAOFactory
// Requer: hibernate.cfg.xml configurado e anotações @Entity nas classes model
public class CampeonatoDAOHibernate implements ICampeonatoDAO {
    // TODO: implementar usando Session do HibernateUtil — mesmo padrão do ClubeDAOHibernate
}
