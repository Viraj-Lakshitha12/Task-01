package lk.ijse.gdse.demo.dto;

import java.time.LocalDate;
import java.util.Date;

public class Inventory {
    private Long id;
    private Item item;
    private LocalDate receivedDate;
    private LocalDate expire_date;
    private int receivedQty;
    private String approvalStatus;
    private String status;

    public Inventory() {
    }

    public Inventory(Long id, Item item, LocalDate receivedDate, LocalDate expire_date, int receivedQty, String approvalStatus, String status) {
        this.id = id;
        this.item = item;
        this.receivedDate = receivedDate;
        this.expire_date = expire_date;
        this.receivedQty = receivedQty;
        this.approvalStatus = approvalStatus;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public LocalDate getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(LocalDate expire_date) {
        this.expire_date = expire_date;
    }

    public int getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(int receivedQty) {
        this.receivedQty = receivedQty;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", item=" + item +
                ", receivedDate=" + receivedDate +
                ", expire_date=" + expire_date +
                ", receivedQty=" + receivedQty +
                ", approvalStatus='" + approvalStatus + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
