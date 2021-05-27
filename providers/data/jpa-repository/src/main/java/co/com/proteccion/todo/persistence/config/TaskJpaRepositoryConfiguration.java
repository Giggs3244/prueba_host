package co.com.proteccion.todo.persistence.config;

import co.com.proteccion.todo.logic.TaskOperations;
import co.com.proteccion.todo.persistence.*;
import lombok.extern.java.Log;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Log
@Configuration
@ComponentScan(basePackageClasses = TaskOperationsImpl.class)
@EnableJpaRepositories(basePackageClasses = JpaTaskRepository.class)
@EntityScan(basePackageClasses = Task.class)
public class TaskJpaRepositoryConfiguration {

    public TaskJpaRepositoryConfiguration() {
        log.info("TaskJpaRepositoryConfiguration INIT....");
    }

    @Bean
    public TaskMapper taskMapper() {
        return Mappers.getMapper( TaskMapper.class );
    }

    @Bean
    public TaskOperations taskOperations(TaskAdapterRepository taskAdapterRepository,
                                         TaskMapper taskMapper) {
        return new TaskOperationsImpl(taskAdapterRepository,
                taskMapper);
    }

}
