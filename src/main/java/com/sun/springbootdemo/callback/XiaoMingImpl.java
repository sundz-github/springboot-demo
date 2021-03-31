package com.sun.springbootdemo.callback;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p> 回调接口实现类，根据策略、逻辑的不同会有多个 </p>
 *
 * @author Sundz
 * @date 2021/1/25 14:20
 */
@Log4j2
@Component
public class XiaoMingImpl implements CallBack {

    @Autowired
    private LiHua liHua;

    /**
     * 需要回调的代码逻辑
     *
     * @param param
     * @return void
     */
    @Override
    public void callBack(String param) {
        log.info("回调接口的实现类执行了！");
    }

    // 异步回调
    Thread xm = new Thread(() -> {
        System.out.println("XiaoMing处理事情!");
        execute();
    });

    public void execute() {
        try {
            // 此处还可以采用异步调用机制
            liHua.play(this, "一起玩篮球吧!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


}
