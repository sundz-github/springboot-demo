package com.sun.springbootdemo.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@Log4j2
public final class ExcelUtils {

    public static <T> ReadListener<T> getReadListener(Consumer<List<T>> action, int threshold) {
        return new AnalysisEventListener<T>() {
            final List<T> datas = new ArrayList<>();

            @Override
            public void invoke(T dto, AnalysisContext analysisContext) {
                datas.add(dto);
                if (datas.size() >= threshold) {
                    action.accept(datas);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                if (datas.size() > 0) {
                    action.accept(datas);
                }
            }
        };
    }
}