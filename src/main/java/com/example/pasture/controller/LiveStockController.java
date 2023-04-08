package com.example.pasture.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pasture.mapper.ILiveStockMapper;
import com.example.pasture.mapper.RailMapper;
import com.example.pasture.model.Livestock;
import com.example.pasture.model.Rail;
import com.example.pasture.model.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品相关操作Controller
 */
@RestController
@RequestMapping("/user")
public class LiveStockController {
    @Autowired
    private ILiveStockMapper mapper;

    @Autowired
    private RailMapper railMapper;

    @RequestMapping("/addLivestock")
    public ResultData<String> addLivestock(@RequestBody Livestock request) {
        if (null == request) {
            return ResultData.fail(405, "请求为空");
        }

        if (request.getDeviceId().equals("")) {
            return ResultData.fail(405, "deviceId为空");
        }
        int insert = mapper.insert(request);
        return ResultData.success("添加数据成功");
    }

    @RequestMapping("/queryLivestock")
    public ResultData<Livestock> queryCommodities(@RequestBody Livestock deviceId) {
        Livestock livestock = mapper.selectById(deviceId.getDeviceId());
        if (livestock == null) {
            return ResultData.fail(405, "数据为空");
        }
        return ResultData.success(livestock);
    }

    @RequestMapping(value = "/queryLivestockAll", method = RequestMethod.POST)
    public ResultData<List<Livestock>> queryLivestockAll() {
        QueryWrapper<Livestock> queryWrapper = new QueryWrapper<>();
        List<Livestock> livestock = mapper.selectList(queryWrapper);
        if (null == livestock) {
            return ResultData.fail(405, "数据为空");
        }
        return ResultData.success(livestock);
    }


    @RequestMapping("/queryRailAll")
    public ResultData<List<Rail>> queryRailAll(@RequestBody Rail rail) {
        QueryWrapper<Rail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", rail.getUserId());
        queryWrapper.eq("rail_number", rail.getRailNumber());
        queryWrapper.orderByAsc("rail_sort");
        List<Rail> rails = railMapper.selectList(queryWrapper);
        if (null == rails) {
            return ResultData.fail(405, "数据为空");
        }
        return ResultData.success(rails);
    }

    @RequestMapping("/addRailAll")
    public ResultData addRailAll(@RequestBody JSONObject json) {
        JSONArray list = json.getJSONArray("list");
        List<Rail> rails = list.toJavaList(Rail.class);
        for (Rail r : rails) {
            r.setRailNumber(Integer.valueOf(json.getString("railNumber")));
            r.setUserId(Integer.valueOf(json.getString("userId")));
            railMapper.insert(r);
        }
        if (null == json) {
            return ResultData.fail(405, "数据为空");
        }
        return ResultData.success("添加数据成功");
    }
}
