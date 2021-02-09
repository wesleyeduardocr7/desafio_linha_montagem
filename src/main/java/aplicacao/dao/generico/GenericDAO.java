package aplicacao.dao.generico;
import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, K extends Serializable> {

    void salvar(T obj) throws Exception;

    List<T> recuperar() throws Exception;

    void removeTodosOsRegistrosDaTabelaTarefasDeEntrada(T obj)throws Exception;
}
