package com.skpGroup.SA.rateLimiter.Model;

import java.time.Instant;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class UserStats {

    private final ConcurrentLinkedQueue<Instant> requests =
            new ConcurrentLinkedQueue<>();

    private final AtomicInteger rejected =
            new AtomicInteger();

    public ConcurrentLinkedQueue<Instant> getRequests() {
        return requests;
    }

    public AtomicInteger getRejected() {
        return rejected;
    }
}
