package com.sun.springbootdemo.service;

import com.sun.springbootdemo.service.entities.Person;
import com.sun.springbootdemo.service.entities.Result;
import com.sun.springbootdemo.service.exceptions.EntitiesException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;

/**
 * @Description:
 * @Author: Sundz
 * @Version: V1.0
 * @Date: 2020/8/7 13:47
 */
@ControllerAdvice
@Log4j2
public class ExceptionHandleService {


    /*@ExceptionHandler(value = EntitiesException.class)
    @ResponseBody
    public Map<String, Object> handleException(Exception e) {
        log.info("handleException方法开始处理异常了,!" + e.getMessage(), e);
        Map<String, Object> map = new HashMap<>(2);
        map.put("code", 200);
        map.put("error", e.getMessage());
        return map;
    }
*/
    @ExceptionHandler(value = EntitiesException.class)
    public ModelAndView handleException(Exception e) {
        log.info("handleException方法开始处理异常了,!" + e.getMessage(), e);
        ModelAndView modelAndView = new ModelAndView();
        //指定错误页面的模板页
        modelAndView.setViewName("error");
        modelAndView.addObject("code", HttpStatus.OK);
        modelAndView.addObject("msg", e.getMessage());
        return modelAndView;
    }

    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public Result<Person> ConstraintViolationExceptionHandler(Exception e) {
        log.error(e.getMessage() + ",方法入参或者实体不合法，请注意检查!");
        return new Result<>(HttpStatus.BAD_REQUEST.value(), "统一异常处理", null);
    }
}
