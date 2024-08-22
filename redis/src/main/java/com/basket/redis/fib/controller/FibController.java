package com.basket.redis.fib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basket.redis.fib.service.FibService;

@RestController
@RequestMapping("fib")
public class FibController
{
   @Autowired
   private FibService fibService;

   @GetMapping("{index}/{name}")
   public Integer getFib(@PathVariable int index, @PathVariable String name){
      return this.fibService.getFib(index, name);
   }
}
