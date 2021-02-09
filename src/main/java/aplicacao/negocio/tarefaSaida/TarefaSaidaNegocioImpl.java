package aplicacao.negocio.tarefaSaida;
import aplicacao.dao.tarefaEntrada.TarefaEntradaDAOImpl;
import aplicacao.dao.tarefaSaida.TarefaSaidaDAOImpl;
import aplicacao.dominio.TarefaEntrada;
import aplicacao.dominio.TarefaSaida;
import aplicacao.utils.TarefaEntradaUtils;
import aplicacao.utils.TarefaSaidaUtils;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaSaidaNegocioImpl implements TarefaSaidaNegocio {

    private TarefaSaidaDAOImpl tarefaSaidaDAO = new TarefaSaidaDAOImpl();
    private TarefaEntradaDAOImpl tarefaEntradaDAO = new TarefaEntradaDAOImpl();

    @Override
    public void salva(TarefaSaida tarefaSaida) throws Exception {
       tarefaSaidaDAO.salvar(tarefaSaida);
    }

    @Override
    public List<TarefaSaida> recuperar() throws Exception {
        return tarefaSaidaDAO.recuperar();
    }

    @Override
    public void gerarLinhasDeMontagem() throws Exception {

        List<TarefaEntrada> tarefasDeEntrada = tarefaEntradaDAO.recuperar();
        LocalDateTime horarioAtualDoDia = TarefaEntradaUtils.getInicioDoTurnoMatutino();
        LocalDateTime horarioDeTerminoDaTarefa;
        List<TarefaEntrada> tarefasDeEntradaPendentes = new ArrayList<>();
        boolean almocoFoiAdicionado = false;

        if (tarefasDeEntrada.isEmpty()){
            throw new Exception("Não Existe Tarefas Adicionadas");
        }

        for(TarefaEntrada tarefaEntrada : tarefasDeEntrada){

            horarioDeTerminoDaTarefa = horarioAtualDoDia.plusMinutes(tarefaEntrada.getTempoDeExecucao());

            if(tarefaDeEntradaJaExisteNaListaDeTArefasDeSaida(tarefaEntrada)){
                continue;
            }

            if(almocoFoiAdicionado && !tarefasDeEntradaPendentes.isEmpty()){
                for(TarefaEntrada tarefaEntradaPendente : tarefasDeEntradaPendentes){
                    salvarTarefaDeEntradaComoTarefaDeSaida(tarefaEntradaPendente,horarioAtualDoDia);
                    horarioAtualDoDia = horarioAtualDoDia.plusMinutes(tarefaEntradaPendente.getTempoDeExecucao());
                }
                tarefasDeEntradaPendentes = new ArrayList<>();
            }

            if(horarioAtualDoDiaEhIgualAoHorarioDeInicioDoAlmoco(horarioAtualDoDia)  && !almocoFoiAdicionado){
                adicionaAlmocoComoTarefaDeSaida(horarioAtualDoDia);
                horarioAtualDoDia = horarioAtualDoDia.plusMinutes(TarefaSaidaUtils.quantidadeDeMinutosParaOAlmoco());
                almocoFoiAdicionado = true;
                tarefasDeEntradaPendentes.add(tarefaEntrada);
                continue;
            }

            if(horarioDeTerminoDaTarefaEhIgualAoHorarioDeInicioDoAlmoco(horarioDeTerminoDaTarefa) && !almocoFoiAdicionado ){
                salvarTarefaDeEntradaComoTarefaDeSaida(tarefaEntrada,horarioAtualDoDia);
                adicionaAlmocoComoTarefaDeSaida(horarioDeTerminoDaTarefa);
                horarioAtualDoDia = horarioDeTerminoDaTarefa.plusMinutes(TarefaSaidaUtils.quantidadeDeMinutosParaOAlmoco());
                almocoFoiAdicionado = true;
                continue;
            }

            if(horarioDeTerminoDaTarefaTerminaNoIntervaloDoAlmoco(horarioDeTerminoDaTarefa)){

                int minutosQueFaltamParaOHorarioDoAlmoco = minutosQueFaltamParaOHorarioDoAlmoco(horarioAtualDoDia);

                TarefaEntrada tarefaComTempoDeExecucaoIgualOuMenorEmRelacaoAoTempoQueFaltaParaOInicioDoAlmoco = tarefaEntradaDAO.pesquisaTarefaQueAindaNaoEstaNaListaDeSaidaEComTempoDeExecucaoIgualOuMenorA(minutosQueFaltamParaOHorarioDoAlmoco);

                if(tarefaComTempoDeExecucaoIgualOuMenorEmRelacaoAoTempoQueFaltaParaOInicioDoAlmoco != null){
                    salvarTarefaDeEntradaComoTarefaDeSaida(tarefaComTempoDeExecucaoIgualOuMenorEmRelacaoAoTempoQueFaltaParaOInicioDoAlmoco,horarioAtualDoDia);
                    horarioAtualDoDia = horarioAtualDoDia.plusMinutes(tarefaComTempoDeExecucaoIgualOuMenorEmRelacaoAoTempoQueFaltaParaOInicioDoAlmoco.getTempoDeExecucao());
                }else{
                    adicionaAlmocoComoTarefaDeSaida(TarefaEntradaUtils.getHorarioDoInicioDoAlmoco());
                    almocoFoiAdicionado = true;
                }

                tarefasDeEntradaPendentes.add(tarefaEntrada);

                continue;
            }

            if(horarioAtualDoDiaEhIgualAoHorarioDaGinastica(horarioAtualDoDia)){
                adicionaGinasticaComoTarefaDeSaida(horarioAtualDoDia);
                horarioAtualDoDia = TarefaEntradaUtils.getInicioDoTurnoMatutino();
                almocoFoiAdicionado = false;
                continue;
            }

            if(horarioDeTerminoDaTarefaEhIgualAoHorarioDeInicioDaGinastica(horarioDeTerminoDaTarefa)){
                salvarTarefaDeEntradaComoTarefaDeSaida(tarefaEntrada,horarioAtualDoDia);
                adicionaGinasticaComoTarefaDeSaida(horarioDeTerminoDaTarefa);
                horarioAtualDoDia = TarefaEntradaUtils.getInicioDoTurnoMatutino();
                almocoFoiAdicionado = false;
                continue;
            }

            if(horarioDeTerminoDaTarefaTerminaAposOHorarioDeInicioDaGinasticaEAntesDoHorarioDeTermino(horarioDeTerminoDaTarefa)){
                salvarTarefaDeEntradaComoTarefaDeSaida(tarefaEntrada,horarioAtualDoDia);
                adicionaGinasticaComoTarefaDeSaida(horarioDeTerminoDaTarefa);
                horarioAtualDoDia = TarefaEntradaUtils.getInicioDoTurnoMatutino();
                almocoFoiAdicionado = false;
                continue;
            }

            salvarTarefaDeEntradaComoTarefaDeSaida(tarefaEntrada,horarioAtualDoDia);
            horarioAtualDoDia = avancaHorarioAtualDoDiaComBaseNoTempoDeExecucaoDaTarefaDeEntrada(horarioAtualDoDia,tarefaEntrada);
        }
    }

    @Override
    public void removeTodosOsRegistros(TarefaSaida tarefaSaida) throws Exception {
        tarefaSaidaDAO.removeTodosOsRegistrosDaTabelaTarefasDeEntrada(tarefaSaida);
    }

    private int minutosQueFaltamParaOHorarioDoAlmoco(LocalDateTime horarioAtualDoDia){
        LocalDateTime minutosQueFaltamParaOHorarioDoAlmoco = TarefaEntradaUtils.getHorarioDoInicioDoAlmoco().minusMinutes(horarioAtualDoDia.getMinute());
        return minutosQueFaltamParaOHorarioDoAlmoco.getMinute();
    }

    private boolean tarefaDeEntradaJaExisteNaListaDeTArefasDeSaida(TarefaEntrada tarefaEntrada){
        return tarefaEntradaDAO.tarefaDeEntradaJaExisteNaListaDeTarefasDeSaida(tarefaEntrada);
    }

    private boolean horarioDeTerminoDaTarefaEhIgualAoHorarioDeInicioDoAlmoco(LocalDateTime horarioDeTerminoDaTarefa){
        return horarioDeTerminoDaTarefa.isEqual(TarefaEntradaUtils.getHorarioDoInicioDoAlmoco());
    }

    private boolean horarioDeTerminoDaTarefaEhIgualAoHorarioDeInicioDaGinastica(LocalDateTime horarioDeTerminoDaTarefa){
        return horarioDeTerminoDaTarefa.isEqual(TarefaEntradaUtils.getHorarioDoInicioDaGinastica());
    }

    private boolean horarioAtualDoDiaEhIgualAoHorarioDeInicioDoAlmoco(LocalDateTime horarioAtualDoDia){
        return horarioAtualDoDia.isEqual(TarefaEntradaUtils.getHorarioDoInicioDoAlmoco());
    }

    private boolean horarioAtualDoDiaEhIgualAoHorarioDaGinastica(LocalDateTime horarioAtualDoDia){
        return horarioAtualDoDia.isEqual(TarefaEntradaUtils.getHorarioDoInicioDaGinastica());
    }

    private boolean horarioDeTerminoDaTarefaTerminaNoIntervaloDoAlmoco(LocalDateTime horarioDeTerminoDaTarefa){
        return horarioDeTerminoDaTarefa.isAfter(TarefaEntradaUtils.getHorarioDoInicioDoAlmoco())
                && horarioDeTerminoDaTarefa.isBefore(TarefaEntradaUtils.getHorarioDoFimDoAlmoco());
    }

    private boolean horarioDeTerminoDaTarefaTerminaAposOHorarioDeInicioDaGinasticaEAntesDoHorarioDeTermino(LocalDateTime horarioDeTerminoDaTarefa){
        return horarioDeTerminoDaTarefa.isAfter(TarefaEntradaUtils.getHorarioDoInicioDaGinastica())
                && horarioDeTerminoDaTarefa.isBefore(TarefaEntradaUtils.getHorarioFimDaGinastica());
    }

    private void adicionaAlmocoComoTarefaDeSaida(LocalDateTime horarioAtualDoDia){

        TarefaSaida tarefaAlmoco = TarefaSaidaUtils.getTarefaAlmoco(horarioAtualDoDia);

        try {
            salva(tarefaAlmoco);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void adicionaGinasticaComoTarefaDeSaida(LocalDateTime horarioAtualDoDia){

        TarefaSaida tarefaGinastica = TarefaSaidaUtils.getTarefaGinastica(horarioAtualDoDia);

        try {
            salva(tarefaGinastica);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private LocalDateTime avancaHorarioAtualDoDiaComBaseNoTempoDeExecucaoDaTarefaDeEntrada(LocalDateTime horarioAtualDoDia,TarefaEntrada tarefaEntrada){
        return horarioAtualDoDia.plusMinutes(tarefaEntrada.getTempoDeExecucao());
    }

    private void salvarTarefaDeEntradaComoTarefaDeSaida(TarefaEntrada tarefaEntrada,LocalDateTime horarioAtualDoDia){

        TarefaSaida tarefaSaida = new TarefaSaida(tarefaEntrada.getNomeTarefa(),tarefaEntrada.getTempoDeExecucao(),horarioAtualDoDia);

        try {
            salva(tarefaSaida);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
