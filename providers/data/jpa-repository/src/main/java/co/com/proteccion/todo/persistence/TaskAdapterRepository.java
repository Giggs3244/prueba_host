package co.com.proteccion.todo.persistence;

import co.com.proteccion.base.common.ReactiveAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


/**
 * Esta clase extiende de la clase ReactiveAdapter que pertenece al paquete common, en general extiende
 * de un template que permite adaptar los objectos no reactivos a objectos reactivos.
 *
 * <p>
 * Depende de ReactiveAdapter que es el Repo con los objectos No-Reactive, el adaptador padre se encarga de
 * convertirlos.
 * <p>
 *
 * Igualmente esta clase es un adaptador y los metodos que no son proporcionados por la clase ReactiveAdapter
 * se deben transformar a objectos Reactivos.
 *
 * <p>
 * Nota: Se debe usar el estereotipo @Repository para indentificar la capa de persistencia
 * <p>
 *
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 22/08/2017.
 */

@Repository
public class TaskAdapterRepository extends ReactiveAdapter<Task> {
    private final JpaTaskRepository repository;

    @Autowired
    public TaskAdapterRepository(JpaTaskRepository repository) {
        super(repository);
        this.repository = repository;
    }

    /**
     * transformacion de una lista de tareas a un objeto reactivo.
     *
     * @return Flux<Task>
     */
    public Flux<Task> listByStatus(boolean isDone) {
        return Flux.fromStream(repository.listByDone(isDone).stream());
    }

    /**
     * transformacion de una lista de tareas a un objeto reactivo.
     *
     * @return Flux<Task>
     */
    @Override
    public Flux<Task> all() {
        return Flux.fromStream(repository.findAll().stream());
    }
}
