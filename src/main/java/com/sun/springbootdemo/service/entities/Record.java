package com.sun.springbootdemo.service.entities;

import com.google.common.base.MoreObjects;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 * @Description: Record
 * @ClassName: Record
 * @Author: Sundz
 * @Date: 2020/6/17 10:52
 * @Version: V1.0
 **/
@Data
@Log4j2
public class Record implements Serializable {

    private static final long serialVersionUID = 6805445227675049474L;

    private long id;

    private String name;


    public Record() {
        log.info("Record的<无>参构造被调用了!");
    }

    public Record(long id, String name) {
        log.info("Record的<有>参构造被调用了!");
        this.id = id;
        this.name = name;
    }

    @PostConstruct
    public void initial() {
        log.info("初始化方法initial开始调用了!");
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(Record.class).add("id", id).add("name", name).toString();
    }

}
