package com.yang.springframework.event;

import com.yang.springframework.context.ApplicationListener;

public class ContextCustomEventListener implements ApplicationListener<CustomEvent> {
    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("收到：" + event.getSource() + "，消息：" + event.getMessage() + "，id：" + event.getId());
    }
}
