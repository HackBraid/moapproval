package com.longfor.repository;

import com.longfor.model.SysWorkitemcenterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SysWorkitemcenterDataRepository extends JpaRepository<SysWorkitemcenterEntity,Integer>,JpaSpecificationExecutor<SysWorkitemcenterEntity> {

    //带条件的分页查询
    public Page<SysWorkitemcenterEntity> findByParticipantcode(String participantcode, Pageable pageable);
}
