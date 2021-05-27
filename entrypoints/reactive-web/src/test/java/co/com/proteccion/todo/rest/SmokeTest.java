package co.com.proteccion.todo.rest;

import co.com.proteccion.todo.rest.config.DemoRestAutoConfiguration;
import co.com.proteccion.todo.usecase.QueriesUseCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.main.web-application-type=reactive")
@ContextConfiguration(classes = DemoRestAutoConfiguration.class)
public class SmokeTest {

    @MockBean
    private QueriesUseCase queriesUseCase;

    @Autowired
    private TaskQueryController controller;

    @Test
    public void contexLoads() throws Exception {
        Assert.assertNotNull(controller);
    }
}
