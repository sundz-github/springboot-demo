package com.sun.springbootdemo.entities;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;


/**
 * @describtion: 人类
 * @author: Sundz
 * @date: 2020/5/10 21:52
 */
@Data
@Log4j2
@PropertySource(value = {"classpath:common.properties"})
@ConfigurationProperties(prefix = "person")
@Configurable(preConstruction = true)
@Component
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@ConditionalOnMissingBean
@ApiModel(value = "Person", description = "Person对象")
public class Person implements Serializable {

    private static final long serialVersionUID = 1287693690722281369L;

    private Long id;

    @ApiModelProperty(name = "name", value = "姓名", example = "夏侯惇")
    @JsonAlias(value = {"name", "Name"})
    @NotNull(message = "姓名不能为null!")
    private String name;

    @ApiModelProperty(name = "age", value = "年龄", example = "25")
    @JsonProperty(value = "Age")
    @JsonAlias(value = {"age", "id"})
    @Range(min = 20, max = 60, message = "年龄只能在20岁到60岁之间!")
    private int age;

    @ApiModelProperty(name = "sex", value = "性别", example = "m")
    @Length(min = 1, max = 1, message = "性别只能为一个字符!")
    @Pattern(regexp = "[mf]", flags = {Pattern.Flag.CASE_INSENSITIVE, Pattern.Flag.COMMENTS}, message = "性别只能为m或者f")
    private String sex;

    @ApiModelProperty(name = "isBoss", value = "是否为老板", example = "true")
    //@AssertTrue(message = "默认为boss!")
    private boolean isBoss;

    @ApiModelProperty(name = "birthDay", value = "生日", example = "1992-01-01")
    @JsonProperty("birthDay")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat
    private Date birthDay;

    @ApiModelProperty(name = "pet", value = "pet对象")
    @JsonProperty(value = "pet")
    // 嵌套校验  必须使用@Valid注解
    @Valid
    private Pet pet;

}
