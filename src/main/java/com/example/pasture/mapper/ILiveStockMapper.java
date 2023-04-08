package com.example.pasture.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pasture.model.Livestock;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ILiveStockMapper extends BaseMapper<Livestock> {

}