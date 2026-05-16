package top.coderak.core.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.coderak.core.utils.MD5Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Component
public class KafkaSender {

    /**
     * 发送数据到Kafka
     *
     * @param channel Topic
     * @param data    数据包
     */
    public void sendChannel(String channel, String data) {
        System.out.println("发送数据成功 (Kafka未配置，模拟发送)");
        log.info("发送数据到 channel: " + channel + ", data: " + data);
    }
}