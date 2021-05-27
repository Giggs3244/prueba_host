package co.com.proteccion.todo.rest;

import co.com.proteccion.todo.model.TaskDto;
import co.com.proteccion.todo.usecase.QueriesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Esta clase se encarga de realizar el control de todos los mentodos GET de algun dominio.
 * Depende de una capa de servicio que basicamente es la encargada de procesar la logina necesaria para
 * este control.
 * <p>
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 24/08/2017.
 */
@RestController
public class TaskQueryController {

    private QueriesUseCase useCase;

    /**
     * Se inyecta utilizando el constructor para que su dependencia sea fuerte y ademar para apoyar a
     * sistema de testing
     *
     * @param useCase
     */
    @Autowired
    public TaskQueryController(QueriesUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping(value = "/tasks/done")
    public Flux<TaskDto> listByStatusDone() {
        return useCase.listByStatusDone();
    }

    @GetMapping(value = "/tasks")
    public Flux<TaskDto> all() {
        return useCase.all();
    }
}
