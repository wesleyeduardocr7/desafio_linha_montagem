package aplicacao.utils;
import aplicacao.dominio.TarefaSaida;
import java.time.LocalDateTime;

public class TarefaSaidaUtils {

    public static TarefaSaida getTarefaAlmoco(LocalDateTime horaInicio){
        return new TarefaSaida("Almoco",60,horaInicio);
    }

    public static TarefaSaida getTarefaGinastica(LocalDateTime horaInicio){
        return new TarefaSaida("Ginástica laboral",60,horaInicio);
    }

    public static int quantidadeDeMinutosParaOAlmoco(){
        return 60;
    }
}
