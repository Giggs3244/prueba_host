package co.com.proteccion.todo.rest.config;

import co.com.proteccion.todo.rest.TaskQueryController;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore({ValidationAutoConfiguration.class})
@ComponentScan(basePackageClasses = {TaskQueryController.class})
public class DemoRestAutoConfiguration {

}
