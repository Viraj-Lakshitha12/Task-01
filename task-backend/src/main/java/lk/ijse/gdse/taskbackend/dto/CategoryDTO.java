package lk.ijse.gdse.taskbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDTO {
    private Long id;
    private String code;
    private String name;
    private String status;
}
