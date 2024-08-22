package com.basket.redis.fib.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class FibService
{


   // have a strategy for cache evict
   @Cacheable( value = "math:fib", key = "#index")
   public int getFib(int index, String name){
      System.out.println("calculateing fib for " + index + ", name: "+ name);
      return this.fib(index);
   }


   // PUT / POST / PATCH / DELETE
   @CacheEvict(value = "math:fib", key = "#index")
   public void clearCache(int index){
      System.out.println("clearing hash key");
   }

   private int fib(int index){
      if(index < 2){
         return index;

      }else {
         return fib(index - 1) + fib(index - 2);
      }
   }
}
