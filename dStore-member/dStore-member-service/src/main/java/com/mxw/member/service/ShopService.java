package com.mxw.member.service;

import com.mxw.common.model.entity.ShopBuyer;
import com.mxw.common.model.param.MemberParam;
import com.mxw.common.model.vo.MemberVO;
import com.mxw.common.model.vo.PageVO;

import java.util.List;


/**
 * 卖家会员表
 *
 * @author miaoXiaoWen
 * @email miaoXiaoWen@gmail.com
 * @date 2020-12-10 15:00:54
 */
public interface ShopService {

    PageVO<MemberVO> queryPage(MemberParam memberParam);

    List<ShopBuyer> queryAll();
}

