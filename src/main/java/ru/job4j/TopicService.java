package ru.job4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topic =
            new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String name = req.getSourceName();
        Resp rsl = new Resp("", "204");
        if ("POST".equals(req.httpRequestType())) {
            for (ConcurrentLinkedQueue<String> queue : topic.get(name).values()) {
                queue.add(req.getParam());
            }
        } else if ("GET".equals(req.httpRequestType())) {
            topic.putIfAbsent(name, new ConcurrentHashMap<>());
            topic.get(name).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            String text = topic.get(name).get(req.getParam()).poll();
            if (text == null) {
                text = "";
            }
            rsl = new Resp(text, "200");
        }
        return rsl;
    }
}
