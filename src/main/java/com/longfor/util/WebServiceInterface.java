package com.longfor.util;

import net.sf.json.JSONObject;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by issuser on 2017/11/13.
 */
public class WebServiceInterface {


    /**
     * 通过Qname调用
     * @param url
     * @param method
     * @param params
     * @return
     * @throws Exception
     */
    public static Object getData(String url, String method, String... params) throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(url);
        QName qName = new QName("http://tempuri.org/", method);
        Object[] objects = new Object[0];
        objects = client.invoke(qName, params);
        if (objects != null && objects.length > 0) {
            return objects[0];
        } else {
            return "";
        }
    }

    /**
     * 不通过Qname调用
     * @param url
     * @param method
     * @param params
     * @return
     * @throws Exception
     */
    public static Object getData2(String url, String method, String... params) throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(url);
        Object[] objects = new Object[0];
        objects = client.invoke(method, params);
        if (objects != null && objects.length > 0) {
            return objects[0];
        } else {
            return "";
        }
    }


    /**
     * 通过Qname调用(通过不同nanamspace调用)
     * @param url
     * @param method
     * @param params
     * @return
     * @throws Exception
     */
    public static Object getData3(String url,String namespace, String method, String... params) throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(url);
        QName qName = new QName(namespace, method);
        Object[] objects = new Object[0];
        objects = client.invoke(qName, params);
        if (objects != null && objects.length > 0) {
            return objects[0];
        } else {
            return "";
        }
    }


    public static void main(String[] args)throws Exception {
//        Object data= WebServiceInterface.getData(url,"WorkflowAction",
//                mapData.get("runnoteId").toString(),
//                mapData.get("flowNo").toString(),
//                mapData.get("userName").toString(),
//                mapData.get("actionType").toString(),
//                mapData.get("content").toString(),
//                mapData.get("device").toString());
//      成本审批
//      System.out.println(JSONObject.fromObject(getData("http://192.168.33.95:9090/com.longfor.esb.DC.COST.WorkflowWebService?wsdl","WorkflowAction","4570020","1794147","zhanghaocheng","APPROVE","同意","phone")));
//      成本详情
//      System.out.println(JSONObject.fromObject(getData("http://192.168.33.95:9090/com.longfor.esb.DC.COST.BusinessWebService?wsdl","GetBusinessInfo","1794147","4570020","admin")));
//      工程系统业务详情  YJJHSPG业务类型
//      System.out.println(getData("http://192.168.10.49:7101/gcproj/AppYJBusinessServicePort?wsdl","getYJBusinessDesc","349230bd-5d5e-455c-aa6d-e07ca4e155c1","cuihz"));
//      工程系统业务详情  YJFKSPG业务类型
//      System.out.println(getData("http://192.168.10.37:7103/gcproj/AppYJBusinessServicePort?wsdl","getYJBusinessDesc","3e0f4c57-8318-4acd-b4a3-b01e9a30c1cb","zhangyongjun"));
//      工程系统业务详情  其他业务类型
//      System.out.println(getData("http://192.168.33.95:9090/com.longfor.esb.DC.PROJPLAN.AppBusinessServicePort?wsdl","getBusinessDesc","d225efeb-5995-4a2b-864c-f70f08f80409","zuoenze"));
//      工程系统审批
//      System.out.println(JSONObject.fromObject(getData2("http://192.168.10.49:7101/gcproj/AppAproveServicePort?wsdl","approve","349230bd-5d5e-455c-aa6d-e07ca4e155c1","0","通过了","2017-11-23 18:00:04","cuihz","SH1","移动端")));
//      crm审批
//      System.out.println(getData("http://192.168.33.95:9090/com.longfor.esb.DC.MYSOFTCRM.WF_MobileSP?wsdl","approve","416baef4-55cf-e711-9f2d-005056ad02c3","0","","2017-11-22 18:40:04","lixping","72e89013-56cf-e711-9f2d-005056ad02c3","移动端"));
//      crm业务
//      System.out.println(JSONObject.fromObject(getData("http://192.168.33.95:9090/com.longfor.esb.DC.MYSOFTCRM.MobileSP?wsdl","GetBusinessDataForWF","交房标准审批","416baef4-55cf-e711-9f2d-005056ad02c3")));
//		资金平台系统获取详情
//    	System.out.println(JSONObject.fromObject(getData3("http://192.168.33.95:9090/com.longfor.esb.DC.CASHFLOW.mobileGetAccountInfo?wsdl","http://mobileGetAccountInfoInterface.isoftstone.com/","mobileGetAccountInfo","36359687-a6e6-4ae0-8e98-920cb47c7a62","zhaoyi")));     
//    	crm业务
//    	System.out.println(JSONObject.fromObject(getData("http://192.168.33.95:9090/com.longfor.esb.DC.COST.WorkflowWebService?wsdl","GetMobileBusinessInfo","1254888","1315074","admin")));
//		龙城业务
//        String jsonStr = "{instanceId:\"95d78fc8-6f67-4808-bea7-31e0d9ea99e9\", userCode:\"songdi\",flag:\"2\"}";//流程id//登录人//专业标识：1、是，2、否
//        //设置头部信息
//        Map<String,Object> mapHeader=new HashMap<String,Object>();
//        mapHeader.put("version","v1");
//        mapHeader.put("platform","app");
//        mapHeader.put("token","0246bee409904bada83e59cd5a62161b");
//        mapHeader.put("Content-Type","application/json");
//        System.out.println(JSONObject.fromObject(HttpUtils.getDataByJson("http://dev-longcity.longfor.com:9080/longcity/services/v1/bpmWorkflow/select",jsonStr,mapHeader)));
//      龙城审批
//        // 将json转为map
//        Map<String,Object> mapData=new HashMap<String,Object>();
//        mapData.put("instanceId","95d78fc8-6f67-4808-bea7-31e0d9ea99e9");//流程id
//        mapData.put("workItemId","444");//节点id
//        mapData.put("toDoId","555");//待办id
//        mapData.put("commentText","333");//评审意见，专业标识为1时，返回json，为2时直接字符串
//        mapData.put("operateType","");//操作类型：1、同意，2、有条件通过，3、驳回，4、否决
//        mapData.put("userCode","liufq");//登录名
        String jsonStr = "{instanceId:\"5492fc4c-7772-45ef-bef9-22dd78e2c3ca\", " +
                "workItemId:\"bf1350e0-35d8-4492-9d9c-bddcd2186239\"," +
//                "toDoId:\"bf1350e0-35d8-4492-9d9c-bddcd2186239\"," +
                "commentText:\"2\"," +
                "operateType:\"1\"," +
                "userCode:\"songdi\"}";
        //设置头部信息
        Map<String,Object> mapHeader=new HashMap<String,Object>();
        mapHeader.put("version","v1");
        mapHeader.put("platform","app");
        mapHeader.put("token","0246bee409904bada83e59cd5a62161b");
        mapHeader.put("Content-Type","application/json");
        System.out.println(HttpUtils.getDataByJson("http://dev-longcity.longfor.com:9080/longcity/services/v1/bpmWorkflow/update",jsonStr,mapHeader));
//    	System.out.println(JSONObject.fromObject(getData("http://192.168.33.95:9090/com.longfor.esb.DC.COST.WorkflowWebService?wsdl","GetMobileBusinessInfo","1254888","1315074","admin")));
//    	全景计划
//    	System.out.println(JSONObject.fromObject(getData3("http://192.168.33.95:9090/com.longfor.esb.DC.PROJPLAN.AppBusinessServicePort?wsdl","http://server.webservice.wpeak.com/","getBusinessDesc","748f481e-559c-4210-aa4c-61444ddc60ec","guojing3")));
//    	GUC
//    	System.out.println(JSONObject.fromObject(getData("http://demo.guc.longhu.net/Service/AppApply.asmx?wsdl","GetForm","fengchi","84694a5c-b636-47a0-a664-7c2b538a18b3")));
//    	System.out.println(JSONObject.fromObject(getData3("http://192.168.33.95:9090/com.longfor.esb.DC.PROJPLAN.AppBusinessServicePort?wsdl","http://server.webservice.wpeak.com/","getBusinessDesc","748f481e-559c-4210-aa4c-61444ddc60ec","guojing3")));
       //催办
        System.out.println(JSONObject.fromObject(getData("http://192.168.33.95:9090/com.longfor.esb.DC.BPM.BPMService_Remind?wsdl","StartRemind","a0494b05-845c-416f-a3de-1c8059d50ea4")));
//    	PRODUCT 产品平台接口统获取详情
//    	System.out.println(JSONObject.fromObject(getData("http://192.168.33.84:4080/WebService/GetBusinessInfo.asmx?wsdl","GetBusinessInfo","a92bb38c-076f-49bd-88a8-ab14dc24bcb9","reviewNode","wanglu5")));
//    	商业成本接口统获取详情
//    	System.out.println(JSONObject.fromObject(getData("http://192.168.33.37/lfbcm/WebService/MobileInterface/WorkflowWebService.asmx?wsdl","GetMobileBusinessInfo","a92bb38c-076f-49bd-88a8-ab14dc24bcb9","reviewNode","wanglu5")));
//    	商业租赁统获取详情
//    	System.out.println(JSONObject.fromObject(getData("http://192.168.33.108/rmstest/WebService/MobileInterface/WorkflowWebService.asmx?wsdl","GetMobileBusinessInfo","8607","19478","admin")));
//    	物业成本获取详情
//    	System.out.println(JSONObject.fromObject(getData("http://192.168.33.247/WYCOST1/WebService/MobileInterface/WorkflowWebService.asmx?wsdl","GetMobileBusinessInfo","1467389","2617966","admin")));
//    	物业成本获取详情
    	System.out.println(JSONObject.fromObject(getData("http://itomtest.longhu.net/Services/ApprovalMobile.asmx?wsdl","GetProjectForm","40e271cc-d120-426f-a4eb-770350d710ec","598f7997-b111-47db-a617-aa8d6c8e70cd")));
    	
        
    }

}
