package dao;
import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface DAO<T> {

    T get(int id);
    Collection<T> getAll();
    void create(T data);
    void delete(int id);
    void write() throws IOException;
}
