package com.sun.springbootdemo.entities.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * <p> 请求日志 </p>
 *
 * @author Sundz
 * @date 2021/7/25 18:03
 */
@Data
@Document(collection = "b_logs")
public class LogMongo implements Serializable {

    private static final long serialVersionUID = -6793913147251230179L;

    @Id
    private String id;

    @Field(name = "type")
    private Integer type;

    @Field(name = "content")
    private String contentJson;

    @Field(name = "remark")
    private String remark;

}
