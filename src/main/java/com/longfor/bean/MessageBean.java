package com.longfor.bean;

import java.util.List;

/**
 * Created by issuser on 2017/7/25.
 */
public class MessageBean {

    private long total;
    private List<?> list;
    private int page;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
