package co.com.proteccion.todo.microservice;

//TODO por favor cambie el paquete co.com.proteccion.todo.* que aparece en todo el demo por un paquete apropiado
//TODO para su aplicacion.

import co.com.proteccion.base.HandlerRegistry;
import co.com.proteccion.todo.model.TaskDto;
import co.com.proteccion.todo.usecase.DeleteTaskUseCase;
import co.com.proteccion.todo.usecase.SaveTaskUseCase;
import co.com.proteccion.todo.usecase.UpdateTaskUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static co.com.proteccion.base.HandlerRegistry.register;

/**
 * Esta clase es usada para enumerar el inventario de los comandos y los eventos, que la microaplicacion
 * va a soportar.
 *
 * Esta es la clase que reemplaza a 'ApplicationExecutors' que existio hasta la version 1.4
 * de la arquitectura base.
 *
 * @author Daniel Bustamante Ospina <daniel.bustamante@sofka.com.co>
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationHandlers {

    private final SaveTaskUseCase saveUseCase;
    private final DeleteTaskUseCase deleteUseCase;
    private final UpdateTaskUseCase updateUseCase;

    /**
     * Declaracion de comandos / Eventos ejecutables.
     *
     * - Los comandos siempre son ejecutables.
     * - Los eventos son ejecutables solo si el scope es SCOPE_MQ.
     *
     * handleCommand( NAME, MessageHandler, TypeOfPayload)
     * listenEvent( NAME, MessageHandler, TypeOfPayload)
     *
     * @return
     */
    @Bean
    public HandlerRegistry commandsConfig() {
        return register()
                .handleCommand("task.save", saveUseCase::saveTask, TaskDto.class)
                .listenEvent("task.saved", saveUseCase::someOherOperation, TaskDto.class)
                .handleCommand("task.update", updateUseCase::updateTask, TaskDto.class)
                .handleCommand("task.delete", cmd -> deleteUseCase.deleteTask(cmd.getPayload()), Long.class);
    }
}
