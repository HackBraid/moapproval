package com.longfor.service;

import com.longfor.model.DohalfData;
import com.longfor.util.CommonUtil;
import com.longfor.util.HttpUtils;
import com.longfor.util.WebServiceInterface;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 17/4/26.
 */

@Service
public class ApprovalService {
    private final static Logger logger= LoggerFactory.getLogger(ApprovalService.class);

    /*
    这里的审批操作方法名要严格按照审批待办的系统编号来命名
    例子：
    如果调用OA系统的审批操作接口，则方法名应命名为：OA_Approval

    不按此原则名则无法调用到此方法
     */

    @Autowired
    private SystemUrlService systemUrlService;

    /**
     *全面预算接口（通过和驳回）
     *
     * @return
     */
    public String PLANNING_Approval(String jsonData,DohalfData dohalfData) throws Exception{

//        http://planningtest.longhu.net:19000/process/processrs.do?action=approve

//        审批通过
//        map.put("action", "approve");
//        map.put("instanceId", "//审批实例ID");
//        map.put("WorkItemID", "//workID");
//        map.put("approveOpinion", "//审批意见");
//        map.put("user", "//操作人");

//        审批驳回
//        map.put("action", "action=reject");
//        map.put("instanceId", "//审批实例ID");
//        map.put("workId", "//workID");
//        map.put("rejectOpinion", "//审批意见");
//        map.put("user", "//操作人");

        // 将json转为map
        Map<String,Object> mapData=JSONObject.fromObject(jsonData);
        //设置头部信息
        Map<String,Object> mapHeader=new HashMap<String,Object>();
        mapHeader.put("HYPERION","CE2300175C38703769F9964D7F1F206A");
        //获取url
        String  url=systemUrlService.findBySystemNo("PLANNING").getString("approvalUrl");

        //获取返回接口详情
        String data= HttpUtils.getData(url,mapData,mapHeader);
        try{
            Map<String,Object> businessInfo= CommonUtil.fromJson(data);
            if("0".equals(businessInfo.get("code"))){
                return businessInfo.get("data").toString();
            }else{
                throw new Exception(businessInfo.get("msg").toString());
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * OA系统 审批接口
     * @param jsonData
     * @return
     * @throws Exception
     */
    public String OA_Approval(String jsonData,DohalfData dohalfData) throws Exception{
        // 将json转为map
        Map<String,Object> mapData=JSONObject.fromObject(jsonData);

//        map.put("flow_no", apprb.getFlwo_no());
//        map.put("operation_type", apprb.getOp_type());
//        map.put("operation_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        map.put("approve_user", apprb.getAppr_userName());
//        map.put("node_no", apprb.getTodoid());
//        map.put("operation_from", apprb.getDevice());
//        map.put("applicationId", "WorkRelationMultiLib");
//        map.put("applicationKey", "05a3df1c-d361-4968-b909-2cd8c3cfe759");
//        map.put("taskUrl", dd.getOa_url());

        //设置头部信息
        Map<String,Object> mapHeader=new HashMap<String,Object>();
        mapHeader.put("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

        //获取url
        String  url=systemUrlService.findBySystemNo("OA").getString("approvalUrl");
        //获取返回接口详情
        String data= HttpUtils.getData(url,mapData,mapHeader);
        try{
            JSONObject businessInfo=JSONObject.fromObject(data);
            if("0".equals(businessInfo.get("code"))){
                return businessInfo.get("data").toString();
            }else{
                throw new Exception(businessInfo.getString("msg"));
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     *成本系统 审批接口
     * @param jsonData
     * @return
     * @throws Exception
     */
    public String COST_Approval(String jsonData,DohalfData dohalfData) throws Exception{
        // 将json转为map
        Map<String,Object> mapData=JSONObject.fromObject(jsonData);
        //获取url
        String  url=systemUrlService.findBySystemNo("COST").getString("approvalUrl");
        Object data= WebServiceInterface.getData(url,"WorkflowAction",
                                                        mapData.get("runnoteId").toString(),
                                                        mapData.get("flowNo").toString(),
                                                        mapData.get("userName").toString(),
                                                        mapData.get("actionType").toString(),
                                                        mapData.get("content").toString(),  
                                                        mapData.get("device").toString());
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if("true".equals(flowInfo.get("success"))){
                return flowInfo.get("data").toString();
            }else{
                throw new Exception(flowInfo.getString("message"));
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     *招采系统 审批接口
     * @param jsonData
     * @return
     * @throws Exception
     */
    public String PO_Approval(String jsonData,DohalfData dohalfData) throws Exception{
        // 将json转为map
        Map<String,Object> mapData=JSONObject.fromObject(jsonData);
        //获取url
        String  url=systemUrlService.findBySystemNo("PO").getString("approvalUrl");
        Object data= WebServiceInterface.getData(url,"WorkflowAction",
                mapData.get("runnoteId").toString(),
                mapData.get("flowNo").toString(),
                mapData.get("userName").toString(),
                mapData.get("actionType").toString(),
                mapData.get("content").toString(),
                mapData.get("device").toString());
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if("true".equals(flowInfo.get("success"))){
                return flowInfo.get("data").toString();
            }else{
                throw new Exception(flowInfo.getString("message"));
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     *CRM系统 审批接口
     * @param jsonData
     * @return
     * @throws Exception
     */
    public String MYSOFTCRM_Approval(String jsonData,DohalfData dohalfData) throws Exception{
        // 将json转为map
        Map<String,Object> mapData=JSONObject.fromObject(jsonData);
        //获取url
        String  url=systemUrlService.findBySystemNo("MYSOFTCRM").getString("approvalUrl");
        Object data= WebServiceInterface.getData(url,"approve",
                mapData.get("flwo_no").toString(),
                mapData.get("op_type").toString(),
                mapData.get("op_content").toString(),
                //时间格式：yyyy-MM-dd HH:mm:ss
                mapData.get("date").toString(),
                mapData.get("appr_userName").toString(),
                mapData.get("runnote_id").toString(),
                //操作来源：移动端、PC端
                mapData.get("device").toString());
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if("0".equals(flowInfo.get("status"))){
                return flowInfo.get("msg").toString();
            }else{
                throw new Exception(flowInfo.getString("msg"));
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     *GCPROJ工程系统 审批接口
     * @param jsonData
     * @return
     * @throws Exception
     */
    public String GCPROJ_Approval(String jsonData,DohalfData dohalfData) throws Exception{
        // 将json转为map
        Map<String,Object> mapData=JSONObject.fromObject(jsonData);
        //获取url
        String  url=systemUrlService.findBySystemNo("GCPROJ",dohalfData.getBusiness_type()).getString("approvalUrl");
        Object data= WebServiceInterface.getData(url,"approve",
                mapData.get("flwo_no").toString(),
                mapData.get("op_type").toString(),
                mapData.get("op_content").toString(),
                //时间格式：yyyy-MM-dd HH:mm:ss
                mapData.get("date").toString(),
                mapData.get("appr_userName").toString(),
                mapData.get("runnote_id").toString(),
                //操作来源：移动端、PC端
                mapData.get("device").toString());
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if("0".equals(flowInfo.get("status"))){
                return flowInfo.get("msg").toString();
            }else{
                throw new Exception(flowInfo.getString("msg"));
            }
        }catch (Exception e){
            throw e;
        }
    }
    
    /**
     * 探亲机票审批  审批接口
     * @param jsonData
     * @return
     * @throws Exception
     */
    public String TRAVELMGT_Approval(String jsonData,DohalfData dohalfData) throws Exception{
        // 将json转为map
        Map<String,Object> mapData=JSONObject.fromObject(jsonData);
		
        //设置头部信息
        Map<String,Object> mapHeader=new HashMap<String,Object>();
        mapHeader.put("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

        //获取url
        String  url=null;
        if(dohalfData.getBusiness_type().equals("NN_FT")){
        	//差旅获取业务信息
        	url=systemUrlService.findBySystemNo("TRAVELMGT","NN_TA").getString("approvalUrl");
        }else{
        	//探亲机票申请业务信息
        	url=systemUrlService.findBySystemNo("TRAVELMGT","NN_FT").getString("approvalUrl");
        }
        
        //获取返回接口详情
        String data= HttpUtils.getData(url,mapData,mapHeader);
        try{
            JSONObject businessInfo=JSONObject.fromObject(data);
            if("0".equals(businessInfo.get("code"))){
                return businessInfo.get("data").toString();
            }else{
                throw new Exception(businessInfo.getString("message"));
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 资金平台系统  审批接口
     * @param jsonData
     * @return
     * @throws Exception
     */
    public String CASHFLOW_Approval(String jsonData,DohalfData dohalfData) throws Exception{
        //获取url
        String  url=systemUrlService.findBySystemNo("CASHFLOW").getString("approvalUrl");

//      JSONObject json = new JSONObject();
//    	json.put("runtid", "Activity6");
//		json.put("runflowid", "d30efaf9-545c-4d34-ad8a-c53571401ca6");
//		json.put("userName", "lizhen");
//		json.put("actionType", 1);
//		json.put("Comment", "同意");
//		json.put("device", "移动端");
        Object data= WebServiceInterface.getData3(url,"http://accountMobileApproveInterface.isoftstone.com/","accountMobileApprove",jsonData);
    	
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if("true".equals(flowInfo.get("success"))){
                return flowInfo.get("data").toString();
            }else{
                throw new Exception(flowInfo.getString("msg"));
            }
        }catch (Exception e){
            throw e;
        }
    }
    
    /**
     * 竟优审批  审批接口
     * @param jsonData
     * @return
     * @throws Exception
     */
    public String NCOST_Approval(String jsonData,DohalfData dohalfData) throws Exception{
        // 将json转为map
        Map<String,Object> mapData=JSONObject.fromObject(jsonData);
        //获取url
        String  url=systemUrlService.findBySystemNo("NCOST").getString("approvalUrl");
        //获取返回接口详情
        Object data= WebServiceInterface.getData(url,"WorkflowAction",
                                                        mapData.get("runtid").toString(),
                                                        mapData.get("flowNo").toString(),
                                                        mapData.get("userName").toString(),
                                                        mapData.get("actionType").toString(),
                                                        mapData.get("content").toString(),  
                                                        mapData.get("device").toString());
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if("true".equals(flowInfo.get("success"))){
                return flowInfo.get("data").toString();
            }else{
                throw new Exception(flowInfo.getString("message"));
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 全景计划_LFPROJ  审批接口
     * @param jsonData
     * @return
     * @throws Exception
     */
    public String LFPROJ_Approval(String jsonData,DohalfData dohalfData) throws Exception{
    	// 将json转为map
    	Map<String,Object> mapData=JSONObject.fromObject(jsonData);
        //获取url
        String  url=systemUrlService.findBySystemNo("LFPROJ").getString("approvalUrl");
        //获取返回接口详情
        Object data= WebServiceInterface.getData3(url,"http://server.webservice.wpeak.com/","approve",
										        		mapData.get("flowNo").toString(),
										                mapData.get("operationType").toString(),
										                mapData.get("operationContent").toString(),
										                mapData.get("operationTime").toString(),
										                mapData.get("approveUser").toString(),
										                mapData.get("nodeNo").toString(),
										                mapData.get("operationFrom").toString());
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if("0".equals(flowInfo.get("status"))){
                return flowInfo.get("data").toString();
            }else{
                throw new Exception(flowInfo.getString("msg"));
            }
        }catch (Exception e){
            throw e;
        }
    }
    
    /**
     * 全景计划_LFPROJ  审批接口
     * @param jsonData
     * @return
     * @throws Exception
     */
    public String PROJPLAN_Approval(String jsonData,DohalfData dohalfData) throws Exception{
    	// 将json转为map
    	Map<String,Object> mapData=JSONObject.fromObject(jsonData);
        //获取url
        String  url=systemUrlService.findBySystemNo("PROJPLAN").getString("approvalUrl");
        //获取返回接口详情
        Object data= WebServiceInterface.getData3(url,"http://server.webservice.wpeak.com/","approve",
										        		mapData.get("flowNo").toString(),
										                mapData.get("operationType").toString(),
										                mapData.get("operationContent").toString(),
										                mapData.get("operationTime").toString(),
										                mapData.get("approveUser").toString(),  
										                mapData.get("nodeNo").toString(),
										                mapData.get("operationFrom").toString());
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if("0".equals(flowInfo.get("status"))){
                return flowInfo.get("data").toString();
            }else{
                throw new Exception(flowInfo.getString("msg"));
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     *LONGCITY龙城系统 审批接口
     * @param jsonData
     * @return
     * @throws Exception
     */
    public String LONGCITY_Approval(String jsonData,DohalfData dohalfData) throws Exception{
        System.out.println(jsonData);
        //设置header
        Map<String,Object> mapHeader= new HashMap<String,Object>();
        mapHeader.put("version","v1");
        mapHeader.put("platform","app");
        mapHeader.put("token","0246bee409904bada83e59cd5a62161b");
        mapHeader.put("Content-Type","application/json");
        //获取url
        String url=systemUrlService.findBySystemNo("LONGCITY",dohalfData.getBusiness_type()).getString("approvalUrl");
        Object data= HttpUtils.getDataByJson(url,jsonData,mapHeader);
        System.out.println(data);
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if("200".equals(flowInfo.get("status"))){
                return flowInfo.get("result").toString();
            }else{
                throw new Exception(flowInfo.getString("message"));
            }
        }catch (Exception e){
            throw e;
        }
    }




    /**
     * GUC  审批接口
     * @param jsonData
     * @return
     * @throws Exception
     */
    public String GUC_Approval(String jsonData,DohalfData dohalfData) throws Exception{
    	// 将json转为map
    	Map<String,Object> mapData=JSONObject.fromObject(jsonData);
        //获取url
        String  url=systemUrlService.findBySystemNo("GUC").getString("approvalUrl");
        //获取返回接口详情
        Object data= WebServiceInterface.getData(url,"SubmitForm",
										        		mapData.get("operationType").toString(),
										                mapData.get("approveUser").toString(),
										                mapData.get("flowNo").toString(),
										                mapData.get("todoId").toString(),
										                mapData.get("operationContent").toString());
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if(flowInfo != null){
                return flowInfo.getString("message");
            }else{
                throw new Exception("执行失败");
            }
        }catch (Exception e){
            throw e;
        }
    }
    
    /**
     * PRODUCT_产品平台  审批接口
     * @param jsonData
     * @return
     * @throws Exception
     */
    public String PRODUCT_Approval(String jsonData,DohalfData dohalfData) throws Exception{
    	// 将json转为map
    	Map<String,Object> mapData=JSONObject.fromObject(jsonData);
        //获取url
        String  url=systemUrlService.findBySystemNo("PRODUCT").getString("approvalUrl");
        Object data= WebServiceInterface.getData(url,"WorkflowAction",
										        		mapData.get("flowNo").toString(),
										                mapData.get("runtid").toString(),
										                mapData.get("approveUser").toString(),
										                mapData.get("operationType").toString(),
										                mapData.get("operationContent").toString(),
										                mapData.get("device").toString());
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if("0".equals(flowInfo.get("status"))){
                return flowInfo.getString("business_data");
            }else{
                throw new Exception(flowInfo.getString("msg"));
            }
        }catch (Exception e){
            throw e;
        }
    }
    
    /**
     * 商业成本接口 审批接口
     * @param jsonData
     * @return
     * @throws Exception
     */
    public String SYCOST_Approval(String jsonData,DohalfData dohalfData) throws Exception{
    	// 将json转为map
    	Map<String,Object> mapData=JSONObject.fromObject(jsonData);
        //获取url
        String  url=systemUrlService.findBySystemNo("SYCOST").getString("approvalUrl");
        //获取返回接口详情
        Object data= WebServiceInterface.getData(url,"WorkflowAction",
										        		mapData.get("flowNo").toString(),
										                mapData.get("runtid").toString(),
										                mapData.get("approveUser").toString(),
										                mapData.get("operationType").toString(),
										                mapData.get("operationContent").toString(),
										                mapData.get("device").toString());
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if(flowInfo.getBoolean("success")){
                return flowInfo.getString("data");
            }else{
                throw new Exception(flowInfo.getString("message"));
            }
        }catch (Exception e){
            throw e;
        }
    }
    
    /**
     * 商业租赁接口 审批接口
     * @param jsonData
     * @return
     * @throws Exception
     */
    public String SyRent_Approval(String jsonData,DohalfData dohalfData) throws Exception{
    	// 将json转为map
    	Map<String,Object> mapData=JSONObject.fromObject(jsonData);
        //获取url
        String  url=systemUrlService.findBySystemNo("SyRent").getString("approvalUrl");
        Object data= WebServiceInterface.getData(url,"WorkflowAction",
										        		mapData.get("flowNo").toString(),
										                mapData.get("runtid").toString(),
										                mapData.get("approveUser").toString(),
										                mapData.get("operationType").toString(),
										                mapData.get("operationContent").toString(),
										                mapData.get("device").toString());
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if(flowInfo.getBoolean("success")){
                return flowInfo.getString("data");
            }else{
                throw new Exception(flowInfo.getString("message"));
            }
        }catch (Exception e){
            throw e;
        }
    }
    
    /**
     * 物业成本统 审批接口
     * @param jsonData
     * @return
     * @throws Exception
     */
    public String WyCost_Approval(String jsonData,DohalfData dohalfData) throws Exception{
    	// 将json转为map
    	Map<String,Object> mapData=JSONObject.fromObject(jsonData);
        //获取url
        String  url=systemUrlService.findBySystemNo("WyCost").getString("approvalUrl");
        Object data= WebServiceInterface.getData(url,"WorkflowAction",
										        		mapData.get("flowNo").toString(),
										                mapData.get("runtid").toString(),
										                mapData.get("approveUser").toString(),
										                mapData.get("operationType").toString(),
										                mapData.get("operationContent").toString(),
										                mapData.get("device").toString());
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if(flowInfo.getBoolean("success")){
                return flowInfo.getString("data");
            }else{
                throw new Exception(flowInfo.getString("message"));
            }
        }catch (Exception e){
            throw e;
        }
    }
    
    /**
     * 物业成本统 审批接口
     * @param jsonData
     * @return
     * @throws Exception
     */
    public String ITOM_Approval(String jsonData,DohalfData dohalfData) throws Exception{
    	// 将json转为map
    	Map<String,Object> mapData=JSONObject.fromObject(jsonData);
        //获取url
        String  url=systemUrlService.findBySystemNo("ITOM",dohalfData.getBusiness_type()).getString("approvalUrl");
        String method = null;
        if("FAGZLQR".equals(dohalfData.getBusiness_type())){
        	method = "SubmitProjectFlow";
        }else if("XTSX".equals(dohalfData.getBusiness_type())){
        	method = "SubmitReleaseFlow";
        }else{
        	method = "SubmitAcceptFlow";
        }
        Object data= WebServiceInterface.getData(url,method,
        												mapData.get("operationType").toString(),
        												mapData.get("approveUser").toString(),
										        		mapData.get("flowNo").toString(),
										                mapData.get("todoId").toString(),
										                mapData.get("operationContent").toString());
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if(flowInfo.getBoolean("success")){
                return flowInfo.getString("data");
            }else{
                throw new Exception(flowInfo.getString("message"));
            }
        }catch (Exception e){
            throw e;
        }
    }
}
