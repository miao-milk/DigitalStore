package com.mxw.member;


import com.mxw.common.model.param.MemberParam;
import com.mxw.common.model.vo.MemberVO;
import com.mxw.common.model.vo.PageVO;
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
        MemberParam memberParam = new MemberParam();
        memberParam.setPage(1l);
        memberParam.setPageSize(10l);
        PageVO<MemberVO> memberVOS = shopService.queryPage(memberParam);
        List<MemberVO> items = memberVOS.getItems();
        items.stream().peek(e-> System.out.println(e));
    }
}
