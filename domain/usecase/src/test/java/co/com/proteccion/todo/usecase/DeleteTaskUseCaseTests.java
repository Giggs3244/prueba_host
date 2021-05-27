package co.com.proteccion.todo.usecase;

import co.com.proteccion.base.model.Event;
import co.com.proteccion.todo.logic.TaskOperations;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeleteTaskUseCaseTests {

    @Mock
    TaskOperations taskOperations;

    @InjectMocks
    DeleteTaskUseCase deleteTaskUseCase;

    @Test
    public void testDelete() {

        when(taskOperations.delete(eq(1l))).thenReturn(
                Mono.empty().then()
        );

        Flux<Event> eventosResultado = deleteTaskUseCase.deleteTask(1l);
        StepVerifier.create(eventosResultado)
                .expectSubscription()
                .expectNextMatches(event -> {
                    Assert.assertNotNull(event.getPayload());
                    Assert.assertEquals("task.deleted", event.getNombre());
                    Long payload = (Long)event.getPayload();
                    Assert.assertEquals(1L, payload.longValue());
                    return true;
                })
                .expectComplete()
                .verify();

        verify(taskOperations).delete(eq(1l));
    }

}
