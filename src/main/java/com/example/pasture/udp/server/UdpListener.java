package com.example.pasture.udp.server;


import com.example.pasture.mapper.ILiveStockMapper;
import com.example.pasture.model.Livestock;
import com.example.pasture.udp.config.UdpConfig;
import com.example.pasture.udp.entity.DoorSendMsg;
import com.example.pasture.udp.util.CardUtil;
import com.example.pasture.udp.util.HexConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author aaron
 * @since 2021-01-28
 */

@Slf4j
@WebListener
public class UdpListener implements ServletContextListener {

    private UdpConfig udpConfig;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WebApplicationContext context = WebApplicationContextUtils
                .getWebApplicationContext(sce.getServletContext());
        UdpConfig udpConfig = context
                .getBean(UdpConfig.class);
        this.udpConfig = udpConfig;
        try {
            executeUdpMsg(context);
        } catch (SocketException e) {
            log.error(e.getMessage());
        }
    }

    private void executeUdpMsg(WebApplicationContext context) throws SocketException {
//        log.info("========>监听UDP数据包，监听端口 {} <======== ", udpConfig.getPort());
//        int port = udpConfig.getPort();
//        //创建服务器端DatagramSocket，指定端口
//        DatagramSocket socket = new DatagramSocket(port);
//        log.info("=======创建数据报，用于接收客户端发送的数据======");
//        int a = 0;
//        while (true) {
//            byte[] buffer = new byte[udpConfig.getMaxUdpDataSize()];
//            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
//            try {
//                log.info("=======端口 {} 等待接收消息 ======", udpConfig.getPort());
//                socket.receive(packet);
//                // 接收到的UDP信息，然后解码
//                buffer = packet.getData();
//                InetAddress address = packet.getAddress();
//                String targetIp = address.getHostAddress();
//                int targetPort = packet.getPort();
//
//                List<String> udpList = HexConvert.BinaryToHexStrings(buffer);
//                log.info("=========接收到来自" + targetIp + ":" + targetPort + "的消息:" + udpList.toString());
//
//                //发送数据给UDP服务器端
//                //                UdpClient.send("47.108.177.197", 8888, buffer);
//                if (udpList.size() == 128) {
//                    //55AA为校验头
//                    if ("55".equals(udpList.get(0)) && "AA".equals(udpList.get(1))) {
//                        //有效数组
//                        List<String> effectiveList = new ArrayList<>();
//                        int number = Integer.parseInt(udpList.get(2));
//                        //获取有效数据
//                        for (int i = 3; i < udpList.size(); i++) {
//                            if (effectiveList.size() == number) {
//                                break;
//                            }
//                            effectiveList.add(udpList.get(i));
//                        }
//                        //牲畜类型
//                        int type = Integer.parseInt(effectiveList.get(0));
//                        //单位代字
//                        int unitpronoun = Integer.parseInt(effectiveList.get(2) + effectiveList.get(3), 16);
//                        //设备ID
//                        long deviceId = Long.parseLong(effectiveList.get(4) + effectiveList.get(5)
//                                + effectiveList.get(6) + effectiveList.get(7), 16);
//                        //经纬度
//                        double longitude = Double.parseDouble(Integer.parseInt(effectiveList.get(9) + effectiveList.get(10)) +
//                                "." +
//                                Integer.parseInt(effectiveList.get(11)));
//
//                        double latitude = Double.parseDouble(Integer.parseInt(effectiveList.get(14)) +
//                                "." +
//                                Integer.parseInt(effectiveList.get(15)));
//
//                        //时间 yyyy-MM-dd HH:mm:ss
//                        String time =
//                                "20" + Integer.parseInt(effectiveList.get(21)) + "-" +
//                                        Integer.parseInt(effectiveList.get(22)) + "-" +
//                                        Integer.parseInt(effectiveList.get(23)) + " " +
//                                        Integer.parseInt(effectiveList.get(24)) + ":" +
//                                        Integer.parseInt(effectiveList.get(25)) + ":" +
//                                        Integer.parseInt(effectiveList.get(26));
//
//                        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        Date dateDate = sdfTime.parse(time);//String->Date->Long
//
//                        //体温
//                        double temperature = Integer.parseInt(effectiveList.get(27));
//                        //步数
//                        long step = Integer.parseInt(effectiveList.get(28) + effectiveList.get(29), 16);
//
//
//                        ILiveStockMapper iLiveStockMapper = context.getBean(ILiveStockMapper.class);
//                        Livestock livestock = new Livestock();
//                        livestock.setType(type);
//                        livestock.setUnitPronoun(unitpronoun + "");
//                        livestock.setDeviceId(deviceId + "");
//                        livestock.setUpdataTime(dateDate);
//                        livestock.setLatitude(latitude);
//                        livestock.setLongitude(longitude);
//                        livestock.setTemperature(temperature);
//                        livestock.setTemperature(temperature);
//                        livestock.setStep(step);
//                        iLiveStockMapper.insert(livestock);
//                    }
//                }
//            } catch (IOException e) {
//                log.error(e.getMessage());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("========> 关闭监听UDP数据包，监听端口 {} <======== ", udpConfig.getPort());
    }
}
