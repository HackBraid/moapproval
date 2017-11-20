package com.longfor.repository;

import com.longfor.model.DohalfData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Created by issuser on 2017/9/28.
 */
public interface DohalfDataRepository extends JpaRepository<DohalfData,Integer>,JpaSpecificationExecutor<DohalfData> {

    @Query("update DohalfData d set todoStatus=4  where  d.todoId = :todoId ")
    @Modifying
    Integer updateTodoId(@Param("todoId") String todoId);

}
