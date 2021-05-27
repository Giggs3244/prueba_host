package co.com.proteccion.todo.microservice;

//TODO por favor cambie el paquete co.com.proteccion.todo.* que aparece en todo el demo por un paquete apropiado
//TODO para su aplicacion.

import co.com.proteccion.todo.logic.TaskOperations;
import co.com.proteccion.todo.usecase.DeleteTaskUseCase;
import co.com.proteccion.todo.usecase.QueriesUseCase;
import co.com.proteccion.todo.usecase.SaveTaskUseCase;
import co.com.proteccion.todo.usecase.UpdateTaskUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registro de los Casos de uso de la aplicacion.
 *
 * Los casos de uso son la parte de la arquitectura limpia donde se define la logica de negocio.
 *
 * Los casos de uso deben estar en el modulo domain/usecase
 *
 */
@Configuration
public class UseCaseConfiguration {

    @Bean
    public QueriesUseCase queriesUseCase(TaskOperations taskOperations){
        return new QueriesUseCase(taskOperations);
    }

    @Bean
    public SaveTaskUseCase saveTaskUseCase(TaskOperations taskOperations){
        return new SaveTaskUseCase(taskOperations);
    }

    @Bean
    public UpdateTaskUseCase updateTaskUseCase(TaskOperations taskOperations) {
        return new UpdateTaskUseCase(taskOperations);
    }

    @Bean
    public DeleteTaskUseCase deleteTaskUseCase(TaskOperations taskOperations) {
        return new DeleteTaskUseCase(taskOperations);
    }

}
