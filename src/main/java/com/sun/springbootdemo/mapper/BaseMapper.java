package com.sun.springbootdemo.mapper;

import java.util.List;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/8/28 18:01
 */
public interface BaseMapper<D> {

    int insertBatch(List<D> t);

}
