package aplicacao.negocio.tarefaEntrada;
import aplicacao.dominio.TarefaEntrada;

import java.util.List;

public interface TarefaEntradaNegocio {

    void salvarTarefasDeEntrada(List<String> dadosDeEntrada) throws Exception;

    List<TarefaEntrada> recuperar() throws Exception;

    void removeTodosOsRegistros(TarefaEntrada tarefaEntrada)throws Exception;
}
