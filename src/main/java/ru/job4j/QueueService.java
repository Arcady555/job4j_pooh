package ru.job4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
    @Override
    public Resp process(Req req) {
        String name = req.getSourceName();
        Resp rsl = new Resp("", "501");
        if ("POST".equals(req.httpRequestType())) {
            queue.putIfAbsent(name, new ConcurrentLinkedQueue<>());
            queue.get(name).add(req.getParam());
            rsl = new Resp(req.getParam(), "201");
        } else if ("GET".equals(req.httpRequestType())) {
            rsl = new Resp(queue.getOrDefault(name, new ConcurrentLinkedQueue<>()).poll(), "201");
            if (rsl.text() == null) {
                rsl = new Resp("", "501");
            }
        }
        return rsl;
    }
}
