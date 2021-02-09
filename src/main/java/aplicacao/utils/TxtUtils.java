package aplicacao.utils;
import aplicacao.dominio.TarefaSaida;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TxtUtils {


    public static void escreveResultadoEmArquivoDeSaidaTxt(List<TarefaSaida> tarefaSaidas){

        AtomicInteger contadorDeLinhaDeMontagem = new AtomicInteger(1);

        tarefaSaidas.forEach(tarefaSaida -> {

            if(horarioDeInicioDaTarefaEhIgualAoInicioDoHorarioDoTurnoMatutino(tarefaSaida.getDataHoraMinutoDeInicioDaTarefa())){
                TxtUtils.escreverEmArquivoTxt("");
                TxtUtils.escreverEmArquivoTxt("Linha de Montagem " + contadorDeLinhaDeMontagem+":");
                contadorDeLinhaDeMontagem.addAndGet(1);
            }

            String horarioDeInicioDaTarefa =tarefaSaida.getDataHoraMinutoDeInicioDaTarefa().getHour()+":"+tarefaSaida.getDataHoraMinutoDeInicioDaTarefa().getMinute()+":"+tarefaSaida.getDataHoraMinutoDeInicioDaTarefa().getSecond()+" ";

            String nomeDaTarefa =  tarefaSaida.getNomeTarefa()+" ";

            String tempoDeExecucaoDaTarefa = tarefaSaida.getTempoDeExecucao()+"min";

            String linha = horarioDeInicioDaTarefa.concat(nomeDaTarefa).concat(tempoDeExecucaoDaTarefa);

            escreverEmArquivoTxt(linha);
        });
    }

    private static void escreverEmArquivoTxt(String fraseDesCriptografada) {

        File arquivo = new File( "src/main/resources/saida.txt");
        try {
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }

            FileWriter fw = new FileWriter(arquivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fraseDesCriptografada);
            bw.newLine();
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<String> getDadosDeEntrada(String fileName){

        List<String> linhas = Collections.emptyList();

        try{
            linhas = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        return linhas;
    }

    private static boolean horarioDeInicioDaTarefaEhIgualAoInicioDoHorarioDoTurnoMatutino(LocalDateTime horaDeInicioDaTarefa){
        return horaDeInicioDaTarefa.isEqual(TarefaEntradaUtils.getInicioDoTurnoMatutino());
    }

    public static boolean arquivoDeSaidaJaExiste(){
        File arquivo = new File( "src/main/resources/saida.txt");
        if (arquivo.exists()) {
            return true;
        }
        return false;
    }

    public static void removeArquivoDeSaida(){
        File arquivo = new File( "src/main/resources/saida.txt");
        arquivo.delete();
    }
}
