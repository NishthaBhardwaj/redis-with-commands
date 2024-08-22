package com.basket.redis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Restaurent
{
   private String id;
   private String city;
   private double latitude;
   private double longitude;
   private String name;
   private String zip;
}
