package co.com.proteccion.todo.usecase;

import co.com.proteccion.todo.logic.TaskOperations;
import co.com.proteccion.todo.model.TaskDto;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class QueriesUseCase {

    private final TaskOperations taskOperations;

    public Flux<TaskDto> listByStatusDone() {
        return taskOperations.listByStatus(true);
    }

    public Flux<TaskDto> listByStatusNotDone() {
        return taskOperations.listByStatus(false);
    }

    public Flux<TaskDto> all() {
        return taskOperations.listAll();
    }

}
