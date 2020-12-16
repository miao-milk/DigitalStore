package com.mxw.applicationWeb.controller;

import com.mxw.common.model.entity.LabelEntity;
import com.mxw.common.utils.ResponseUtils;
import com.mxw.member.api.LabelService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class UserLabelController {

    @Reference
    private LabelService labelService;

    /**
     * 列表
     */
    @GetMapping("/allLabel")
    public ResponseUtils list(){
        List<LabelEntity> labelList = labelService.queryallLabel();
        return ResponseUtils.ok().put("data", labelList);
    }

    /**
     * 列表
     */
    @GetMapping("/addLabel")
    public ResponseUtils addLabel(){
        labelService.addLabel();
        return ResponseUtils.ok();
    }

}
