package lk.ijse.gdse.demo.dto;

public class Supplier {
    private String id;
    private String supplierCode;
    private String name;
    private String address;
    private String status;

    public Supplier() {
    }

    public Supplier(String id, String supplierCode, String name, String address, String status) {
        this.id = id;
        this.supplierCode = supplierCode;
        this.name = name;
        this.address = address;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
