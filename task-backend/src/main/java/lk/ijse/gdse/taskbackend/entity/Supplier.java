package lk.ijse.gdse.taskbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tbl_master_suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplier_code")
    private String supplierCode;

    private String name;
    private String address;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        Active,
        Inactive
    }

}
