package co.com.proteccion.todo.microservice;

import co.com.proteccion.todo.logic.TaskOperations;
import co.com.proteccion.todo.usecase.DeleteTaskUseCase;
import co.com.proteccion.todo.usecase.QueriesUseCase;
import co.com.proteccion.todo.usecase.SaveTaskUseCase;
import co.com.proteccion.todo.usecase.UpdateTaskUseCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UseCaseConfigurationTests {

    @InjectMocks
    UseCaseConfiguration useCaseConfiguration;

    @Mock
    TaskOperations taskOperations;

    @Before
    public void prepare() {
        this.useCaseConfiguration = new UseCaseConfiguration();
    }

    @Test
    public void testConfiguration() {

        QueriesUseCase queriesUseCase = useCaseConfiguration.queriesUseCase(taskOperations);
        Assert.assertNotNull(queriesUseCase);

        DeleteTaskUseCase deleteTaskUseCase = useCaseConfiguration.deleteTaskUseCase(taskOperations);
        Assert.assertNotNull(deleteTaskUseCase);

        SaveTaskUseCase saveTaskUseCase = useCaseConfiguration.saveTaskUseCase(taskOperations);
        Assert.assertNotNull(saveTaskUseCase);

        UpdateTaskUseCase updateTaskUseCase = useCaseConfiguration.updateTaskUseCase(taskOperations);
        Assert.assertNotNull(updateTaskUseCase);

    }
}
