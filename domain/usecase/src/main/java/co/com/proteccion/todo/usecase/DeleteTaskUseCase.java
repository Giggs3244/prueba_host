package co.com.proteccion.todo.usecase;

import co.com.proteccion.base.event.EventScope;
import co.com.proteccion.base.model.Event;
import co.com.proteccion.todo.logic.TaskOperations;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import static reactor.core.publisher.Mono.just;

@RequiredArgsConstructor
public class DeleteTaskUseCase {

    private final TaskOperations taskOperations;

    public Flux<Event> deleteTask(Long taskId){
        return just(taskId)
            .flatMap(taskOperations::delete)
            .thenReturn(deleteSuccess(taskId))
            .log().flux();
    }

    private Event deleteSuccess(Long taskId) {
        Event evt = new Event();
        evt.setPayload(taskId);
        evt.setEventScope(EventScope.SCOPE_UI.getScope());
        evt.setNombre("task.deleted");
        return evt;
    }

}
