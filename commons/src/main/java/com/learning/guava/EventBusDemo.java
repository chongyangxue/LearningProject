package com.learning.guava;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.Data;

/**
 * @author xuechongyang
 */
public class EventBusDemo {

    @Data
    public static class BaseEvent {
        protected String event;

        public BaseEvent(){}

        public BaseEvent(String event) {
            this.event = event;
        }
    }

    @Data
    public static class ChangeEvent extends BaseEvent {
        private String type;

        public ChangeEvent(String event, String type) {
            this.type = type;
            this.event = event;
        }
    }

    @Data
    public static class DeleteEvent extends BaseEvent {
        private String type;

        public DeleteEvent(String event, String type) {
            this.type = type;
            this.event = event;
        }
    }

    public static class EventBusChangeRecorder {

        @Subscribe
        public void eventListener(BaseEvent e) {
            System.out.println("收到事件总线BaseEvent：" + e.getEvent());
        }

        @Subscribe
        public void eventListener(ChangeEvent e) {
            System.out.println("收到事件总线ChangeEvent：" + e.getEvent());
        }

        @Subscribe
        public void eventListener(DeleteEvent e) {
            System.out.println("收到事件总线DeleteEvent：" + e.getEvent());
        }
    }

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new EventBusChangeRecorder());
        ChangeEvent changeEvent = new ChangeEvent("event", "change");
        DeleteEvent deleteEvent = new DeleteEvent("event", "delete");
        eventBus.post(changeEvent);
        eventBus.post(deleteEvent);

        BaseEvent baseEvent = new BaseEvent("event");
        eventBus.post(baseEvent);
    }




}
