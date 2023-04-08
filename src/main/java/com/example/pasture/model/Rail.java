package com.example.pasture.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("rail")
public class Rail implements Serializable {
    /**
     * 指定主键名、主键生产策略
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 设备ID，唯一标识
     */
    @TableField("lon")
    private Double longitude;

    /**
     * 牲畜类型
     */
    @TableField("lat")
    private Double latitude;

    @TableField("user_id")
    private Integer userId;
    @TableField("rail_number")
    private Integer railNumber;
    @TableField("rail_sort")
    private Integer railSort;
}
