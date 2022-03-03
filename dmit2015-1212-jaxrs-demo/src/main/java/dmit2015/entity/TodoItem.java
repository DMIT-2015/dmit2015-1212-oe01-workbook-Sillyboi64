package dmit2015.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TodoItem implements Serializable {

    @Id                 // This is the primary key field
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // This primary key field is generated by the database
    private Long id;

    @NotBlank(message = "Name field value is required.")
    @Column(nullable = false, length = 64)
    private String name;

    private boolean complete;

    @Version
    private Integer version;

    public TodoItem(Long id, String name, boolean complete) {
        this.id = id;
        this.name = name;
        this.complete = complete;
    }
}