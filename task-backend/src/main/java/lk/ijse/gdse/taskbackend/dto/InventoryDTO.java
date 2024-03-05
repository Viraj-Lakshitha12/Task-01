package lk.ijse.gdse.taskbackend.dto;

import lk.ijse.gdse.taskbackend.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryDTO {
    private String id;
    private Item item;
    private LocalDate receivedDate;
    private int receivedQty;
    private String approvalStatus;
    private String status;
}
