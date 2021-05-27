package co.com.proteccion.todo.rest;

import co.com.proteccion.todo.model.TaskDto;
import co.com.proteccion.todo.usecase.QueriesUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@RunWith(MockitoJUnitRunner.class)
public class TaskQueryControllerTest {

    @InjectMocks
    TaskQueryController taskQueryController;

    @Mock
    private QueriesUseCase queriesUseCase;

    private TaskDto taskNotDone;
    private TaskDto taskDone;

    @Before
    public void prepare() {
        taskNotDone = new TaskDto();
        taskNotDone.setId(1L);
        taskNotDone.setName("Task 1");
        taskNotDone.setDone(false);

        taskDone = new TaskDto();
        taskDone.setId(2L);
        taskDone.setName("Task 2");
        taskDone.setDone(true);
    }

    @Test
    public void testQueryAll() {

        when(queriesUseCase.all()).thenReturn(Flux.just(taskDone, taskNotDone));

        create(taskQueryController.all())
                .expectSubscription()
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @Test
    public void testQueryAllDone() {

        when(queriesUseCase.listByStatusDone()).thenReturn(Flux.just(taskDone));

        create(taskQueryController.listByStatusDone())
                .expectSubscription()
                .expectNextMatches(taskDto -> {
                    assertEquals("Task 2", taskDto.getName());
                    return true;
                })
                .expectComplete()
                .verify();
    }
}
