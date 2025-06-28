package com.github.colommar.missionsubexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 发布-订阅模式主运行类
 */
@SpringBootApplication
public class PubSubPatternMain {
    
    public static void main(String[] args) {
        // 启动Spring应用
        ConfigurableApplicationContext context = SpringApplication.run(PubSubPatternMain.class, args);
        
        // 获取演示类
        PubSubPatternDemo demo = context.getBean(PubSubPatternDemo.class);
        
        // 执行演示
        demo.demonstratePubSubPattern();
        demo.demonstrateDynamicSubscription();
        demo.demonstrateEventStatistics();
        
        // 关闭应用
        context.close();
    }
} 