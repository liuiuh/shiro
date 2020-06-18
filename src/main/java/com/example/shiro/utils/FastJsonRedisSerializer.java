package com.example.shiro.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * @Description: redis序列化为JSON
 * @Author:  Liu
 * @Date: 2020/6/11 15:06
 */

public class FastJsonRedisSerializer implements RedisSerializer {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<?> clazz;

    public FastJsonRedisSerializer(Class<?> clazz) {
        super();
        this.clazz = clazz;
    }


    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (o == null) {
            return new byte[0];
        }
        return JSON.toJSONString(o, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);

    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return JSON.parseObject(str, clazz);
    }
}
