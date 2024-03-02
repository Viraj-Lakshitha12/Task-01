package lk.ijse.gdse.taskbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierDTO {
    private Long id;
    private String supplierCode;
    private String name;
    private String address;
    private String status;
}
