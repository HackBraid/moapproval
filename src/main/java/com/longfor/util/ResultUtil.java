package com.longfor.util;


import com.longfor.bean.Result;

/**
 * Created by mac on 17/4/20.
 */
public class ResultUtil {

    public static Result success(Object object){
        Result result =new Result();
        result.setCode(0);
        result.setMsg("success");
        result.setData(object);
        return result;
    }

    public static Result error(Integer code,String msg){
        Result result =new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
}
