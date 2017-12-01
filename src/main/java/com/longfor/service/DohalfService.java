package com.longfor.service;

import com.longfor.bean.DohalfParamBean;
import com.longfor.model.DohalfData;
import com.longfor.repository.DohalfDataRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by issuser on 2017/9/28.
 */
@Service
public class DohalfService {

    @Autowired
    DohalfDataRepository dohalfDataRepository;

    /**
     * 分页查询已办代办列表
     * @param dohalfParamBean
     * @return
     */
    public Page<DohalfData> findList(DohalfParamBean dohalfParamBean){
        List<Sort.Order> orders=new ArrayList<Sort.Order>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "otherStatus"));
        orders.add(new Sort.Order( Sort.Direction.DESC, "updateDate"));
        Pageable pageable = new PageRequest(dohalfParamBean.getPage(), dohalfParamBean.getPageSize(), new Sort(orders));
        Page<DohalfData> goodsPage=dohalfDataRepository.findAll(new Specification<DohalfData>(){
            public Predicate toPredicate(Root<DohalfData> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                List<Integer> listStatus=new ArrayList<Integer>();
                //0和5表示代办  1和4 表示已办
                if(dohalfParamBean.getStatus()==0){
                    listStatus.add(0);
                    listStatus.add(5);
                }else if(dohalfParamBean.getStatus()==1){
                    listStatus.add(1);
                    listStatus.add(4);
                }
                if(listStatus.size()>0){
                    Expression<Integer> exp = root.get("todoStatus");
                    list.add(exp.in(listStatus));
                }
                list.add(criteriaBuilder.equal(root.get("appvUsername").as(String.class), dohalfParamBean.getUsername()));
                list.add(criteriaBuilder.equal(root.get("todo_type").as(Integer.class), 0));
                list.add(criteriaBuilder.isNull(root.get("slideDel").as(Integer.class)));
                list.add(criteriaBuilder.isNull(root.get("logicDel").as(Integer.class)));
                if(StringUtils.isNotEmpty(dohalfParamBean.getSearchType())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+dohalfParamBean.getSearchType()+"%"));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        return goodsPage;
    }

    /**
     * 更新审批状态
     * @param todoId
     */
    @Transactional
    public void updateTodoId(String todoId,Integer status){
        dohalfDataRepository.updateTodoId(todoId,status);
    }

}
