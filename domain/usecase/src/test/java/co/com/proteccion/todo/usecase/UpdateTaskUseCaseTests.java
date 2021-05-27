package co.com.proteccion.todo.usecase;

import co.com.proteccion.base.model.Command;
import co.com.proteccion.base.model.Event;
import co.com.proteccion.todo.logic.TaskOperations;
import co.com.proteccion.todo.model.TaskDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UpdateTaskUseCaseTests {

    @Mock
    TaskOperations taskOperations;

    @InjectMocks
    UpdateTaskUseCase updateTaskUseCase;

    @Test
    public void testUpdate() {

        Command<TaskDto> comandoATestear = new Command<TaskDto>();
        comandoATestear.setNombre("task.update");
        TaskDto dto = new TaskDto();
        dto.setId(1l);
        dto.setName("Soy el nombre de la tarea");
        dto.setDone(false);
        dto.setTaskDate(new Date());
        comandoATestear.setPayload(dto);

        when(taskOperations.update(any(TaskDto.class))).thenReturn(
                Mono.just(dto).map(dto1 -> {
                    dto1.setDone(true);
                    return dto1;
                })
        );

        Flux<Event> eventosResultado = updateTaskUseCase.updateTask(comandoATestear);
        StepVerifier.create(eventosResultado)
                .expectSubscription()
                .expectNextMatches(event -> {
                    Assert.assertNotNull(event.getPayload());
                    TaskDto dto1 = (TaskDto)event.getPayload();
                    Assert.assertEquals(dto.getName(), dto1.getName());
                    Assert.assertEquals(1L, dto1.getId().intValue());
                    Assert.assertNotNull(dto1.getTaskDate());
                    Assert.assertTrue(dto1.isDone());
                    return true;
                })
                .expectComplete()
                .verify();

        verify(taskOperations).update(any(TaskDto.class));
        verify(taskOperations, times(0)).save(any(TaskDto.class));
    }

}
