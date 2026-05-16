package top.coderak.core.base.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LocalCache {

    private static final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    static {
        scheduler.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            cache.entrySet().removeIf(entry -> entry.getValue().expireTime < now);
        }, 1000, 1000, TimeUnit.MILLISECONDS);
    }

    public static void set(String key, String value, long timeout) {
        cache.put(key, new CacheEntry(value, System.currentTimeMillis() + timeout));
    }

    public static String get(String key) {
        CacheEntry entry = cache.get(key);
        if (entry == null || entry.expireTime < System.currentTimeMillis()) {
            cache.remove(key);
            return null;
        }
        return entry.value;
    }

    private static class CacheEntry {
        String value;
        long expireTime;

        CacheEntry(String value, long expireTime) {
            this.value = value;
            this.expireTime = expireTime;
        }
    }

    public static void shutdown() {
        scheduler.shutdown();
    }
}