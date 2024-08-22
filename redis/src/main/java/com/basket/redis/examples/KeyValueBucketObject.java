package com.basket.redis.examples;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RBucket;
import org.redisson.api.RDeque;
import org.redisson.api.RHyperLogLog;
import org.redisson.api.RList;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RQueue;
import org.redisson.client.codec.LongCodec;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basket.redis.RedissonConfig;
import com.basket.redis.dto.Student;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KeyValueBucketObject
{
   @Autowired
   RedissonConfig redissonConfig;

   public void keyValueObjectTest(){
      Student student = new Student("Marchal", 10,"atlanta", List.of(40,59,90));
      final RBucket<Student> bucket = redissonConfig.redissonClient().getBucket("student:1", new TypedJsonJacksonCodec(Student.class));
      bucket.set(student);
      log.info("Student object: {}", bucket.get());


   }

   public void numberIncRDecR(){
      final RAtomicLong uVisit = redissonConfig.redissonClient().getAtomicLong("user:1:visit");
      final long l = uVisit.incrementAndGet();
   }

   public void bucketAsMap(){
      // user:1:name
      //user:2:name
      final Map<String, String> stringObjectMap = redissonConfig.redissonClient().getBuckets(StringCodec.INSTANCE)
            .get("user:1:name", "user:2:name", "user3:name");

      final RMap<Object, Object> map = redissonConfig.redissonClient().getMap("user:1:name");




   }

   public void mapTest(){
      final RMap<String, String> map = redissonConfig.redissonClient().getMap("user:1", StringCodec.INSTANCE);
      map.put("1","Same");
      map.put("2","neha");
      map.put("3","Rekhs");
   }

   public void mapTest1(){
      final RMap<String, String> map = redissonConfig.redissonClient().getMap("user:1", StringCodec.INSTANCE);
      final Map<String, String> javaMap = Map.of(
            "name", "jake",
            "age", "20"
      );

      map.putAll(javaMap);
   }


   public void mapTest2(){
      final TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, Student.class);
      final RMap<Integer, Student> map = redissonConfig.redissonClient().getMap("users",codec );
      Student student1 = new Student("Shakti", 40,"Meerut",List.of(23,45,6));
      Student student2 = new Student("Rakesh", 40,"Delhi",List.of(23,45,6));
      Student student3 = new Student("Preeti", 40,"Hyderabad",List.of(23,45,6));
      Student student4 = new Student("Florian", 40,"Mumbai",List.of(23,45,6));
      Student student5 = new Student("Mac", 40,"Berlin",List.of(23,45,6));

      map.put(1,student1);
      map.put(2,student2);
      map.put(3,student3);
      map.put(4,student4);
      map.put(5,student5);


   }

   public void mapCacheTest(){
      //  hgetall users:cache
      final TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, Student.class);
      final RMapCache<Integer, Student> mapCache = this.redissonConfig.redissonClient().getMapCache("users:cache", codec);
      Student student1 = new Student("Shakti", 40,"Meerut",List.of(23,45,6));
      Student student2 = new Student("Rakesh", 40,"Delhi",List.of(23,45,6));
      Student student3 = new Student("Preeti", 40,"Hyderabad",List.of(23,45,6));
      Student student4 = new Student("Florian", 40,"Mumbai",List.of(23,45,6));
      Student student5 = new Student("Mac", 40,"Berlin",List.of(23,45,6));

      mapCache.put(1,student1,20, TimeUnit.SECONDS);
      mapCache.put(2,student2);
      mapCache.put(3,student3);
      mapCache.put(4,student4);
      mapCache.put(5,student5,50, TimeUnit.SECONDS);

   }
   public void mapLocalCacheTest(){
      final TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, Student.class);
      final LocalCachedMapOptions<Integer, Student> mapOption = LocalCachedMapOptions.<Integer, Student>defaults()
            .syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
            .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.NONE);
      final RLocalCachedMap<Integer, Student> localCachedMap = this.redissonConfig.redissonClient().getLocalCachedMap("students", codec, mapOption);

      Student student1 = new Student("Shakti", 40,"Meerut",List.of(23,45,6));
      Student student2 = new Student("Rakesh", 40,"Delhi",List.of(23,45,6));
      Student student3 = new Student("Preeti", 40,"Hyderabad",List.of(23,45,6));
      Student student4 = new Student("Florian", 40,"Mumbai",List.of(23,45,6));
      Student student5 = new Student("Mac", 40,"Berlin",List.of(23,45,6));

      localCachedMap.put(1,student1);
      localCachedMap.put(2,student2);
      localCachedMap.put(3,student3);
      localCachedMap.put(4,student4);
      localCachedMap.put(5,student5);

   }

   public void listTest(){
      // lrange number-input 0 -1
      final RList<Long> list = this.redissonConfig.redissonClient().getList("number-input", LongCodec.INSTANCE);
      list.add(4L);list.add(6L);list.add(4L);list.add(4L);

      final List<Long> range = list.range(list.size());
      range.stream().forEach(s -> System.out.println(s));

   }

   public void queueTest(){
      final RQueue<Long> queue = this.redissonConfig.redissonClient().getQueue("number-input", LongCodec.INSTANCE);
      queue.add(10L);queue.add(20L);

      final Long poll = queue.poll();
      System.out.println(poll);

   }
   public void stackTest(){
      final RDeque<Long> deque = this.redissonConfig.redissonClient().getDeque("number-input", LongCodec.INSTANCE);
      deque.add(30L);deque.add(40L);
      final Long poll = deque.pollLast();

      System.out.println(poll);

   }

   private RBlockingQueue<Long> msgQueue;

   public void messageQueueTest(){
      msgQueue = this.redissonConfig.redissonClient().getBlockingQueue("message-queuet", LongCodec.INSTANCE);


   }

   public void consumer() throws InterruptedException
   {
      final Long take = this.msgQueue.take();
      System.out.println("Consumer1 : " +  take);

   }

   public void producer() throws InterruptedException
   {
      for (Long i = 100L; i < 1L; i++)
      {
         this.msgQueue.add(i.longValue());


      }

   }

   public void hyperLogLog(){
      final RHyperLogLog<Long> hyperLogLog = this.redissonConfig.redissonClient().getHyperLogLog("user:visit", LongCodec.INSTANCE);
      hyperLogLog.add(25L);hyperLogLog.add(26L);hyperLogLog.add(27L);hyperLogLog.add(28L);

      System.out.println(hyperLogLog.count());

   }


}
