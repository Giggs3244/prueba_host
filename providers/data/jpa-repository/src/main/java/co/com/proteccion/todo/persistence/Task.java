package co.com.proteccion.todo.persistence;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 22/08/2017.
 */
@Data
@Entity
@Table(name = "task")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;
    private Date taskDate;
    private boolean isDone;

}
