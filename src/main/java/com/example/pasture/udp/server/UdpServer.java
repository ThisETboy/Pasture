package com.example.pasture.udp.server;

import com.example.pasture.mapper.ILiveStockMapper;
import com.example.pasture.model.Livestock;
import com.example.pasture.udp.util.HexConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.dsl.Udp;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.net.DatagramPacket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
public class UdpServer {
    @Value("${udp.port}")
    private Integer udpPort;

    @Autowired
    private ILiveStockMapper iLiveStockMapper;
    //    @Autowired
    //    private NewLocatorService newLocatorService;

    @Bean
    public IntegrationFlow integrationFlow() {
        log.info("UDP服务启动成功，端口号为: {}", udpPort);
        return IntegrationFlows.from(Udp.inboundAdapter(udpPort)).channel("udpChannel").get();
    }

    /**
     * 转换器
     */
    @Transformer(inputChannel = "udpChannel", outputChannel = "udpFilter")
    public List<String> transformer(@Payload byte[] payload, @Headers Map<String, Object> headers) {
        List<String> udpList = HexConvert.BinaryToHexStrings(payload);

        return udpList;
    }

    /**
     * 过滤器
     */
    @Filter(inputChannel = "udpFilter", outputChannel = "udpRouter")
    public boolean filter(String message, @Headers Map<String, Object> headers) {

        return true;
    }

    /**
     * 路由分发处理器
     */
    @Router(inputChannel = "udpRouter")
    public String router(String message, @Headers Map<String, Object> headers) {

        return "udpHandle1";
    }

    /**
     * 最终处理器1
     */
    @ServiceActivator(inputChannel = "udpHandle1")
    public void udpMessageHandle(List<String> effectiveList) throws Exception {
        // 可以进行异步处理
        /*ObjectMapper objectMapper = new ObjectMapper();
        CameraReq req = objectMapper.readValue(message, CameraReq.class);
        newLocatorService.storeCameraInfo(req);*/
        log.info("message:" + effectiveList);
        //        List<String> udpList = HexConvert.BinaryToHexStrings(message.getBytes());
        System.out.println(effectiveList.size());
        //发送数据给UDP服务器端
        //                UdpClient.send("47.108.177.197", 8888, buffer);
        if (effectiveList.size() <= 128) {
            //55AA为校验头
            if ("55".equals(effectiveList.get(0)) && "AA".equals(effectiveList.get(1))) {
                //有效数组
                //                List<String> effectiveList = new ArrayList<>();
                int number = Integer.parseInt(effectiveList.get(2));
                //获取有效数据
                //                for (int i = 3; i < udpList.size(); i++) {
                //                    if (effectiveList.size() == number) {
                //                        break;
                //                    }
                //                    effectiveList.add(udpList.get(i));

                effectiveList.subList(0, 3).clear();
                if (number != effectiveList.size()) {
                    for (int i = 0; i <= number - effectiveList.size(); i++) {
                        effectiveList.add("00");
                    }
                }
                //牲畜类型
                int type = Integer.parseInt(effectiveList.get(0));
                //单位代字
                int unitpronoun = Integer.parseInt(effectiveList.get(2) + effectiveList.get(3), 16);
                //设备ID
                long deviceId = Long.parseLong(effectiveList.get(4) + effectiveList.get(5)
                        + effectiveList.get(6) + effectiveList.get(7), 16);
                //经纬度
                double longitude = Double.parseDouble(Integer.parseInt(effectiveList.get(9) + effectiveList.get(10)) +
                        "." +
                        Integer.parseInt(effectiveList.get(11)));

                double latitude = Double.parseDouble(Integer.parseInt(effectiveList.get(14)) +
                        "." +
                        Integer.parseInt(effectiveList.get(15)));

                //时间 yyyy-MM-dd HH:mm:ss
                String time =
                        "20" + Integer.parseInt(effectiveList.get(21)) + "-" +
                                Integer.parseInt(effectiveList.get(22)) + "-" +
                                Integer.parseInt(effectiveList.get(23)) + " " +
                                Integer.parseInt(effectiveList.get(24)) + ":" +
                                Integer.parseInt(effectiveList.get(25)) + ":" +
                                Integer.parseInt(effectiveList.get(26));

                SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateDate = sdfTime.parse(time);//String->Date->Long

                //体温
                double temperature = Integer.parseInt(effectiveList.get(27));
                //步数
                long step = Integer.parseInt(effectiveList.get(28) + effectiveList.get(29), 16);

                //                long step = Integer.parseInt(effectiveList.get(28) + effectiveList.get(29));


                Livestock livestock = new Livestock();
                livestock.setType(type);
                livestock.setUnitPronoun(unitpronoun + "");
                livestock.setDeviceId(deviceId + "");
                livestock.setUpdataTime(dateDate);
                livestock.setLatitude(latitude);
                livestock.setLongitude(longitude);
                livestock.setTemperature(temperature);
                livestock.setTemperature(temperature);
                livestock.setStep(step);
                iLiveStockMapper.insert(livestock);
            }
        }
    }


    /**
     * 最终处理器2
     */
    @ServiceActivator(inputChannel = "udpHandle2")
    public void udpMessageHandle2(String message) throws Exception {
        log.info("UDP2:" + message);
    }
}
