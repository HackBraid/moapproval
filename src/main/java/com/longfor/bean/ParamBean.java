package com.longfor.bean;

/**
 * Created by issuser on 2017/11/2.
 */
public class ParamBean {

    private Integer status=0;

    private Integer page=0;

    private Integer pageSize=10;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
