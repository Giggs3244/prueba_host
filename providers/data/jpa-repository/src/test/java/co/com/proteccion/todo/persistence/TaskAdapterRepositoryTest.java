package co.com.proteccion.todo.persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskAdapterRepositoryTest {

    @Mock
    private JpaTaskRepository repository;

    @InjectMocks
    private TaskAdapterRepository adapter;

    private Task taskNotDone;
    private Task taskDone;

    @Before
    public void setup() {
        taskNotDone = new Task();
        taskNotDone.setId(1L);
        taskNotDone.setName("Task 1");
        taskNotDone.setDone(false);

        taskDone = new Task();
        taskDone.setId(2L);
        taskDone.setName("Task 2");
        taskDone.setDone(true);
    }

    @Test
    public void listWithFilterByStatusDone() {

        when(repository.listByDone(eq(true))).thenReturn(Collections.singletonList(taskDone));
        when(repository.listByDone(eq(false))).thenReturn(Collections.singletonList(taskNotDone));

        Flux<Task> listDone = adapter.listByStatus(true);
        StepVerifier.create(listDone)
                .expectSubscription()
                .expectNextMatches(taskA -> {
                    Assert.assertEquals(taskDone.getId(), taskA.getId());
                    return true;
                })
                .expectComplete()
                .verify();

        Flux<Task> listNotDone = adapter.listByStatus(false);
        StepVerifier.create(listNotDone)
                .expectSubscription()
                .expectNextMatches(taskB -> {
                    Assert.assertEquals(taskNotDone.getId(), taskB.getId());
                    return true;
                })
                .expectComplete()
                .verify();

        verify(repository).listByDone(eq(true));
        verify(repository).listByDone(eq(false));

    }

    @Test
    public void listAll() {

        when(repository.findAll()).thenReturn(Arrays.asList(taskNotDone, taskDone));

        Flux<Task> allList = adapter.all();
        StepVerifier.create(allList)
                .expectSubscription()
                .expectNext(taskNotDone, taskDone)
                .expectComplete()
                .verify();

        verify(repository).findAll();

    }
}

