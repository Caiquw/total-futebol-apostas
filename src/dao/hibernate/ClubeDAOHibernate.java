package dao.hibernate;

import dao.interfaces.IClubeDAO;
import model.Clube;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

// Implementação Hibernate de IClubeDAO
// O Hibernate mapeia o objeto Clube diretamente para a tabela — sem SQL manual
public class ClubeDAOHibernate implements IClubeDAO {

    @Override
    public void salvar(Clube clube) {
        // session.persist() substitui o INSERT INTO — o Hibernate gera o SQL automaticamente
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(clube);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Erro ao salvar clube (Hibernate): " + e.getMessage());
        }
    }

    @Override
    public List<Clube> listarTodos() {
        // HQL (Hibernate Query Language) — parecido com SQL mas usa o nome da classe Java, não da tabela
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Clube", Clube.class).list();
        } catch (Exception e) {
            System.out.println("Erro ao listar clubes (Hibernate): " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public Clube buscarPorNome(String nome) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Clube WHERE nome = :nome", Clube.class)
                    .setParameter("nome", nome)
                    .uniqueResult();
        } catch (Exception e) {
            System.out.println("Erro ao buscar clube (Hibernate): " + e.getMessage());
            return null;
        }
    }

    @Override
    public int buscarId(String nome) {
        // No Hibernate geralmente usa-se o próprio objeto, mas mantemos buscarId para compatibilidade com os outros DAOs
        Clube c = buscarPorNome(nome);
        return c != null ? c.getId() : -1;
    }
}
