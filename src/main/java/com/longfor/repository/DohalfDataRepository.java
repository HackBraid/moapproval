package com.longfor.repository;

import com.longfor.model.DohalfData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by issuser on 2017/9/28.
 */
public interface DohalfDataRepository extends JpaRepository<DohalfData,Integer>,JpaSpecificationExecutor<DohalfData> {

    @Query("update DohalfData d set todoStatus=:todoStatus  where  d.todoId = :todoId ")
    @Modifying
    Integer updateTodoId(@Param("todoId") String todoId,@Param("todoStatus") Integer todoStatus);


    List<DohalfData> findByTodoId(String todoId);

    List<DohalfData> findByFlowNoAndSystemNo(String flowNo,String systemNo);

}
