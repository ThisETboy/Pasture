package com.example.pasture.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("livestock")
public class Livestock {

    /**
     * 指定主键名、主键生产策略
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 设备ID，唯一标识
     */
    @TableField("c_device_id")
    private String deviceId;

    /**
     * 牲畜类型
     */
    @TableField("c_type")
    private Integer type;

    /**
     * 牲畜类型名称
     */
    @TableField("c_type_name")
    private String typeName;

    /**
     * 秘钥暂时保留
     */
    @TableField("c_secret_key")
    private String secret_key;

    /**
     * 单位代字
     */
    @TableField("c_unit_pronoun")
    private String unitPronoun;

    /**
     * 经度
     */
    @TableField("c_longitude")
    private Double longitude;

    /**
     * 纬度
     */
    @TableField("c_latitude")
    private Double latitude;

    /**
     * 上传时间
     */
    @TableField("c_updata_time")
    private Date updataTime;

    /**
     * 温度
     */
    @TableField("c_temperature")
    private Double temperature;

    /**
     * 步数
     */
    @TableField("c_step")
    private Long step;
}
