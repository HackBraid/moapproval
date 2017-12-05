package com.longfor.service;

import com.longfor.model.BusinessData;
import com.longfor.model.DohalfData;
import com.longfor.repository.BusinessDataRepository;
import com.longfor.util.HttpUtils;
import com.longfor.util.WebServiceInterface;
import net.sf.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mac on 17/4/26.
 */

@Service
public class BusinessService {
    private final static Logger logger= LoggerFactory.getLogger(BusinessService.class);

    /*
    这里的获取接入放业务系统的方法名必须按照约定规则命名
    例子：
    如果调用OA系统的获取业务信息接口，则方法名应命名为：OA__BusinessInfo
    不按此原则名则无法调用到此方法
     */
    @Autowired
    private BusinessDataRepository businessDataRepository;

    @Autowired
    private SystemUrlService systemUrlService;
    /**
     * Transactional 注解表示事物操作，有任何异常会回滚
     * @param businessData
     * @throws Exception
     */
    @Transactional
    public void addOrUpdateByEntity(BusinessData businessData) throws Exception{
        businessDataRepository.save(businessData);
    }

    public void addOrUpdateByDohalf(DohalfData dohalfData, String usercode) throws Exception{
        Method method= ReflectionUtils.findMethod(this.getClass(),dohalfData.getSystemNo()+"_BusinessInfo",new Class[]{DohalfData.class});
        String businessJson= (String) ReflectionUtils.invokeMethod(method,this,dohalfData);

        //2.1获取数据库中的business
        BusinessData businessData=this.findByDohalfAndUsercode(dohalfData,usercode);
        if(businessData==null){
            // 组装实体
            businessData = new BusinessData();
            businessData.setAppUsername(usercode);
            businessData.setBznsType(dohalfData.getBusiness_type());
            businessData.setCreateTime(new Date());
            businessData.setUpdateTime(new Date());
            businessData.setFlowNo(dohalfData.getFlowNo());
            businessData.setSystemNo(dohalfData.getSystemNo());
            businessData.setPubUsername(dohalfData.getPubUsername());
        }
        businessData.setUpdateTime(new Date());
        businessData.setBznsData(businessJson);
        addOrUpdateByEntity(businessData);
    }

//    public BusinessData findBySystemNoAndFlowNo(DohalfData dohalfData){
//        List<BusinessData> list=businessRepository.findBySystemNoAndFlowNoAndAppUsername(dohalfData.getSystemNo(),dohalfData.getFlowNo());
//        if(list!=null && list.size()>0){
//            return list.get(0);
//        }
//        return null;
//    }

