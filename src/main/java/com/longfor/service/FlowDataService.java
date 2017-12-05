package com.longfor.service;

import com.longfor.bean.DohalfParamBean;
import com.longfor.model.BusinessData;
import com.longfor.model.DohalfData;
import com.longfor.model.FlowData;
import com.longfor.repository.BusinessDataRepository;
import com.longfor.repository.FlowDataRepository;
import com.longfor.util.RedisUtil;
import com.longfor.util.WebServiceInterface;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by issuser on 2017/9/29.
 */
@Service
public class FlowDataService {

    @Autowired
    FlowDataRepository flowDataRepository;

    @Autowired
    BusinessDataRepository businessDataRepository;

    @Value("${BPM_FLOW_ENGINES}")
    private String BPM_FLOW_ENGINES;

    /**
     * 分页查询我的发起
     * @param dohalfParamBean
     * @return
     */
    public Page<FlowData> findFlowList(DohalfParamBean dohalfParamBean){
        //自定义字段排序
        List<Sort.Order> orders=new ArrayList<Sort.Order>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "flowStatus"));
        orders.add(new Sort.Order( Sort.Direction.DESC, "updateTime"));
        Pageable pageable = new PageRequest(dohalfParamBean.getPage(), dohalfParamBean.getPageSize(), new Sort(orders));
        Page<FlowData> goodsPage=flowDataRepository.findAll(new Specification<FlowData>(){
            public Predicate toPredicate(Root<FlowData> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(criteriaBuilder.equal(root.get("pubUsername").as(String.class), dohalfParamBean.getUsername()));
                list.add(criteriaBuilder.isNull(root.get("slideDel").as(Integer.class)));
                if(StringUtils.isNotEmpty(dohalfParamBean.getSearchType())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+dohalfParamBean.getSearchType()+"%"));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        return goodsPage;
    }

    public List<BusinessData> findBusiness(String systemNo,String flowNo,String username){
        return businessDataRepository.findBySystemNoAndFlowNoAndAppUsername(systemNo,flowNo,username);
    }

    public List<FlowData> findFlowData(String flowNo,String systemNo){
        return flowDataRepository.findByFlowNoAndSystemNo(flowNo,systemNo);
    }

    public String getBpmFlowInfo(String flowNo,String status)throws Exception{
        Object data= WebServiceInterface.getData(BPM_FLOW_ENGINES,"getflow_desc",flowNo,status);
        try{
            JSONObject flowInfo=JSONObject.fromObject(data);
            if("0".equals(flowInfo.get("status"))){
                return data.toString();
            }else{
                throw new Exception(flowInfo.getString("msg"));
            }
        }catch (Exception e){
            throw e;
        }
    }

}
