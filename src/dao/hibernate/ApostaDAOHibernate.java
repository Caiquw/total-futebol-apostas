package dao.hibernate;

import dao.interfaces.IApostaDAO;

// Implementação Hibernate de IApostaDAO
// Para ativar: trocar PERSISTENCIA para "HIBERNATE" na DAOFactory
// Requer: hibernate.cfg.xml configurado e anotações @Entity nas classes model
public class ApostaDAOHibernate implements IApostaDAO {
    // TODO: implementar usando Session do HibernateUtil — mesmo padrão do ClubeDAOHibernate
}
