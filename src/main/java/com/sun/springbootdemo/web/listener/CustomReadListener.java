package com.sun.springbootdemo.web.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.sun.springbootdemo.dto.UserDTO;
import com.sun.springbootdemo.entities.RoleEnum;
import com.sun.springbootdemo.entities.User;
import com.sun.springbootdemo.mapper.BaseMapper;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/8/28 17:58
 */
public class CustomReadListener extends AnalysisEventListener<UserDTO> {

    private final BaseMapper<User> baseMapper;

    public CustomReadListener(BaseMapper<User> baseMapper) {
        this.baseMapper = baseMapper;
    }

    private final List<User> data = new ArrayList<>();

    @Override
    public void invoke(UserDTO dto, AnalysisContext analysisContext) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        String roleEnum = dto.getRoleEnum();
        user.setRoleEnum(EnumUtils.getEnum(RoleEnum.class, roleEnum));
        data.add(user);
        if (data.size() == 5) {
            baseMapper.insertBatch(data);
            data.clear();
        }
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        baseMapper.insertBatch(data);
    }

}
