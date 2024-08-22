package com.basket.redis.examples;

import java.util.List;

import org.redisson.api.RBucket;
import org.redisson.api.RTopic;
import org.redisson.api.listener.MessageListener;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basket.redis.RedissonConfig;
import com.basket.redis.dto.Student;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PubSub
{
   @Autowired
   RedissonConfig redissonConfig;

   public void subscriber(){

      final RTopic topic = redissonConfig.redissonClient().getTopic("student:1", StringCodec.INSTANCE);
      topic.addListener(String.class, new MessageListener<String>()
      {
         @Override
         public void onMessage(final CharSequence charSequence, final String s)
         {
            log.info("Message recieve charSequence: {} s: {} ", charSequence,s);

         }
      });

   }
}
