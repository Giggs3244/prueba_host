package co.com.proteccion.todo.microservice;

import co.com.proteccion.todo.usecase.DeleteTaskUseCase;
import co.com.proteccion.todo.usecase.QueriesUseCase;
import co.com.proteccion.todo.usecase.SaveTaskUseCase;
import co.com.proteccion.todo.usecase.UpdateTaskUseCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationHandlersConfigurationTests {

    @InjectMocks
    ApplicationHandlers applicationHandlers;

    @InjectMocks
    MainApplication mainApplication;

    @Mock
    QueriesUseCase queriesUseCase;

    @Mock
    DeleteTaskUseCase deleteTaskUseCase;

    @Mock
    SaveTaskUseCase saveTaskUseCase;

    @Mock
    UpdateTaskUseCase updateTaskUseCase;

    @Test
    public void testConfiguration() {

        assertNotNull(applicationHandlers.commandsConfig());
        assertEquals(3, applicationHandlers.commandsConfig().getCommandHandlers().size());
        assertEquals(1, applicationHandlers.commandsConfig().getEventListeners().size());

    }

    @Test
    public void testCLR() {
        MainApplication.printInfo();
        assertNotNull(mainApplication.memInfoRunner());
    }

}
