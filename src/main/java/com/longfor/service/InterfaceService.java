package com.longfor.service;

import com.longfor.bean.ApprovalBean;
import com.longfor.bean.FlowParamBean;
import com.longfor.util.HttpUtils;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * Created by issuser on 2017/12/13.
 */
@Service
public class InterfaceService {

    @Value("${APPROVAL_HANDLE}")
    private String APPROVAL_HANDLE;

    public String getApprove( ApprovalBean approvalBean){
        JSONObject jsonData=new JSONObject();
        jsonData.put("todoId",approvalBean.getTodoId());
        jsonData.put("systemNo",approvalBean.getSystemNo());
//        JSONObject data1=JSONObject.fromObject(approvalBean.getData());
//        jsonData.put("data",data1);
        try{
            String data= HttpUtils.getDataByJson(APPROVAL_HANDLE+"approve",jsonData.toString(),null);
            System.out.println(data);
            JSONObject flowInfo=JSONObject.fromObject(data);
            if("0".equals(flowInfo.getString("code"))){
                return flowInfo.get("data").toString();
            }else{
                return "-1";
            }
        }catch (Exception e){
           e.printStackTrace();
        }
        return "";
    }

    public String getBusiness(FlowParamBean flowParamBean){
        JSONObject jsonData=new JSONObject();
        jsonData.put("flowNo",flowParamBean.getFlowNo());
        jsonData.put("systemNo",flowParamBean.getSystemNo());
        try{
            String data= HttpUtils.getDataByJson(APPROVAL_HANDLE+"business",jsonData.toString(),null);
            JSONObject flowInfo=JSONObject.fromObject(data);
            if("0".equals(flowInfo.getString("code"))){
                return flowInfo.get("data").toString();
            }else{
                return "-1";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public String getFlow(FlowParamBean flowParamBean){
        JSONObject jsonData=new JSONObject();
        jsonData.put("flowNo",flowParamBean.getFlowNo());
        jsonData.put("status","1");
        try{
            String data= HttpUtils.getDataByJson(APPROVAL_HANDLE+"flow",jsonData.toString(),null);
            JSONObject flowInfo=JSONObject.fromObject(data);
            if("0".equals(flowInfo.getString("code"))){
                return flowInfo.get("data").toString();
            }else{
                return "-1";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

}
