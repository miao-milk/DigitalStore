package com.mxw.common.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class GroupVO implements Serializable {

    private Integer id;

    private Integer parentId;

    private String label;

    private List<GroupVO> children = new ArrayList<>();

    public GroupVO(Integer id, Integer parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.label = name;
    }
}
