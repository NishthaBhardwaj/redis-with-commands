package com.basket.redis;

import java.util.List;

import org.redisson.api.GeoUnit;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RGeo;
import org.redisson.api.RedissonClient;
import org.redisson.api.geo.GeoSearchArgs;
import org.redisson.api.geo.OptionalGeoSearch;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.basket.redis.dto.Restaurent;
import com.basket.redis.dto.Student;
import com.basket.redis.examples.KeyValueBucketObject;
import com.basket.redis.examples.KeyValueTest;
import com.basket.redis.examples.PubSub;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class BasketRedisApplication implements CommandLineRunner
{

   @Autowired
   KeyValueTest keyValueTest;
   @Autowired
   KeyValueBucketObject keyValueBucketObject;

   @Autowired
   PubSub pubSub;

   @Autowired
   RedissonClient redissonClient;

   public static void main(String[] args)
   {
      SpringApplication.run(BasketRedisApplication.class, args);
   }

   @Override
   public void run(final String... args) throws Exception
   {
      //keyValueTest.keyValueTest();
      keyValueBucketObject.hyperLogLog();
      pubSub.subscriber();
      final RAtomicLong atomicLong = redissonClient.getAtomicLong("user-long");
      atomicLong.set(10000);
      atomicLong.set(20000);
      final long l = atomicLong.get();
      System.out.println(l);

      final RGeo<Restaurent> geo = redissonClient.getGeo("restaurants",
            new TypedJsonJacksonCodec(Restaurent.class));

      Restaurent restaurent = new Restaurent("1","Meerut",32.78136,-96.80539,"Toda1","12340");
      Restaurent restaurent1 = new Restaurent("2","Meerut1",32.88136,-46.80539,"Toda2","12341");
      Restaurent restaurent2 = new Restaurent("3","Meerut2",52.88136,48.80539,"Toda3","12342");
      geo.add(restaurent.getLongitude(),restaurent.getLatitude(),restaurent);
      geo.add(restaurent1.getLongitude(),restaurent1.getLatitude(),restaurent1);
      geo.add(restaurent2.getLongitude(),restaurent2.getLatitude(),restaurent2);

      final OptionalGeoSearch radius = GeoSearchArgs.from(32.78136, 48.80539).radius(3, GeoUnit.FEET);
      final List<Restaurent> search = geo.search(radius);
      System.out.println(search);

   }
}