    public BusinessData findByDohalf(DohalfData dohalfData){
        List<BusinessData> list=businessDataRepository.findBySystemNoAndFlowNoAndAppUsername(dohalfData.getSystemNo(),dohalfData.getFlowNo(),dohalfData.getAppvUsername());
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public BusinessData findByDohalfAndUsercode(DohalfData dohalfData, String usercode){
        List<BusinessData> list=businessDataRepository.findBySystemNoAndFlowNoAndAppUsername(dohalfData.getSystemNo(),dohalfData.getFlowNo(),usercode);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     *全面预算接口获取详情接口
     * @return
     */
    public String PLANNING_BusinessInfo(DohalfData dohalfData) throws Exception{
        // 将json转为map
        Map<String,Object> mapData= new HashMap<String,Object>();
        mapData.put("action","getData");
        mapData.put("instanceId",dohalfData.getFlowNo());
        mapData.put("user",dohalfData.getAppvUsername());
        //设置头部信息
        Map<String,Object> mapHeader=new HashMap<String,Object>();
        mapHeader.put("HYPERION","CE2300175C38703769F9964D7F1F206A");
        //获取url
        String  url=systemUrlService.findBySystemNo("PLANNING").getString("approvalUrl");
        //获取返回接口详情
        String data= HttpUtils.getData(url,mapData,mapHeader);
        try{
            JSONObject businessInfo=JSONObject.fromObject(data);
            if("0".equals(businessInfo.getString("code"))){
                return businessInfo.get("data").toString();
            }else{
                throw new Exception(businessInfo.getString("msg"));
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     *OA获取详情接口
     * jsonData为传的参数和对应的值
     * @return
     */
    public String OA_BusinessInfo(DohalfData dohalfData) throws Exception{
        // 将json转为map
        Map<String,Object> mapData= new HashMap<String,Object>();
        mapData.put("flow_no", dohalfData.getFlowNo());
        mapData.put("applicationId", "WorkRelationMultiLib");
        mapData.put("applicationkey", "05a3df1c-d361-4968-b909-2cd8c3cfe759");
        mapData.put("taskUrl", dohalfData.getOa_url());

        //设置头部信息
        Map<String,Object> mapHeader=new HashMap<String,Object>();
        mapHeader.put("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

        //获取url
        String  url=systemUrlService.findBySystemNo("OA").getString("businessUrl");

        //获取返回接口详情
        String data= HttpUtils.getData(url,mapData,mapHeader);
        try{
            JSONObject businessInfo=JSONObject.fromObject(data);
            if("0".equals(businessInfo.get("status"))){
                return businessInfo.get("business_data").toString();
            }else{
                throw new Exception(businessInfo.getString("message"));
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 成本系统获取详情
     * jsonData为传的参数和对应的值
     * webservice接口
     * @return
     */
    public String COST_BusinessInfo(DohalfData dohalfData) throws Exception{
        //获取url
        String  url=systemUrlService.findBySystemNo("COST").getString("businessUrl");
        Object data= WebServiceInterface.getData(url,"GetBusinessInfo",dohalfData.getRunnote_id(),dohalfData.getFlowNo(),"admin");
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if("0".equals(flowInfo.get("status"))){
                return flowInfo.get("flow_data").toString();
            }else{
                throw new Exception(flowInfo.getString("msg"));
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     *  获取招采业务信息。
     * jsonData为传的参数和对应的值
     * webservice接口
     * @return
     */
    public String PO_BusinessInfo(DohalfData dohalfData) throws Exception{
        //获取url
        String  url=systemUrlService.findBySystemNo("PO").getString("businessUrl");
        Object data= WebServiceInterface.getData(url,"GetMobileBusinessInfo",dohalfData.getRunnote_id(),dohalfData.getFlowNo(),dohalfData.getAppvUsername());
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if("0".equals(flowInfo.get("status"))){
                return flowInfo.get("business_data").toString();
            }else{
                throw new Exception(flowInfo.getString("msg"));
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     *  获取投资平台业务信息。
     * jsonData为传的参数和对应的值
     * webservice接口
     * @return
     */
    public String INVEST_BusinessInfo(DohalfData dohalfData) throws Exception{
        //获取url
        String  url=systemUrlService.findBySystemNo("INVEST").getString("businessUrl");
        Object data= WebServiceInterface.getData(url,"GetBusinessInfo",dohalfData.getRunnote_id(),dohalfData.getFlowNo(),dohalfData.getAppvUsername());
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if("0".equals(flowInfo.get("status"))){
                return flowInfo.get("business_data").toString();
            }else{
                throw new Exception(flowInfo.getString("msg"));
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * crm系统获取详情
     * jsonData为传的参数和对应的值
     * webservice接口
     * @return
     */
    public String MYSOFTCRM_BusinessInfo(DohalfData dohalfData) throws Exception{
        //获取url
        String  url=systemUrlService.findBySystemNo("MYSOFTCRM").getString("businessUrl");
        Object data= WebServiceInterface.getData(url,"GetBusinessDataForWF",dohalfData.getBusiness_type(),dohalfData.getFlowNo());
        try{
            JSONObject businessInfo=JSONObject.fromObject(data);
            if("0".equals(businessInfo.get("status"))){
                return businessInfo.get("business_data").toString();
            }else{
                throw new Exception(businessInfo.getString("msg"));
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * GCPROJ工程系统获取详情
     * jsonData为传的参数和对应的值
     * webservice接口
     * @return
     */
    public String GCPROJ_BusinessInfo(DohalfData dohalfData) throws Exception{
        //获取url
        String  url=systemUrlService.findBySystemNo("GCPROJ",dohalfData.getBusiness_type()).getString("businessUrl");
        Object businessInfo = null;
        if(dohalfData.getBusiness_type().equals("YJJHSPG") || dohalfData.getBusiness_type().equals("YJFKSPG")){
            businessInfo = WebServiceInterface.getData(url,"getYJBusinessDesc",dohalfData.getFlowNo(),dohalfData.getAppvUsername());
        }else{
            businessInfo= WebServiceInterface.getData(url,"getBusinessDesc",dohalfData.getFlowNo(),dohalfData.getAppvUsername());
        }

        try{

            if(businessInfo != null){
                return businessInfo.toString();
            }else{
                throw new Exception(businessInfo.toString());
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     *任务管理业务接口获取详情接口
     * @return
     */
    public String WBSMGMT_BusinessInfo(DohalfData dohalfData) throws Exception{
        // 将json转为map
        Map<String,Object> mapData= new HashMap<String,Object>();
        mapData.put("instanceId",dohalfData.getFlowNo());
        //设置头部信息
        Map<String,Object> mapHeader=new HashMap<String,Object>();
        //获取url
        String  url=systemUrlService.findBySystemNo("WBSMGMT").getString("businessUrl");
        //获取返回接口详情
        String data= HttpUtils.getData(url,mapData,mapHeader);
        try{
            JSONObject businessInfo=JSONObject.fromObject(data);
            if("0".equals(businessInfo.getString("code"))){
                return businessInfo.get("data").toString();
            }else{
                throw new Exception(businessInfo.getString("msg"));
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     *龙信审计业务接口获取详情接口
     * @return
     */
    public String AUDIT_BusinessInfo(DohalfData dohalfData) throws Exception{
        // 将json转为map
        Map<String,Object> mapData= new HashMap<String,Object>();
        mapData.put("instanceId",dohalfData.getFlowNo());
        //设置头部信息
        Map<String,Object> mapHeader=new HashMap<String,Object>();
        //获取url
        String  url=systemUrlService.findBySystemNo("AUDIT").getString("businessUrl");
        //获取返回接口详情
        String data= HttpUtils.getData(url,mapData,mapHeader);
        try{
            JSONObject businessInfo=JSONObject.fromObject(data);
            if("true".equals(businessInfo.getString("success"))){
                return businessInfo.get("data").toString();
            }else{
                throw new Exception(businessInfo.getString("msg"));
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 探亲机票业务接口获取详情接口
     * http接口
     * @return
     */
    public String TRAVELMGT_BusinessInfo(DohalfData dohalfData) throws Exception{
        // 将json转为map
        Map<String,Object> mapData= new HashMap<String,Object>();
        mapData.put("instanceId",dohalfData.getFlowNo());
        //设置头部信息
        Map<String,Object> mapHeader=new HashMap<String,Object>();
        //获取url
        String url = systemUrlService.findBySystemNo("TRAVELMGT",dohalfData.getBusiness_type()).getString("businessUrl");
        //获取返回接口详情
        String data= HttpUtils.getData(url,mapData,mapHeader);
        try{
            JSONObject businessInfo=JSONObject.fromObject(data);
            if("0".equals(businessInfo.getString("code"))){
                return businessInfo.get("data").toString();
            }else{
                throw new Exception(businessInfo.getString("message"));
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 资金平台系统获取详情
     * jsonData为传的参数和对应的值
     * webservice接口
     * @return
     */
    public String CASHFLOW_BusinessInfo(DohalfData dohalfData) throws Exception{
        //获取url
        String  url=systemUrlService.findBySystemNo("CASHFLOW").getString("businessUrl");
        //获取返回接口详情
        Object businessInfo = WebServiceInterface.getData3(url,"http://mobileGetAccountInfoInterface.isoftstone.com/","mobileGetAccountInfo",dohalfData.getFlowNo(),dohalfData.getAppvUsername());
        try{
            if(businessInfo != null){
                return businessInfo.toString();
            }else{
                throw new Exception(businessInfo.toString());
            }
        }catch (Exception e){
            throw e;
        }
    }
    
    /**
     * 竟优接口统获取详情
     * jsonData为传的参数和对应的值
     * webservice接口
     * @return
     */
    public String NCOST_BusinessInfo(DohalfData dohalfData) throws Exception{
        //获取url
        String  url=systemUrlService.findBySystemNo("NCOST").getString("businessUrl");
        //获取返回接口详情
        Object businessInfo = WebServiceInterface.getData(url,"GetMobileBusinessInfo",dohalfData.getFlowNo(),dohalfData.getRunnote_id(),"amdin");
        try{
            if(businessInfo != null){
                return businessInfo.toString();
            }else{
                throw new Exception("未获取到信息");
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     *LONGCITY龙城业务接口获取详情接口
     * @return
     */
    public String LONGCITY_BusinessInfo(DohalfData dohalfData) throws Exception{
        //获取json数据
        Map<String,Object> mapData= new HashMap<String,Object>();
        mapData.put("instanceId",dohalfData.getFlowNo());
        mapData.put("userCode",dohalfData.getAppvUsername());
        mapData.put("flag","2");
        ObjectMapper jsonMap = new ObjectMapper();
        String jsonStr =jsonMap.writeValueAsString(mapData);
        //设置头部信息
        Map<String,Object> mapHeader=new HashMap<String,Object>();
        mapHeader.put("version","v1");
        mapHeader.put("platform","app");
        mapHeader.put("token","7e2d0ec641474c1985758959825cc1f9de29b2f02be84d90b9a7dc1edf731eba");
        mapHeader.put("Content-Type","application/json");
        //获取url
        String  url=systemUrlService.findBySystemNo("LONGCITY").getString("businessUrl");
        //获取返回接口详情
        Object data =  HttpUtils.getDataByJson(url,jsonStr,mapHeader);
        try{
            JSONObject businessInfo=JSONObject.fromObject(data);
            if("200".equals(businessInfo.getString("code"))){
                return businessInfo.get("result").toString();
            }else{
                throw new Exception(businessInfo.getString("message"));
            }
        }catch (Exception e){
            throw e;
        }
    }



    /**
     * 全景计划_LFPROJ统获取详情
     * jsonData为传的参数和对应的值
     * webservice接口
     * @return
     */
    public String LFPROJ_BusinessInfo(DohalfData dohalfData) throws Exception{
        //获取url
        String  url=systemUrlService.findBySystemNo("LFPROJ").getString("businessUrl");
        //获取返回接口详情
        Object businessInfo = WebServiceInterface.getData3(url,"http://server.webservice.wpeak.com/","getBusinessDesc",dohalfData.getFlowNo(),dohalfData.getAppvUsername());
        try{
            if(businessInfo != null){
                return businessInfo.toString();
            }else{
            	throw new Exception("未获取到信息");
            }
        }catch (Exception e){
            throw e;
        }
    }
    
    /**
     * 全景计划_PROJPLAN统获取详情
     * jsonData为传的参数和对应的值
     * webservice接口
     * @return
     */
    public String PROJPLAN_BusinessInfo(DohalfData dohalfData) throws Exception{
        //获取url
        String  url=systemUrlService.findBySystemNo("PROJPLAN").getString("businessUrl");
        //获取返回接口详情
        Object businessInfo = WebServiceInterface.getData3(url,"http://server.webservice.wpeak.com/","getBusinessDesc",dohalfData.getFlowNo(),dohalfData.getAppvUsername());
        try{
            if(businessInfo != null){
                return businessInfo.toString();
            }else{
            	throw new Exception("未获取到信息");
            }
        }catch (Exception e){
            throw e;
        }
    }
    
    /**
     * GUC统获取详情
     * jsonData为传的参数和对应的值
     * webservice接口
     * @return
     */
    public String GUC_BusinessInfo(DohalfData dohalfData) throws Exception{
        //获取url
        String  url=systemUrlService.findBySystemNo("GUC").getString("businessUrl");
        //获取返回接口详情
        Object businessInfo = WebServiceInterface.getData(url,"GetForm",dohalfData.getPubUsername(),dohalfData.getFlowNo());
        try{
            if(businessInfo != null){
                return businessInfo.toString();
            }else{
            	throw new Exception("未获取到信息");
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 产品平台接口统获取详情
     * jsonData为传的参数和对应的值
     * webservice接口
     * @return
     */
    public String PRODUCT_BusinessInfo(DohalfData dohalfData) throws Exception{
        //获取url
        String  url=systemUrlService.findBySystemNo("PRODUCT").getString("businessUrl");
        //获取返回接口详情
        Object businessInfo = WebServiceInterface.getData(url,"GetBusinessInfo",dohalfData.getFlowNo(),dohalfData.getRunnote_id(),dohalfData.getAppvUsername());
        try{
            if(businessInfo != null){
                return businessInfo.toString();
            }else{
            	throw new Exception("未获取到信息");
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 商业成本接口统获取详情
     * jsonData为传的参数和对应的值
     * webservice接口
     * @return
     */
    public String SYCOST_BusinessInfo(DohalfData dohalfData) throws Exception{
        //获取url
        String  url=systemUrlService.findBySystemNo("SYCOST").getString("businessUrl");
        //获取返回接口详情
        Object businessInfo = WebServiceInterface.getData(url,"GetBusinessInfo",dohalfData.getFlowNo(),dohalfData.getRunnote_id(),dohalfData.getAppvUsername());
        try{
            if(businessInfo != null){
                return businessInfo.toString();
            }else{
            	throw new Exception("未获取到信息");
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 商业租赁统获取详情
     * jsonData为传的参数和对应的值
     * webservice接口
     * @return
     */
    public String SyRent_BusinessInfo(DohalfData dohalfData) throws Exception{
        //获取url
        String  url=systemUrlService.findBySystemNo("SyRent").getString("businessUrl");
        //获取返回接口详情
        Object businessInfo = WebServiceInterface.getData(url,"GetMobileBusinessInfo",dohalfData.getFlowNo(),dohalfData.getRunnote_id(),"admin");
        try{
            if(businessInfo != null){
                return businessInfo.toString();
            }else{
            	throw new Exception("未获取到信息");
            }
        }catch (Exception e){
            throw e;
        }
    }
    
    /**
     * 物业成本统获取详情
     * jsonData为传的参数和对应的值
     * webservice接口
     * @return
     */
    public String WyCost_BusinessInfo(DohalfData dohalfData) throws Exception{
        //获取url
        String  url=systemUrlService.findBySystemNo("WyCost").getString("businessUrl");
        //获取返回接口详情
        Object businessInfo = WebServiceInterface.getData(url,"GetMobileBusinessInfo",dohalfData.getFlowNo(),dohalfData.getRunnote_id(),"admin");
        try{
            if(businessInfo != null){
                return businessInfo.toString();
            }else{
            	throw new Exception("未获取到信息");
            }
        }catch (Exception e){
            throw e;
        }
    }

}
