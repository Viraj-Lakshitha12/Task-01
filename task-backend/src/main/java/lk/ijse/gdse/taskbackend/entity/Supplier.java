package lk.ijse.gdse.taskbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Supplier {
    @Id
    private String id;

    private String supplierCode;

    private String name;

    private String address;

    @Enumerated(EnumType.STRING)
    private Status status = Status.Active;

    public enum Status {
        Active,
        Inactive
    }
}
