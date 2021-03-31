package com.sun.springbootdemo.callback;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/1/25 15:17
 */
@Log4j2
@Component
public class LiHua {

    String play(CallBack callBack, String param) throws Exception {
        // 模拟LiHua执行任务
        TimeUnit.SECONDS.sleep(3);
        // 回调方法
        log.info("LiHua的任务已经执行完了!");
        callBack.callBack(param);
        return "LiHua的方法执行了!";
    }

}
