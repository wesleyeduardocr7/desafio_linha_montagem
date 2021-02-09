package aplicacao.negocio.tarefaEntrada;
import aplicacao.dao.tarefaEntrada.TarefaEntradaDAOImpl;
import aplicacao.dominio.TarefaEntrada;
import aplicacao.utils.StringUtils;
import java.util.List;

public class TarefaEntradaNegocioImpl implements TarefaEntradaNegocio {

    private TarefaEntradaDAOImpl tarefaEntradaDAO = new TarefaEntradaDAOImpl();

    @Override
    public  void salvarTarefasDeEntrada(List<String> dadosDeEntrada){

        Integer tempoDeExecucao;

        for (String linhaDoArquivoDeTexto : dadosDeEntrada){

            String linhaSemUnidadeDeTempoDeExecucao = linhaDoArquivoDeTexto.replaceAll("min", "");

            if(linhaSemUnidadeDeTempoDeExecucao.contains("manutenção")){
                tempoDeExecucao = 5;
            }else{
                tempoDeExecucao = Integer.parseInt(StringUtils.retirarCaracteresNaoNumericos(linhaSemUnidadeDeTempoDeExecucao)) ;
            }

            String tarefaSemCaracteresNumericos = StringUtils.retirarCaracteresNumericos(linhaSemUnidadeDeTempoDeExecucao);

            TarefaEntrada tarefa = new TarefaEntrada(tarefaSemCaracteresNumericos,tempoDeExecucao);

            try {
                tarefaEntradaDAO.salvar(tarefa);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public List<TarefaEntrada> recuperar() throws Exception {
        return tarefaEntradaDAO.recuperar();
    }

    @Override
    public void removeTodosOsRegistros(TarefaEntrada tarefaEntrada) throws Exception {
        tarefaEntradaDAO.removeTodosOsRegistrosDaTabelaTarefasDeEntrada(tarefaEntrada);
    }

}
