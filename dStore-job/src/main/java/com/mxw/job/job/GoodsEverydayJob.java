package com.mxw.job.job;


import com.mxw.job.service.GoodsService;
import com.mxw.job.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class GoodsEverydayJob {

    @Autowired
    private OrderService orderService;

    //每日晚上12点进行昨日用户数据统计
    @Scheduled(cron = "0 0 0/1 * * ?")
   // @Scheduled(cron = "0/10 * * * * ?")
    private void GoodsEverydayJob() {
        orderService.userDataStatistics();
    }
}
