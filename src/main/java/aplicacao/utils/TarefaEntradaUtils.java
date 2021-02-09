package aplicacao.utils;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TarefaEntradaUtils {

    public static LocalDateTime getInicioDoTurnoMatutino(){
        LocalDateTime inicioDoTurnoMatutino =
                LocalDateTime.of(LocalDate.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth(),
                        9,
                        00,
                        00);
        return inicioDoTurnoMatutino;
    }

    public static LocalDateTime getHorarioDoInicioDoAlmoco(){
        LocalDateTime horarioExatoDoInicioDoAlmoco =
                LocalDateTime.of(LocalDate.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth(),
                        12,
                        00,
                        00);
        return horarioExatoDoInicioDoAlmoco;
    }

    public static LocalDateTime getHorarioDoFimDoAlmoco(){
        LocalDateTime horarioExatoDoInicioDoAlmoco =
                LocalDateTime.of(LocalDate.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth(),
                        13,
                        00,
                        00);
        return horarioExatoDoInicioDoAlmoco;
    }

    public static LocalDateTime getHorarioDoInicioDaGinastica(){
        LocalDateTime horarioExatoDoInicioDaGinastica =
                LocalDateTime.of(LocalDate.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth(),
                        16,
                        00,
                        00);
        return horarioExatoDoInicioDaGinastica;
    }

    public static LocalDateTime getHorarioFimDaGinastica(){
        LocalDateTime horarioExatoDoInicioDaGinastica =
                LocalDateTime.of(LocalDate.now().getYear(),
                        LocalDateTime.now().getMonth(),
                        LocalDateTime.now().getDayOfMonth(),
                        17,
                        00,
                        00);
        return horarioExatoDoInicioDaGinastica;
    }
}
