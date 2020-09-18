package com.yestae.common.utils;

import com.google.gson.Gson;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ValueOperations<String, String> valueOperations;
    @Autowired
    private HashOperations<String, String, Object> hashOperations;
    @Autowired
    private ListOperations<String, Object> listOperations;
    @Autowired
    private SetOperations<String, Object> setOperations;
    @Autowired
    private ZSetOperations<String, Object> zSetOperations;
    public static final long DEFAULT_EXPIRE = 86400L;
    public static final long NOT_EXPIRE = -1L;
    private static final Gson gson = new Gson();

    public RedisUtils() {
    }

    public void set(String key, Object value, long expire) {
        this.valueOperations.set(key, toJson(value));
        if (expire != -1L) {
            this.redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }

    }

    public void set(String key, Object value) {
        this.set(key, value, 86400L);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = (String)this.valueOperations.get(key);
        if (expire != -1L) {
            this.redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }

        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return this.get(key, clazz, -1L);
    }

    public String get(String key, long expire) {
        String value = (String)this.valueOperations.get(key);
        if (expire != -1L) {
            this.redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }

        return value;
    }

    public String get(String key) {
        return this.get(key, -1L);
    }

    public void delete(String key) {
        this.redisTemplate.delete(key);
    }

    public void hset(final String key, final String field, final String value) {
        this.hashOperations.put(key, field, value);
    }

    public void hmset(final String key, final Map<String, String> value) {
        this.hashOperations.putAll(key, value);
    }

    public void hdel(final String key, final Object field) {
        this.hashOperations.delete(key, new Object[]{field});
    }

    public void hbatchdel(final String key, final List<Object> fields) {
        this.hashOperations.delete(key, fields.toArray(new Object[0]));
    }

    public void sbatchrem(String key, List<Object> values) {
        this.setOperations.remove(key, values.toArray(new Object[0]));
    }

    public void srem(String key, Object value) {
        this.setOperations.remove(key, new Object[]{value});
    }

    public void sadd(String key, Object value) {
        this.setOperations.add(key, new Object[]{value});
    }

    public void sbatchAdd(String key, List<Object> values) {
        this.setOperations.add(key, values.toArray(new Object[0]));
    }

    public static String toJson(Object object) {
        return !(object instanceof Integer) && !(object instanceof Long) && !(object instanceof Float) && !(object instanceof Double) && !(object instanceof Boolean) && !(object instanceof String) ? gson.toJson(object) : String.valueOf(object);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public Set<String> keys(String pattern) {
        Set<String> keys = this.hashOperations.keys(pattern);
        return keys;
    }

    public void delete(Set keys) {
        this.redisTemplate.delete(keys);
    }

    public void setex(String key, String value, int expire) {
        if ((long)expire != -1L) {
            this.redisTemplate.expire(key, (long)expire, TimeUnit.SECONDS);
        }

    }
}

