package com.mxw.member.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.common.enumCode.CommonErrorCode;
import com.mxw.common.exception.MyException;
import com.mxw.common.model.dto.BuyerLabelDTO;
import com.mxw.common.model.entity.BuyerLabelDO;
import com.mxw.common.model.entity.LabelDO;
import com.mxw.common.model.entity.ShopGroupDO;
import com.mxw.common.model.vo.GroupVO;
import com.mxw.member.api.GroupService;
import com.mxw.member.mapper.BuyerLabelLinkMapper;
import com.mxw.member.mapper.GroupMapper;
import io.swagger.models.auth.In;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupMapper groupMapper;


    @Override
    public List<GroupVO> getGroupTree(String sellerId) {
        //最终的树形结构
        List<GroupVO> data = new ArrayList<>();
        try {
            //查询所有分组
            QueryWrapper<ShopGroupDO> wrapper = new QueryWrapper<>();
            wrapper.eq("seller_id", sellerId);
            List<ShopGroupDO> list = groupMapper.selectList(wrapper);
            //将实体类转为树形结构实体类
            List<GroupVO> collect = list.stream().map(item -> {
                return new GroupVO(item.getGroupId(), item.getPid(), item.getGroupName());
            }).collect(Collectors.toList());
            //根节点
            List<GroupVO> rootList = new ArrayList<>();
            for (GroupVO entity : collect) {
                if (0==entity.getParentId()) {
                    //父节点是0的，为根节点
                    rootList.add(entity);
                }
            }

            //为根菜单设置子菜单，getchild是递归调用的
            for (GroupVO entity : rootList) {
                //获取根节点下的所有子节点
                List<GroupVO> childList = getChild(entity.getId(), collect);
                //给根节点设置子节点
                entity.setChildren(childList);
            }
            //封装数据
            data.addAll(rootList);
            return data;
        } catch (MyException e) {
            throw new MyException(CommonErrorCode.ERROR_CODE_10002);
        }
    }

    private List<GroupVO> getChild(Integer labelId, List<GroupVO> list) {
        List<GroupVO> childList = new ArrayList<>();
        for (GroupVO entity : list) {
            if (entity.getParentId().equals(labelId)) {
                childList.add(entity);
            }
        }
        for (GroupVO entity : childList) {
            entity.setChildren(getChild(entity.getId(), list));
        }
        if (childList.size() == 0) {
            return new ArrayList<GroupVO>();
        }
        return childList;
    }

    @Override
    public void addGroup(String sellerId, String content, String pid) {
        //首先判断该用户下是否存在该分组
        QueryWrapper<ShopGroupDO> wrapper = new QueryWrapper<>();
        wrapper.eq("group_name", content).eq("seller_id", sellerId);
        ShopGroupDO groupDO = groupMapper.selectOne(wrapper);
        if (groupDO != null) {
            throw new MyException(CommonErrorCode.ERROR_CODE_10007);
        }
        //如果是父标签的话要增加一个默认子标签
        if ("0".equals(pid)) {
            insertshopGroupDO(sellerId, content, 0);
            //查询刚刚插入的父标签获取其id值
            ShopGroupDO fatherGroupDO = groupMapper.selectOne(wrapper);
            insertshopGroupDO(sellerId, "默认分组", fatherGroupDO.getGroupId());
        } else {
            //不是父标签默认直接添加
            insertshopGroupDO(sellerId, content, Integer.parseInt(pid));
        }
    }

    @Override
    public void editGroup(String sellerId, String content, String id) {
        QueryWrapper<ShopGroupDO> wrapper = new QueryWrapper<>();
        wrapper.eq("group_id", id).eq("seller_id", sellerId);
        ShopGroupDO shopGroupDO = new ShopGroupDO();
        shopGroupDO.setGroupName(content);
        groupMapper.update(shopGroupDO,wrapper);
    }

    private void insertshopGroupDO(String sellerId, String content, Integer pid) {
        ShopGroupDO shopGroupDO = new ShopGroupDO();
        shopGroupDO.setSellerId(Integer.parseInt(sellerId));
        shopGroupDO.setPid(pid);
        shopGroupDO.setGroupName(content);
        shopGroupDO.setCreateTime(new Date());
        groupMapper.insert(shopGroupDO);
    }
}

