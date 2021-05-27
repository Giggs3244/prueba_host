package co.com.proteccion.todo.persistence;

import co.com.proteccion.todo.model.TaskDto;
import org.mapstruct.Mapper;

/**
 * Esta clase permite realizar mapeos entre DTO y Entidades.
 *
 */
@Mapper(componentModel="spring")
public abstract class TaskMapper {

    public abstract TaskDto toDTO(Task source);

    public abstract Task toEntity(TaskDto source);

}
