package top.coderak.core.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import top.coderak.core.utils.DataConverter;

@Component
public class KafkaReceiver {


    /**
     * 监听topic(主题),有消息就读取得到数据
     *
     * @param data 数据包
     */
    @KafkaListener(topics = {"aktest"})
    public void receiveMessage(String data) {

        System.out.println("接收数据");

        // 后续
        System.out.println("接收数据为：" + DataConverter.mapStringToMap(data));
    }
}

