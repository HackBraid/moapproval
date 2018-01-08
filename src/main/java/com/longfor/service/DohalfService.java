package com.longfor.service;

import com.longfor.bean.SysWorkitemcenterBean;
import com.longfor.model.SysWorkitemcenterEntity;
import com.longfor.repository.SysWorkitemcenterDataRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by issuser on 2017/9/28.
 */
@Service
public class DohalfService {

    @Autowired
    SysWorkitemcenterDataRepository sysWorkitemcenterDataRepository;

    public Page<SysWorkitemcenterEntity> findList(SysWorkitemcenterBean sysWorkitemcenterBean){
        List<Sort.Order> orders=new ArrayList<Sort.Order>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "createdtime"));
        Pageable pageable = new PageRequest(sysWorkitemcenterBean.getPage(), sysWorkitemcenterBean.getPageSize(), new Sort(orders));

        Page<SysWorkitemcenterEntity> goodsPage=sysWorkitemcenterDataRepository.findAll(new Specification<SysWorkitemcenterEntity>(){
            public Predicate toPredicate(Root<SysWorkitemcenterEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(criteriaBuilder.like(root.get("participantcode").as(String.class), "%"+sysWorkitemcenterBean.getParticipantcode()+"%"));

                if (StringUtils.isNotEmpty(sysWorkitemcenterBean.getWorkitemname())){
                    list.add(criteriaBuilder.like(root.get("workitemname").as(String.class), "%"+sysWorkitemcenterBean.getWorkitemname()+"%"));
                }

                if (StringUtils.isNotEmpty(sysWorkitemcenterBean.getOrderid())){
                    list.add(criteriaBuilder.like(root.get("orderid").as(String.class), "%"+sysWorkitemcenterBean.getOrderid()+"%"));
                }

                if (StringUtils.isNotEmpty(sysWorkitemcenterBean.getOriginatorname())){
                    list.add(criteriaBuilder.like(root.get("originatorname").as(String.class), "%"+sysWorkitemcenterBean.getOriginatorname()+"%"));
                }

                if (StringUtils.isNotEmpty(sysWorkitemcenterBean.getOriginator())){
                    list.add(criteriaBuilder.like(root.get("originator").as(String.class), "%"+sysWorkitemcenterBean.getOriginator()+"%"));
                }

                if (sysWorkitemcenterBean.getStatus() != null){
                    Expression<Integer> exp = root.get("status");
                    list.add(exp.in(sysWorkitemcenterBean.getStatus()));
                }

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        return goodsPage;
    }
}
