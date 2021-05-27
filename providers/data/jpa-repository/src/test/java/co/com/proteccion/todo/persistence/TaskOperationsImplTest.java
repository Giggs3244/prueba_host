package co.com.proteccion.todo.persistence;

import co.com.proteccion.todo.model.TaskDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskOperationsImplTest {

    private TaskOperationsImpl taskOperations;

    @Mock
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

        taskOperations = new TaskOperationsImpl(adapter, Mappers.getMapper( TaskMapper.class ));

    }

    @Test
    public void shouldListTasksWithFilterByStatus() {

        when(adapter.listByStatus(eq(true))).thenReturn(Flux.just(taskDone));
        when(adapter.listByStatus(eq(false))).thenReturn(Flux.just(taskNotDone));

        Flux<TaskDto> listDone = taskOperations.listByStatus(true);
        StepVerifier.create(listDone)
                .expectSubscription()
                .expectNextMatches(tasktaskDto -> {
                    assertEquals(taskDone.getId(), tasktaskDto.getId());
                    return true;
                })
                .expectComplete()
                .verify();

        Flux<TaskDto> listNotDone = taskOperations.listByStatus(false);
        StepVerifier.create(listNotDone)
                .expectSubscription()
                .expectNextMatches(taskDto -> {
                    assertEquals(taskNotDone.getId(), taskDto.getId());
                    return true;
                })
                .expectComplete()
                .verify();

        verify(adapter).listByStatus(eq(true));
        verify(adapter).listByStatus(eq(false));

    }

    @Test
    public void shouldListAllTasks() {

        when(adapter.all()).thenReturn(Flux.just(taskNotDone, taskDone));

        Flux<TaskDto> allList = taskOperations.listAll();
        StepVerifier.create(allList)
                .expectSubscription()
                .expectNextCount(2)
                .expectComplete()
                .verify();

        verify(adapter).all();

    }

    @Test
    public void shouldSaveTask() {

        Task result = new Task();
        result.setId(1L);
        result.setDone(false);
        result.setName("New Task");
        result.setDone(false);
        result.setTaskDate(new Date());
        result.setDescription("Hello Task");

        when(adapter.save(any(Task.class))).thenReturn(Mono.just(result));

        TaskDto seed = new TaskDto();
        seed.setDone(false);
        seed.setName("New Task");

        Mono<TaskDto> saveMono = taskOperations.save(seed);
        StepVerifier.create(saveMono)
                .expectSubscription()
                .expectNextMatches( argDto -> {
                    assertEquals(1L, argDto.getId().longValue());
                    assertEquals(seed.getName(), argDto.getName());
                    return true;
                })
                .expectComplete()
                .verify();

        verify(adapter).save(any(Task.class));

    }

    @Test
    public void shouldUpdateTask() {

        Task existingEntity = new Task();
        existingEntity.setId(1L);
        existingEntity.setDone(false);
        existingEntity.setName("Existing Task");
        existingEntity.setDone(false);
        existingEntity.setTaskDate(new Date());
        existingEntity.setDescription("Hello Task");
//        when(adapter.byId(any(Long.class))).thenReturn(Mono.just(existingEntity));

        Task updatedEntity = Mockito.spy(existingEntity);
//        when(updatedEntity.isDone()).thenReturn(true);
        when(adapter.save(any(Task.class))).thenReturn(Mono.just(updatedEntity));

        TaskDto seed = new TaskDto();
        seed.setId(1L);
        seed.setDone(false);
        seed.setName("Existing Task");
        seed.setDone(true);
        seed.setTaskDate(new Date());
        seed.setDescription("Hello Task");

        Mono<TaskDto> updateMono = taskOperations.update(seed);
        StepVerifier.create(updateMono)
                .expectSubscription()
                .expectNextMatches( argDto -> {
                    assertEquals(seed.getName(), argDto.getName());
                    assertEquals(seed.isDone(), argDto.isDone());
                    return true;
                })
                .expectComplete()
                .verify();

        verify(adapter).save(any(Task.class));
        verify(adapter, times(0)).update(any(Task.class));

    }

    @Test
    public void shouldDeleteATask() {

        Task result = new Task();
        result.setId(1L);
        result.setDone(false);
        result.setName("Existing Task");
        result.setDone(true);
        result.setTaskDate(new Date());
        result.setDescription("Hello Task");

        when(adapter.delete(any(Long.class))).thenReturn(Mono.empty().then());

        Mono<Void> deleteMono = taskOperations.delete(1L);
        StepVerifier.create(deleteMono)
                .expectSubscription()
                .expectComplete()
                .verify();

        verify(adapter).delete(any(Long.class));

    }
}

