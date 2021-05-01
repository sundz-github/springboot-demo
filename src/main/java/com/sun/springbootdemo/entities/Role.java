package com.sun.springbootdemo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 角色 </p>
 *
 * @author Sundz
 * @date 2021/4/18 19:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    private Long id;

    private String name;


    /**
     * @field 角色等级
     */
    private Integer grade;

}
