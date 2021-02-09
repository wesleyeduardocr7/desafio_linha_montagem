package aplicacao.dao.generico;
import org.hibernate.HibernateException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericDAOImpl<T, K extends Serializable> implements GenericDAO<T, K>, Serializable {

    protected static final String ERRO_ACESSO_A_BASE_DADOS = "Ocorreu um erro ao "
            + "acessar a base de dados.";

    protected Class<T> claz;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("desafio_linha_montagem");
    private EntityManager em = emf.createEntityManager();

    public GenericDAOImpl() {
        this.claz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public EntityManager getEntity() {
        return em;
    }

    @Override
    public void salvar(T obj) throws Exception {
        try {
            iniciaTransacao();
            em.persist(obj);
            confirmaTransacao();
        } catch (HibernateException e) {
            throw new Exception(ERRO_ACESSO_A_BASE_DADOS, e);
        }
    }

    @Override
    public List<T> recuperar() throws Exception {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(claz));
            return em.createQuery(cq).getResultList();
        } catch (HibernateException e) {
            throw new Exception(ERRO_ACESSO_A_BASE_DADOS, e);
        }
    }

    @Override
    public void removeTodosOsRegistrosDaTabelaTarefasDeEntrada(T obj){
        String nomeDaTabela = obj.getClass().getSimpleName();
        iniciaTransacao();
        String hql = "DELETE FROM " + nomeDaTabela;
        Query query = getEntity().createQuery(hql);
        query.executeUpdate();
        confirmaTransacao();
    }

    private void iniciaTransacao() {
        em.getTransaction().begin();
    }

    private void confirmaTransacao() {
        em.getTransaction().commit();
    }
}
