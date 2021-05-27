package co.com.proteccion.todo.model;

import lombok.Data;

import java.util.Date;

/**
 * El demo de arquitectura es una lista de tareas, esta clase representa la tarea que una
 * persona puede crear.
 *
 * A nivel de capa de datos exite una clase de entidad que mapea la Tarea a una tabla en BD.
 * No es recomendable la Entidad definida en la capa de datos como una clase DTO.
 *
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 22/08/2017.
 */
@Data
public class TaskDto {

    private Long id;
    private String name;
    private String description;
    private Date taskDate;
    private boolean isDone;

}
