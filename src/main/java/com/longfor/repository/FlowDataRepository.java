package com.longfor.repository;

import com.longfor.model.FlowData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by issuser on 2017/9/28.
 */
public interface FlowDataRepository extends JpaRepository<FlowData,Integer>,JpaSpecificationExecutor<FlowData> {

    List<FlowData> findByFlowNoAndSystemNo(String flowNo,String systemNo);

}

