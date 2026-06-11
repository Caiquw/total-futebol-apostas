package dao.dao.hibernate;

import dao.interfaces.IParticipanteDAO;

// Implementação Hibernate de IParticipanteDAO
// Para ativar: trocar PERSISTENCIA para "HIBERNATE" na DAOFactory
// Requer: hibernate.cfg.xml configurado e anotações @Entity nas classes model
public class ParticipanteDAOHibernate implements IParticipanteDAO {
    // TODO: implementar usando Session do HibernateUtil — mesmo padrão do ClubeDAOHibernate
}
