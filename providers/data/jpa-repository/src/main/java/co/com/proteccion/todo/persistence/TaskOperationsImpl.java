package co.com.proteccion.todo.persistence;

import co.com.proteccion.todo.logic.TaskOperations;
import co.com.proteccion.todo.model.TaskDto;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementacion de los servicios definidos en 'TaskOperations' con persistencia JPA.
 */
@RequiredArgsConstructor
public class TaskOperationsImpl implements TaskOperations {

    private final TaskAdapterRepository taskAdapterRepository;
    private final TaskMapper taskMapper;

    @Override
    public Flux<TaskDto> listByStatus(boolean isDone) {
        return taskAdapterRepository.listByStatus(isDone)
                .map(taskMapper::toDTO);
    }

    @Override
    public Flux<TaskDto> listAll() {
        return taskAdapterRepository.all()
                .map(taskMapper::toDTO);
    }

    @Override
    public Mono<TaskDto> save(TaskDto taskDto) {
        Task entidad = taskMapper.toEntity(taskDto);
        return taskAdapterRepository.save(entidad)
                .map(task1 -> {
                    taskDto.setId(task1.getId());
                    return taskDto;
                });
    }

    @Override
    public Mono<TaskDto> update(TaskDto taskDto) {
        Task entidad = taskMapper.toEntity(taskDto);
        return taskAdapterRepository.save(entidad)
                .map(task1 -> {
                    taskDto.setId(task1.getId());
                    return taskDto;
                });
    }

    @Override
    public Mono<Void> delete(Long taskid) {
        return taskAdapterRepository.delete(taskid);
    }

}
