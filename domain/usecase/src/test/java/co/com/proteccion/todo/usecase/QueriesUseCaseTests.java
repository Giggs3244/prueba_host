package co.com.proteccion.todo.usecase;

import co.com.proteccion.todo.logic.TaskOperations;
import co.com.proteccion.todo.model.TaskDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QueriesUseCaseTests {

    @Mock
    TaskOperations taskOperations;

    @InjectMocks
    QueriesUseCase queriesUseCase;

    @Test
    public void tesQueryAll() {

        TaskDto dto = new TaskDto();
        dto.setId(1l);
        dto.setName("Soy el nombre de la tarea");

        when(taskOperations.listAll()).thenReturn(Flux.just(dto));

        Flux<TaskDto> eventosResultado = queriesUseCase.all();
        StepVerifier.create(eventosResultado)
                .expectSubscription()
                .expectNextMatches(dto1 -> {
                    Assert.assertEquals(dto.getName(), dto1.getName());
                    Assert.assertEquals(1L, dto1.getId().intValue());
                    return true;
                })
                .expectComplete()
                .verify();

        verify(taskOperations).listAll();
    }

    @Test
    public void tesQueryByStatus() {

        TaskDto dto2 = new TaskDto();
        dto2.setId(2l);
        dto2.setName("Soy otra tarea");
        dto2.setDone(true);

        TaskDto dto3 = new TaskDto();
        dto3.setId(3l);
        dto3.setName("Soy otra tarea");

        when(taskOperations.listByStatus(eq(true))).thenReturn(Flux.just(dto2));
        when(taskOperations.listByStatus(eq(false))).thenReturn(Flux.just(dto3));

        Flux<TaskDto> eventosResultado = queriesUseCase.listByStatusDone();
        StepVerifier.create(eventosResultado)
                .expectSubscription()
                .expectNextMatches(dto1 -> {
                    Assert.assertEquals(dto2.getName(), dto1.getName());
                    Assert.assertEquals(2L, dto1.getId().intValue());
                    Assert.assertTrue(dto2.isDone());
                    return true;
                })
                .expectComplete()
                .verify();


        Flux<TaskDto> eventosResultado2 = queriesUseCase.listByStatusNotDone();
        StepVerifier.create(eventosResultado2)
                .expectSubscription()
                .expectNextMatches(dto1 -> {
                    Assert.assertEquals(dto3.getName(), dto1.getName());
                    Assert.assertEquals(3L, dto1.getId().intValue());
                    Assert.assertFalse(dto1.isDone());
                    return true;
                })
                .expectComplete()
                .verify();

        verify(taskOperations, times(1)).listByStatus((eq(true)));
        verify(taskOperations, times(1)).listByStatus((eq(false)));


    }
}
