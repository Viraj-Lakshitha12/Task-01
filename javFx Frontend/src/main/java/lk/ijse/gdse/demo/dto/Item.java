package lk.ijse.gdse.demo.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("id")
    @Expose(serialize = true, deserialize = true)
    private String id;
    private String code;
    private String name;
    private Category category;
    private Unit unit;
    private String status;

    public Item() {
    }

    public Item(String id, String code, String name, Category category, Unit unit, String status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.category = category;
        this.unit = unit;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", unit=" + unit +
                ", status='" + status + '\'' +
                '}';
    }
}
