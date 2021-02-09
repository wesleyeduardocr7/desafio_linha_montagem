package aplicacao.dao.tarefaEntrada;
import aplicacao.dao.generico.GenericDAO;
import aplicacao.dominio.TarefaEntrada;
import java.util.List;

public interface TarefaEntradaDAO extends GenericDAO<TarefaEntrada, Long> {

    List<TarefaEntrada> recuperar() throws Exception;

    TarefaEntrada pesquisaTarefaQueAindaNaoEstaNaListaDeSaidaEComTempoDeExecucaoIgualOuMenorA(Integer tempoDeExecucao) throws Exception;

    boolean tarefaDeEntradaJaExisteNaListaDeTarefasDeSaida(TarefaEntrada tarefaEntrada) throws Exception;
}
