package message;

import com.mxw.common.model.dto.SalesDTO;
import com.mxw.common.model.entity.MessageDO;
import com.mxw.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import message.mapper.SendMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "首页", tags = "首页", description = "首页")
@RestController("/send")
public class TestController {

    @Autowired
    SendMessageMapper sendMessageMapper;

    /**
     * 查询累计销售额
     */
    @GetMapping("/test")
    @ApiOperation("查询累计销售额")
    public Result getTotalSales() {
        List<MessageDO> messageDOS = sendMessageMapper.selectList(null);
        return Result.ok("查询累计销售额成功").put("data", messageDOS);
    }
}
