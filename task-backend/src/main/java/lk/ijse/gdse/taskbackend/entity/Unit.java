package lk.ijse.gdse.taskbackend.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Unit {
    @Id
    private String id;

    private String code;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status = Status.Active;
    public enum Status {
        Active,
        Inactive
    }
}
