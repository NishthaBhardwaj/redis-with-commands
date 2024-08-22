package com.basket.redis.pub_stream;

import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer
{

   @Autowired
   RedissonClient redissonClient;


   public void consumer(){
      RStream<String, String> stream = redissonClient.getStream("topic1");

   }
}
