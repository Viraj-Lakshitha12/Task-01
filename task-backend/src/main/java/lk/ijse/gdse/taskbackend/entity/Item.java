package lk.ijse.gdse.taskbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Item {
    @Id
    private String id;
    private String code;
    private String name;
    private String category;
    private String unit;
    @Enumerated(EnumType.STRING)
    private Status status = Status.Active;
    public enum Status {
        Active,
        Inactive
    }
}
