package com.longfor.repository;

import com.longfor.model.SystemUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by wangfajin on 17/4/25.
 */
public interface SystemUrlRepository extends JpaRepository<SystemUrl,Integer> {

    List<SystemUrl> findBySystemNo(String systemNO);

    List<SystemUrl> findBySystemNoAndBusinessType(String systemNO, String businessType);
}
