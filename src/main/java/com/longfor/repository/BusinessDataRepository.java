package com.longfor.repository;

import com.longfor.model.BusinessData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by issuser on 2017/9/28.
 */
public interface BusinessDataRepository extends JpaRepository<BusinessData,Integer>,JpaSpecificationExecutor<BusinessData> {

    List<BusinessData> findBySystemNoAndFlowNoAndAppUsername(String systemNo,String flowNo,String username);
}
