package com.sun.springbootdemo.entities.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * <p> 班级信息 -- >> mongo </p>
 *
 * @author Sundz
 * @date 2021/7/24 21:47
 */
@Document(collection = "b_class")
@Data
public class ClassMongo implements Serializable {

    private static final long serialVersionUID = -8603689722810755295L;
    
    @Field(name = "id")
    private String id;

    @Field(name = "name")
    @Indexed
    private String name;

    @Field(name = "stu_count")
    private Long stuCount;

    @Field(name = "teacher")
    private String teacher;
}
