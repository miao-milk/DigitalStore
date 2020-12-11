package com.mxw.member;


import com.mxw.common.model.entity.ShopBuyer;
import com.mxw.common.model.param.MemberParam;
import com.mxw.common.model.vo.MemberVO;
import com.mxw.common.model.vo.PageVO;
import com.mxw.member.service.ShopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MemberApplicationTest {

    @Autowired
    ShopService shopService;

    @Test
    public void contextLoads() {
        List<ShopBuyer> ShopBuyer = shopService.queryAll();
        for (ShopBuyer item : ShopBuyer) {
            System.out.println((ShopBuyer)item);
        }
    }
}
