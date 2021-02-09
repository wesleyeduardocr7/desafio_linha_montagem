package aplicacao;
import aplicacao.dominio.TarefaEntrada;
import aplicacao.dominio.TarefaSaida;
import aplicacao.negocio.tarefaEntrada.TarefaEntradaNegocioImpl;
import aplicacao.negocio.tarefaSaida.TarefaSaidaNegocioImpl;
import aplicacao.utils.TxtUtils;
import java.util.List;

public class ProgramaTeste {

    private static TarefaEntradaNegocioImpl tarefaEntradaNegocio = new TarefaEntradaNegocioImpl();
    private static TarefaSaidaNegocioImpl tarefaSaidaNegocio = new TarefaSaidaNegocioImpl();

    public static void main(String[] args) {

        resetaInformacoesParaRealizacaoDeUmNovoTeste();

        try {
            adicionaDadosDeEntradaNoBancoDeDados();
            tarefaSaidaNegocio.gerarLinhasDeMontagem();
            List<TarefaSaida> tarefaSaidas = tarefaSaidaNegocio.recuperar();
            TxtUtils.escreveResultadoEmArquivoDeSaidaTxt(tarefaSaidas);

            System.out.println("\nArquivo de resultado gerado no diretório src/main/resources/saida.txt\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void adicionaDadosDeEntradaNoBancoDeDados(){
        List<String> dadosDeEntrada = TxtUtils.getDadosDeEntrada("src/main/resources/input.txt");
        tarefaEntradaNegocio.salvarTarefasDeEntrada(dadosDeEntrada);
    }

    private static void resetaInformacoesParaRealizacaoDeUmNovoTeste(){
       removeArquivoDeSaida();
       removeTodosOsRegistrosDaTabelaDeDadosDeEntrada();
       removeTodosOsRegistrosDaTabelaDeDadosDeSaida();
    }

    private static void removeTodosOsRegistrosDaTabelaDeDadosDeEntrada(){
        try {
            tarefaEntradaNegocio.removeTodosOsRegistros(new TarefaEntrada());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void removeTodosOsRegistrosDaTabelaDeDadosDeSaida(){
        try {
            tarefaSaidaNegocio.removeTodosOsRegistros(new TarefaSaida());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void removeArquivoDeSaida(){
        if(TxtUtils.arquivoDeSaidaJaExiste()){
            TxtUtils.removeArquivoDeSaida();
        }
    }
}


