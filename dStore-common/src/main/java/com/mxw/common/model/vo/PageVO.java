package com.mxw.common.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 统一返回分页数据规范
 * Iterablea迭代器，用于迭代输出集合
 */
@Data
public class PageVO<T> implements Iterable<T>, Serializable {
    //核心四件套
    private long counts;
    private long page;
    private long pageSize;
    private List<T> items=new ArrayList<T>();

    public PageVO() {
    }

    public PageVO(long counts, long page, long pageSize, List<T> items) {
        this.counts = counts;
        this.page = page;
        this.pageSize = pageSize;
        this.items = items;
    }

    @Override
    public Iterator<T> iterator() {
        return getItems().iterator();
    }
}
