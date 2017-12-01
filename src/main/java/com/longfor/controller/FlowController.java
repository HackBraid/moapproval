package com.longfor.controller;

import com.longfor.bean.*;
import com.longfor.model.DohalfData;
import com.longfor.service.DohalfService;
import com.longfor.service.FlowDataService;
import com.longfor.util.CommonUtil;
import com.longfor.util.RedisSourceFactory;
import com.longfor.util.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.validation.Valid;
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

    @Autowired
    FlowDataService flowDataService;
    /**
     * 获取移动审批列表 status 获取列表的状态0表示代办1表示已办
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Result<String> list(@RequestBody @Valid DohalfParamBean dohalfParamBean) throws Exception{
        Page<DohalfData> pageList=dohalfService.findList(dohalfParamBean);
        MessageBean messageBean=new MessageBean();
        messageBean.setList(pageList.getContent());
        messageBean.setTotal(pageList.getTotalElements());
        return ResultUtil.success(messageBean);
    }

    /**
     * 获取我的发起
     */
    @RequestMapping(value = "/sponsorlist",method = RequestMethod.POST)
    public Result<String> sponsorlist(@RequestBody @Valid DohalfParamBean dohalfParamBean) throws Exception{
        Page<DohalfData> pageList=dohalfService.findList(dohalfParamBean);
        MessageBean messageBean=new MessageBean();
        messageBean.setList(pageList.getContent());
        messageBean.setTotal(pageList.getTotalElements());
        return ResultUtil.success(messageBean);
    }

    /**
     * 获取业务详情接口
     */
    @RequestMapping(value = "/business",method = RequestMethod.POST)
    public Result<String> business(@RequestBody @Valid FlowParamBean flowParamBean) throws Exception{
        try{
            RedisSourceFactory redisSourceFactory=new RedisSourceFactory(redisBusiness.getADDR(),redisBusiness.getPORT(),redisBusiness.getAUTH());
            Jedis jedis=redisSourceFactory.getRedis(redisBusiness.getADDR());
            String data=jedis.get("business_" + flowParamBean.getSystemNo() + "_" + flowParamBean.getFlowNo()+"_"+flowParamBean.getUsercode());
            if(StringUtils.isNotEmpty(data)){
                return ResultUtil.success("");
            }else{
                return ResultUtil.success(flowDataService.findBusiness(flowParamBean.getSystemNo(),flowParamBean.getFlowNo(),flowParamBean.getUsercode()));
            }
        }catch (Exception e){
            return ResultUtil.success(flowDataService.findBusiness(flowParamBean.getSystemNo(),flowParamBean.getFlowNo(),flowParamBean.getUsercode()));
        }

    }

    /**
     * 获取流程信息
     */
    @RequestMapping(value = "/flow",method = RequestMethod.POST)
    public Result<String> flow(@RequestBody @Valid FlowParamBean flowParamBean) throws Exception{
        try{
            RedisSourceFactory redisSourceFactory=new RedisSourceFactory(redisBusiness.getADDR(),redisBusiness.getPORT(),redisBusiness.getAUTH());
            Jedis jedis=redisSourceFactory.getRedis(redisBusiness.getADDR());
            String data=jedis.get("flow_" + flowParamBean.getFlowNo() + "_" + flowParamBean.getSystemNo());
            if(StringUtils.isNotEmpty(data)){
                return ResultUtil.success("");
            }else{
                return ResultUtil.success(flowDataService.findFlowData(flowParamBean.getFlowNo(),flowParamBean.getSystemNo()));
            }
        }catch (Exception e){
            return ResultUtil.success(flowDataService.findFlowData(flowParamBean.getFlowNo(),flowParamBean.getSystemNo()));
        }
    }

    /**
     *  审批操作接口
     */
    @RequestMapping(value = "/approve",method = RequestMethod.POST)
    public Result<String> updateFlow(@RequestBody @Valid ApprovalBean approvalBean) throws Exception{
        try{
            RedisSourceFactory redisSourceFactory=new RedisSourceFactory(redisQuene.getADDR(),redisQuene.getPORT(),redisQuene.getAUTH());
            Jedis jedis=redisSourceFactory.getRedis(redisQuene.getADDR());
            jedis.lpush("APPROVAL_QUEUE", CommonUtil.toJson(approvalBean));
        }catch (Exception e){
            logger.info("更新redis异常：APPROVAL_QUEUE"+e.getMessage());
        }
        dohalfService.updateTodoId(approvalBean.getTodoId(),4);
        return  ResultUtil.success("审批操作成功");
    }

    /**
     *  更新待办状态接口
     */
    @RequestMapping(value = "/update-dohalf-status",method = RequestMethod.POST)
    public Result<String> updateDohalfStatus(@RequestBody @Valid ApprovalBean approvalBean) throws Exception{
        if(approvalBean.getStatus()==null){
            return  ResultUtil.error(-1,"审批参数不能为空");
        }
        dohalfService.updateTodoId(approvalBean.getTodoId(),approvalBean.getStatus());
        return  ResultUtil.success("审批操作成功");
    }


}
