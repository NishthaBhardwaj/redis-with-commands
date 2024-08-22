package com.basket.redis.examples;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basket.redis.RedissonConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KeyValueTest
{

   @Autowired
   RedissonConfig redissonConfig;

   public void keyValueTest() throws InterruptedException
   {
      final RedissonClient redissonClient = redissonConfig.redissonClient();
      final RBucket<String> bucket = redissonClient.getBucket("user:1:name", StringCodec.INSTANCE);
      bucket.set("same",10, TimeUnit.SECONDS);
      final String name = bucket.get();
      log.info("Bucket value : {}", name);

      //extend expiry

      Thread.sleep(5000);
      bucket.expire(60, TimeUnit.SECONDS);
      final long ttl = bucket.remainTimeToLive();
      log.info("TTL : {}", ttl);

   }


}
