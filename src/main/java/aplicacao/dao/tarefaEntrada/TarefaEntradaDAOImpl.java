package aplicacao.dao.tarefaEntrada;
import aplicacao.dao.generico.GenericDAOImpl;
import aplicacao.dao.tarefaSaida.TarefaSaidaDAOImpl;
import aplicacao.dominio.TarefaEntrada;
import aplicacao.dominio.TarefaSaida;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

public class TarefaEntradaDAOImpl extends GenericDAOImpl<TarefaEntrada, Long> implements TarefaEntradaDAO {

    private TarefaSaidaDAOImpl tarefaSaidaDAO = new TarefaSaidaDAOImpl();

    @Override
    public TarefaEntrada pesquisaTarefaQueAindaNaoEstaNaListaDeSaidaEComTempoDeExecucaoIgualOuMenorA(Integer tempoDeExecucao) throws Exception {

        TarefaEntrada tarefaEntrada;
        TarefaEntrada tarefaDeEntradaComTempoDeExecucaoIgual = pesquisaTarefaQueAindaNaoEstaNaListaDeSaidaEComTempoDeExecucaoIgualA(tempoDeExecucao);

        if (tarefaDeEntradaComTempoDeExecucaoIgual != null) {
            tarefaEntrada = tarefaDeEntradaComTempoDeExecucaoIgual;
        } else {
            TarefaEntrada tarefaDeEntradaComTempoDeExecucaoMenor = pesquisaTarefaQueAindaNaoEstaNaListaDeSaidaEComTempoDeExecucaoMenor(tempoDeExecucao);
            tarefaEntrada = tarefaDeEntradaComTempoDeExecucaoMenor;
        }

        return tarefaEntrada;
    }

    private TarefaEntrada pesquisaTarefaQueAindaNaoEstaNaListaDeSaidaEComTempoDeExecucaoIgualA(Integer tempoDeExecucao) {

        TarefaEntrada tarefaDeEntradaComTempoDeExecucaoIgual = null;

        String hql = "FROM TarefaEntrada WHERE tempo_execucao = :tempo_execucao";

        Query query = getEntity().createQuery(hql);

        query.setParameter("tempo_execucao", tempoDeExecucao);

        List<TarefaEntrada> listaDeTarefasDeEntradaComTempoDeExecucaoIgual = query.getResultList();

        if (listaDeTarefasDeEntradaComTempoDeExecucaoIgual == null || listaDeTarefasDeEntradaComTempoDeExecucaoIgual.isEmpty()) {
            return null;
        }

        for (int i = 0; i < listaDeTarefasDeEntradaComTempoDeExecucaoIgual.size(); i++) {
            if (tarefaDeEntradaJaExisteNaListaDeTarefasDeSaida(listaDeTarefasDeEntradaComTempoDeExecucaoIgual.get(i))) {
                continue;
            }
            if (listaDeTarefasDeEntradaComTempoDeExecucaoIgual.get(i).getTempoDeExecucao() == tempoDeExecucao) {
                tarefaDeEntradaComTempoDeExecucaoIgual = listaDeTarefasDeEntradaComTempoDeExecucaoIgual.get(i);
                break;
            }
        }

        return tarefaDeEntradaComTempoDeExecucaoIgual;
    }

    private TarefaEntrada pesquisaTarefaQueAindaNaoEstaNaListaDeSaidaEComTempoDeExecucaoMenor(Integer tempoDeExecucao) {

        TarefaEntrada tarefaDeEntradaComTempoDeExecucaoMenor = null;

        String hql = "FROM TarefaEntrada WHERE tempo_execucao < :tempo_execucao";

        Query query = getEntity().createQuery(hql);

        query.setParameter("tempo_execucao", tempoDeExecucao);

        List<TarefaEntrada> listaDeTarefasDeEntradaComTempoDeExecucaoMenor = query.getResultList();

        if (listaDeTarefasDeEntradaComTempoDeExecucaoMenor == null || listaDeTarefasDeEntradaComTempoDeExecucaoMenor.isEmpty()) {
            return null;
        } else {
            Collections.sort(listaDeTarefasDeEntradaComTempoDeExecucaoMenor, Collections.reverseOrder());
        }

        for (int i = 0; i < listaDeTarefasDeEntradaComTempoDeExecucaoMenor.size(); i++) {
            if (tarefaDeEntradaJaExisteNaListaDeTarefasDeSaida(listaDeTarefasDeEntradaComTempoDeExecucaoMenor.get(i))) {
                continue;
            }
            if (listaDeTarefasDeEntradaComTempoDeExecucaoMenor.get(i).getTempoDeExecucao() < tempoDeExecucao) {
                tarefaDeEntradaComTempoDeExecucaoMenor = listaDeTarefasDeEntradaComTempoDeExecucaoMenor.get(i);
                break;
            }
        }

        return tarefaDeEntradaComTempoDeExecucaoMenor;
    }

    @Override
    public boolean tarefaDeEntradaJaExisteNaListaDeTarefasDeSaida(TarefaEntrada tarefaEntrada) {

        try {

            List<TarefaSaida> tarefaSaidas = tarefaSaidaDAO.recuperar();

            for (TarefaSaida tarefaSaida : tarefaSaidas) {
                if (tarefaSaida.getNomeTarefa().equals(tarefaEntrada.getNomeTarefa())) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
