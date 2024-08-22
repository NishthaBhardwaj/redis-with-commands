package com.basket.redis.pub_stream;

import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.redisson.api.StreamMessageId;
import org.redisson.api.stream.StreamAddArgs;
import org.redisson.api.stream.StreamTrimStrategyArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Publisher
{
   @Autowired
   RedissonClient redissonClient;

   public void publish(){


      final RStream<String, String> stream =redissonClient.getStream("topic1");
      final StreamMessageId add = stream.add(StreamAddArgs.entry("Nishtha", "architect"));
      System.out.println(add.toString());
   }
}
