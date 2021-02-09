package aplicacao.negocio.tarefaSaida;
import aplicacao.dominio.TarefaSaida;
import java.util.List;

public interface TarefaSaidaNegocio {

    void salva(TarefaSaida tarefaSaida) throws Exception;

    List<TarefaSaida> recuperar() throws Exception;

    void gerarLinhasDeMontagem() throws Exception;

    void removeTodosOsRegistros(TarefaSaida tarefaSaida)throws Exception;
}
