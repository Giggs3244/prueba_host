package co.com.proteccion.todo.usecase;

import co.com.proteccion.base.event.EventScope;
import co.com.proteccion.base.model.Command;
import co.com.proteccion.base.model.Event;
import co.com.proteccion.todo.logic.TaskOperations;
import co.com.proteccion.todo.model.TaskDto;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import static reactor.core.publisher.Mono.just;

@RequiredArgsConstructor
public class UpdateTaskUseCase {

    private final TaskOperations taskOperations;

    public Flux<Event> updateTask(Command<TaskDto> command) {
        return just(command.getPayload())
            .flatMap(taskOperations::update)
            .map(this::updateSuccess)
            .log().flux();
    }

    private Event updateSuccess(TaskDto task) {
        Event evt = new Event();
        evt.setPayload(task);
        evt.setEventScope(EventScope.SCOPE_UI.getScope());
        evt.setNombre("task.updated");
        return evt;
    }

}
