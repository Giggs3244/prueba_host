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
public class SaveTaskUseCaseTests {

    @Mock
    TaskOperations taskOperations;

    @InjectMocks
    SaveTaskUseCase saveTaskUseCase;

    @Test
    public void testSave() {

        Command<TaskDto> comandoATestear = new Command<TaskDto>();
        comandoATestear.setNombre("task.save");
        TaskDto dto = new TaskDto();
        dto.setName("Soy el nombre de la tarea");
        comandoATestear.setPayload(dto);

        when(taskOperations.save(any(TaskDto.class))).thenReturn(
                Mono.just(dto).map(dto1 -> {
                    dto1.setId(1l);
                    dto1.setTaskDate(new Date());
                    return dto1;
                })
        );

        Flux<Event> eventosResultado = saveTaskUseCase.saveTask(comandoATestear);
        StepVerifier.create(eventosResultado)
                .expectSubscription()
                .expectNextMatches(event -> {
                    Assert.assertNotNull(event.getPayload());
                    TaskDto dto1 = (TaskDto)event.getPayload();
                    Assert.assertEquals(dto.getName(), dto1.getName());
                    Assert.assertEquals(1L, dto1.getId().intValue());
                    Assert.assertNotNull(dto1.getTaskDate());
                    return true;
                })
                .expectComplete()
                .verify();

        verify(taskOperations).save(any(TaskDto.class));
    }

    @Test
    public void testEvent() {

        Event<TaskDto> eventoATestear = new Event<>();
        eventoATestear.setNombre("task.saved");
        TaskDto dto = new TaskDto();
        dto.setName("Soy el nombre de la tarea");
        eventoATestear.setPayload(dto);

        Flux<Event> eventosResultado = saveTaskUseCase.someOherOperation(eventoATestear);
        StepVerifier.create(eventosResultado)
                .expectSubscription()
                .expectNextMatches(event -> {
                    Assert.assertNotNull(event.getPayload());
                    Assert.assertEquals("task.saved.complete", event.getNombre());

                    TaskDto dto1 = (TaskDto)event.getPayload();
                    Assert.assertEquals(dto.getName(), dto1.getName());

                    return true;
                })
                .expectComplete()
                .verify();

        verify(taskOperations, times(0)).save(any(TaskDto.class));
    }
}
