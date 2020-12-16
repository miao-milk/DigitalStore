package com.mxw.member.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.baomidou.mybatisplus.extension.api.R;
import com.mxw.common.model.entity.LabelEntity;
import com.mxw.common.utils.ResponseUtils;
import com.mxw.member.api.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * 会员标签信息表
 *
 * @author miaoXiaoWen
 * @email miaoXiaoWen@gmail.com
 * @date 2020-12-15 10:09:45
 */
@RestController
@RequestMapping("dStore/label")
public class LabelController {
    @Autowired
    private LabelService labelService;



//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{buyerLabelId}")
//    //@RequiresPermissions("dStore:label:info")
//    public R info(@PathVariable("buyerLabelId") Long buyerLabelId){
//		LabelEntity label = labelService.getById(buyerLabelId);
//
//        return R.ok().put("label", label);
//    }
//
//    /**
//     * 保存
//     */
//    @RequestMapping("/save")
//    //@RequiresPermissions("dStore:label:save")
//    public R save(@RequestBody LabelEntity label){
//		labelService.save(label);
//
//        return R.ok();
//    }
//
//    /**
//     * 修改
//     */
//    @RequestMapping("/update")
//    //@RequiresPermissions("dStore:label:update")
//    public R update(@RequestBody LabelEntity label){
//		labelService.updateById(label);
//
//        return R.ok();
//    }
//
//    /**
//     * 删除
//     */
//    @RequestMapping("/delete")
//    //@RequiresPermissions("dStore:label:delete")
//    public R delete(@RequestBody Long[] buyerLabelIds){
//		labelService.removeByIds(Arrays.asList(buyerLabelIds));
//
//        return R.ok();
//    }

}
