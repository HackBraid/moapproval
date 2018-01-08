package com.longfor.controller;

import com.longfor.bean.*;
import com.longfor.model.SysWorkitemcenterEntity;
import com.longfor.service.*;
import com.longfor.util.CommonUtil;
import com.longfor.util.RedisSourceFactory;
import com.longfor.util.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by issuser on 2017/11/1.
 */
@RestController
@RequestMapping("/flowapi")
public class FlowController {

    private final static Logger logger= LoggerFactory.getLogger(FlowController.class);

    @Autowired
    DohalfService dohalfService;

//    @Autowired
//    RedisUtil redisUtil;

    @Autowired
    RedisQuene redisQuene;

    @Autowired
    RedisBusiness redisBusiness;


    /**
     * 获取移动审批列表 status 获取列表的状态0表示代办1表示已办
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Result<String> list(@RequestBody @Valid SysWorkitemcenterBean sysWorkitemcenterBean) throws Exception{
        Page<SysWorkitemcenterEntity> pageList=dohalfService.findList(sysWorkitemcenterBean);
        MessageBean messageBean=new MessageBean();
        messageBean.setList(pageList.getContent());
        messageBean.setTotal(pageList.getTotalElements());
        return ResultUtil.success(messageBean);
    }

}
