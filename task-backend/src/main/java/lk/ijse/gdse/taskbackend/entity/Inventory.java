package lk.ijse.gdse.taskbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Temporal(TemporalType.DATE)
    private Date receivedDate;

    private int receivedQty;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus = ApprovalStatus.Pending;

    @Enumerated(EnumType.STRING)
    private Status status = Status.Active;

    public enum ApprovalStatus {
        Pending,
        Approved
    }

    public enum Status {
        Active,
        Inactive
    }
}
