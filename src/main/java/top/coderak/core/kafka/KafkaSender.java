package top.coderak.core.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import top.coderak.core.utils.MD5Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
@EnableScheduling
public class KafkaSender {

    @Autowired
    private KafkaTemplate kafkaTemplate;


    /**
     * 发送数据到Kafka
     *
     * @param channel Topic
     * @param data    数据包
     */
    public void sendChannel(String channel, String data) {

        ListenableFuture future = kafkaTemplate.send(channel, data);

        future.addCallback(new SuccessCallback() {

            @Override
            public void onSuccess(Object o) {

                System.out.println("发送数据成功");
            }
        }, new FailureCallback() {

            @Override
            public void onFailure(Throwable throwable) {

                System.out.println("发送数据异常");
            }
        });
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void task() {

        Map<String, Object> map = new HashMap<>();

        map.put("Topic", "aktest");

        map.put("Data", "测试数据：" + MD5Utils.string2MD5(new Random().toString()) + new Date());

        sendChannel("aktest", map.toString());
    }
}
