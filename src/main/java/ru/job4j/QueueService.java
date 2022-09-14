package ru.job4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
    @Override
    public Resp process(Req req) {
        String name = req.getSourceName();
        Resp rsl = null;
        if ("POST".equals(req.httpRequestType())) {
            queue.putIfAbsent(name, new ConcurrentLinkedQueue<>());
            queue.get(name).add(req.getParam());
        } else if ("GET".equals(req.httpRequestType())) {
            rsl = new Resp(queue.get(name).poll(), "200");
        }
        return rsl;
    }
}
