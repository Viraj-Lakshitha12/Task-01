package lk.ijse.gdse.taskbackend.dto;

import lk.ijse.gdse.taskbackend.entity.Category;
import lk.ijse.gdse.taskbackend.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO {
    private Long id;
    private String code;
    private String name;
    private Category category;
    private Unit unit;
    private String status;
}
