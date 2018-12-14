package com.ycxg.server.redis;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author gaoyou
 */
@Configuration
@EnableCaching
public class RedisCacheConfig {
	
    @Bean
    public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate){
        return new RedisCacheManager(redisTemplate);
    }

    /**
     *   key序列化方式;（不然会出现乱码;）,但是如果方法上有Long等非String类型的话，会报类型转换错误；
     *  所以在没有自己定义key生成策略的时候，以下这个代码建议不要这么写，可以不配置或者自己实现ObjectRedisSerializer
     *  或者JdkSerializationRedisSerializer序列化方式;
     *  Long类型不可以会出现异常信息;
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        return redisTemplate;
    }
    
    @Bean
    public RedisRepository redisRepository(RedisTemplate<String, String> redisTemplate){
    	return new RedisRepository(redisTemplate);
    }
    
}