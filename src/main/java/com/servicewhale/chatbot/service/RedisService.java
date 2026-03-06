package com.servicewhale.chatbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void addKeyValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }
    
    public String listAllKeys() {
    	return redisTemplate.keys("*").toString();
    }
    
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }
}