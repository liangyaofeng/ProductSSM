package com.liangyaofeng.entity;

import java.io.Serializable;

public class Product implements Serializable {

  private long id;
  private String name;
  private long pid;
  private double price;
  private String picture;

  private Protype protype;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public long getPid() {
    return pid;
  }

  public void setPid(long pid) {
    this.pid = pid;
  }


  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }


  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public Protype getProtype() {
    return protype;
  }

  public void setProtype(Protype protype) {
    this.protype = protype;
  }


  @Override
  public String toString() {
    return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", pid=" + pid +
            ", price=" + price +
            ", picture='" + picture + '\'' +
            ", protype=" + protype +
            '}';
  }
}
