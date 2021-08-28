package com.sun.springbootdemo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/8/28 17:37
 */
@Data
public class UserDTO {

    @ExcelProperty(index = 0, value = "用户名")
    private String userName;

    @ExcelProperty(index = 1, value = "年龄")
    private Integer age;

    @ExcelProperty(index = 2, value = "密码")
    private String passWord;

    @ExcelProperty(index = 3, value = "角色")
    private String roleEnum;
}
