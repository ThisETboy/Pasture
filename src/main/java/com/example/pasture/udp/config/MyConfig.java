package com.example.pasture.udp.config;

import com.example.pasture.udp.server.UdpListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyConfig extends WebMvcConfigurerAdapter {

    @Bean
    public UdpListener udpListener() {
        return new UdpListener();
    }

}
