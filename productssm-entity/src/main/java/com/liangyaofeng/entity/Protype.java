package com.liangyaofeng.entity;


import java.io.Serializable;

public class Protype implements Serializable {

  private long pid;
  private String tname;


  public long getPid() {
    return pid;
  }

  public void setPid(long pid) {
    this.pid = pid;
  }

  public String getTname() {
    return tname;
  }

  public void setTname(String tname) {
    this.tname = tname;
  }

  @Override
  public String toString() {
    return "Protype{" +
            "pid=" + pid +
            ", tname='" + tname + '\'' +
            '}';
  }
}
