package com.mxw.job.job;


import com.mxw.job.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SellerEverydayJob {

    @Autowired
    private BuyerService buyerService;


    //每日晚上12点进行昨日用户数据统计
//    @Scheduled(cron = "0/10 * * * * ?")
    @Scheduled(cron = "0 0 0/1 * * ?")
    private void SellerEverydayJob() {
        buyerService.userSellerStatistics();
    }
}
