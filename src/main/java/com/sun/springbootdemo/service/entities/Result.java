package com.sun.springbootdemo.service.entities;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Objects;

/**
 * @description:
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/3 10:34
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -353366208103054376L;

    private int code;

    private String message;

    private T body;

    public Result(int code, String message, T body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public Result(T body) {
        this.code = HttpStatus.OK.value();
        this.message = "数据成功返回";
        this.body = body;
    }

    public Result() {
    }

    /**
     * @class 采用Builder形式初始化数据
     */
    public static class Builder<T> {

        private Result<T> result;

        public Builder() {
        }

        private Result<T> getInstance() {
            if (Objects.isNull(result)) {
                result = new Result<>();
            }
            return result;
        }

        public Builder<T> bodey(T body) {
            getInstance().setBody(body);
            return this;
        }

        public Result<T> build() {
            return this.getInstance();
        }
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", " + body.getClass().getSimpleName() + "=" + body +
                '}';
    }
}
