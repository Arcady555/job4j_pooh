package ru.job4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topic =
            new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String name = req.getSourceName();
        Resp rsl = new Resp("", "501");
        if ("POST".equals(req.httpRequestType())) {
            for (ConcurrentLinkedQueue<String> queue : topic.getOrDefault(name, new ConcurrentHashMap<>()).values()) {
                queue.add(req.getParam());
                rsl = new Resp(req.getParam(), "201");
            }
        } else if ("GET".equals(req.httpRequestType())) {
            topic.putIfAbsent(name, new ConcurrentHashMap<>());
            topic.get(name).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            String text = topic.get(name).get(req.getParam()).poll();
            rsl = new Resp(text, "201");
            if (text == null) {
                rsl = new Resp("", "204");
            }
        }
        return rsl;
    }
}
