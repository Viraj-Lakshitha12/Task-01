package lk.ijse.gdse.demo.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Unit {
    @SerializedName("id")
    @Expose(serialize = true, deserialize = true)
    private String id;


    @SerializedName("code")
    private String code;

    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private String status;

    public Unit() {
    }

    public Unit(String id, String code, String name, String status) {
        this.id = id;
        this.code = code;
        this.name = name;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
