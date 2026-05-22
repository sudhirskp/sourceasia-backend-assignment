package com.skpGroup.SA.rateLimiter.Service;

import com.skpGroup.SA.rateLimiter.Model.UserStats;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    private final Map<String, UserStats> storage =
            new ConcurrentHashMap<>();

    private static final int LIMIT = 5;

    public boolean accept(String userId) {

        UserStats stats =
                storage.computeIfAbsent(
                        userId,
                        x -> new UserStats()
                );

        synchronized (stats) {

            Instant now = Instant.now();

            while (!stats.getRequests().isEmpty()) {

                Instant t =
                        stats.getRequests().peek();

                if (t.isBefore(now.minusSeconds(60))) {
                    stats.getRequests().poll();
                } else {
                    break;
                }
            }

            if (stats.getRequests().size() >= LIMIT) {

                stats.getRejected()
                        .incrementAndGet();

                return false;
            }

            stats.getRequests().add(now);

            return true;
        }
    }

    public Map<String,Object> stats() {

        Map<String,Object> res =
                new ConcurrentHashMap<>();

        storage.forEach((u,s)->{

            res.put(
                    u,
                    Map.of(
                            "accepted",
                            s.getRequests().size(),
                            "rejected",
                            s.getRejected().get()
                    )
            );

        });

        return res;
    }

}
