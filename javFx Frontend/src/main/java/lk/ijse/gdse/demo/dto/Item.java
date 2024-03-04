package lk.ijse.gdse.demo.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("id")
    @Expose(serialize = true, deserialize = true)
    private String id;
    private String code;
    private String name;
    private String category;
    private String unit;
    private String status;
}
