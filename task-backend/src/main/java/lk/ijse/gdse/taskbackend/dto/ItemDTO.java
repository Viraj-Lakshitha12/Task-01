package lk.ijse.gdse.taskbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO {
    private String id;
    private String code;
    private String name;
    private String category;
    private String unit;
    private String status;
}
