package co.com.proteccion.todo.usecase;

import co.com.proteccion.base.event.EventScope;
import co.com.proteccion.base.model.Command;
import co.com.proteccion.base.model.Event;
import co.com.proteccion.todo.logic.TaskOperations;
import co.com.proteccion.todo.model.TaskDto;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import static reactor.core.publisher.Mono.just;

/**
 * Caso de uso con la logica pertinente a la creacion de tareas.
 */
@RequiredArgsConstructor
public class SaveTaskUseCase {

    private final TaskOperations taskOperations;

    /**
     * Este es un metodo para el procesamiento de comandos, ya que
     * recibe un comando y genera un flux de eventos.
     *
     * @param task El comando recibido, cuyo payload para esta caso corresponde a un TaskDto
     * @return un flujo de eventos producto del procesamiento del comando. Para este caso solo uno
     * de nombre 'task.saved' y con SCOPE_MQ
     */
    public Flux<Event> saveTask(Command<TaskDto> task){

        /*
            Aqui va la logica de negocio para procesar el comando
        */
        // En el demo solo se esta llamando el repositorio para guardar en BD,
        return just(task.getPayload())
            .flatMap(taskOperations::save)
            .map(this::eventSuccess)
            .log().flux();

    }

    /**
     * En metodo solo genear un evento que diga que la tarea se grabo.
     *
     * Este evento al ser scope SCOPE_MQ, no se envia aun a la UI, va al broker de
     * mensajes para continuar siendo procesado.
     *
     * @param task el payload del comando, que para el caso corresponde a TaskDto
     * @return un evento
     */
    private Event eventSuccess(TaskDto task) {
        Event evt = new Event();
        evt.setPayload(task);
        evt.setEventScope(EventScope.SCOPE_MQ.getScope());
        // los nombres de los eventos que tienen SCOPE_MQ, se deben registrar en el HandlerRegistry,
        // ver la clase ApplicationHandlers de este demo para mas detalles.
        evt.setNombre("task.saved");
        return evt;
    }

    /**
     * Este es un metodo para el procesamiento de eventos, ya que
     * recibe un Evento y a su vez genera un Flux de eventos como
     * respuesta.
     *
     * @param eventData el Evento recibido, cuyo payload corresponde a un TaskDto
     * @return un flujo de eventos producto del procesamiento del evento. Para este caso solo uno con SCOPE_UI.
     */
    public Flux<Event> someOherOperation(Event<TaskDto> eventData) {

        /*
            Aqui se puede colocar la logica de negocio para procesar el evento
        */

        // finalmente se retorna solo un evento con scope SCOPE_UI para que
        // este ya sea enviado a la UI.
        Event evt = new Event();
        evt.setPayload(eventData.getPayload());
        evt.setEventScope(EventScope.SCOPE_UI.getScope());
        // los nombres de los eventos que tienen SCOPE_UI, no se registran en el HandlerRegistry
        evt.setNombre("task.saved.complete");
        return Flux.just(evt);
    }
}
