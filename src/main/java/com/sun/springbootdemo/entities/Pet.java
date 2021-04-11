package com.sun.springbootdemo.entities;


import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @describtion: TODO
 * @author: Sundz
 * @date: 2020/5/10 21:54
 */
@Data
@Log4j2
@Service
@ApiModel(value = "Pet", description = "人类的宠物")
public class Pet implements Serializable {

    private static final long serialVersionUID = -362402966686497597L;

    @ApiModelProperty(name = "name", value = "鹦鹉的名称", example = "Tom")
    @Length(min = 3, max = 8, message = "鹦鹉的名字只能是3到8个字符!")
    @NotNull
    private String name;

    @ApiModelProperty(name = "age", value = "鹦鹉的年龄", example = "2")
    @Min(value = 0)
    @NotNull
    private int age;

    @ApiModelProperty(name = "sex", value = "鹦鹉的性别", example = "m")
    @Pattern(regexp = "[mf]", flags = {Pattern.Flag.CASE_INSENSITIVE, Pattern.Flag.COMMENTS}, message = "性别只能为m或者f")
    @NotNull
    private String sex;

    public Pet(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Pet() {

    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(Pet.class).add("name", name).add("age", age).toString();
    }

}
