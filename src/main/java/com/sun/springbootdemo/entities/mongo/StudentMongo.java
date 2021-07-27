package com.sun.springbootdemo.entities.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * <p> mongo student实体 </p>
 *
 * @author Sundz
 * @date 2021/7/24 12:34
 */
@Data
@Document(collection = "b_student")
public class StudentMongo implements Serializable {

    private static final long serialVersionUID = -705651020432284944L;

    @Id
    private String id;

    @Field(name = "name")
    @Indexed
    private String name;

    @Field(name = "sex")
    private Integer sex;

    @Field(name = "age")
    private Integer age;

    @Field(name = "class_id")
    @Indexed
    private Long classId;

    @Field(name = "remark")
    private String remark;

}
