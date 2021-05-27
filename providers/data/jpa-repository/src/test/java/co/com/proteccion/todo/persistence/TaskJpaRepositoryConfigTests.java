package co.com.proteccion.todo.persistence;

import co.com.proteccion.todo.persistence.config.TaskJpaRepositoryConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class TaskJpaRepositoryConfigTests {

    @InjectMocks
    TaskJpaRepositoryConfiguration taskJpaRepositoryConfiguration;

    @Mock
    TaskAdapterRepository taskAdapterRepository;

    @Test
    public void shouldCreateTaskMapperBean() {
        assertNotNull(taskJpaRepositoryConfiguration.taskMapper());
    }

    @Test
    public void shouldCreateTaskOperationsBean() {
        assertNotNull(taskJpaRepositoryConfiguration.taskOperations(taskAdapterRepository,
                taskJpaRepositoryConfiguration.taskMapper()));
    }
}
