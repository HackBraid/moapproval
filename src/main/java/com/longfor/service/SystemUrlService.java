package com.longfor.service;

import com.longfor.model.SystemUrl;
import com.longfor.repository.SystemUrlRepository;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mac on 17/4/26.
 */

@Service
public class SystemUrlService {
    private final static Logger logger= LoggerFactory.getLogger(SystemUrlService.class);

    @Autowired
    private SystemUrlRepository systemUrlRepository;

    public JSONObject findBySystemNo(String sysno)throws Exception{
        List<SystemUrl> list=systemUrlRepository.findBySystemNo(sysno);
        if(list!=null && list.size()>0){
            return JSONObject.fromObject(list.get(0).getUrl());
        }else{
            throw new Exception("url获取失败");
        }
    }

    public JSONObject findBySystemNo(String sysno,String businessType)throws Exception{
        List<SystemUrl> list=systemUrlRepository.findBySystemNoAndBusinessType(sysno,businessType);
        if(list!=null && list.size()>0){
            if(StringUtils.isNotEmpty(businessType)){
                JSONObject json=new JSONObject();
                for(SystemUrl systemUrl:list){
                    if(businessType.equals(systemUrl.getBusinessType())){
                        json=JSONObject.fromObject(list.get(0).getUrl());
                    }
                }
                return json;
            }else{
                return JSONObject.fromObject(list.get(0).getUrl());
            }
        }else{
            throw new Exception("url获取失败");
        }
    }
}
