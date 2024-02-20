package com.kh.demo.domain.entity;
// wrapper class
// byte->Byte, short->Short, char->Character, int->Integer, long->Long
// boolean->Boolean, double->Double, float->Float

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Product {
  private Long productId;
  private String title;
  private String textLog;
  private String userName;
  private LocalDateTime cdate;
  private LocalDateTime udate;
}
