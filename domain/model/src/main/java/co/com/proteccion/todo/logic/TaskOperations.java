package co.com.proteccion.todo.logic;

import co.com.proteccion.todo.model.TaskDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interface que define servicios usados en la aplicacion sobre las tareas (TASK).
 *
 * En la filosofia de arquitectura limpia, sera(n) otro(s) modulo(s) el(los) encargado(s)
 * de implementar la funcionalidad esta interfaz.
 *
 */
public interface TaskOperations {

    Flux<TaskDto> listByStatus(boolean isDone);
    Flux<TaskDto> listAll();
    Mono<TaskDto> save(TaskDto task);
    Mono<TaskDto> update(TaskDto task);
    Mono<Void> delete(Long taskid);
}
