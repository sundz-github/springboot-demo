package com.sun.springbootdemo.service.database;

import com.sun.springbootdemo.dto.UserDTO;
import com.sun.springbootdemo.entities.User;
import com.sun.springbootdemo.mapper.UserMapper;
import com.sun.springbootdemo.utils.ExcelUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p> 用户服务 </p>
 *
 * @author Sundz
 * @date 2021/5/2 20:03
 */
@Service
@Log4j2
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;


    /**
     * 将数据写入Excel
     *
     * @param response
     * @return void
     */
    public void writeExcel(HttpServletResponse response) {
        try (InputStream is = ExcelUtils.class.getClassLoader().getResourceAsStream("用户导出模板.xlsx")) {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            List<User> userInfo = getUserInfo();
            int size = userInfo.size();
            if (size == 0) {
                throw new RuntimeException("没有导出数据，请重新选择帅选条件!");
            }
            CellStyle cellstyle = workbook.createCellStyle();
            cellstyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellstyle.setAlignment(HorizontalAlignment.CENTER);
            Map<String, Integer> userNum = getUserNum();
            for (int i = 0; i < size; i++) {
                Row row = sheet.createRow(i + 4);
                User user = userInfo.get(i);
                for (int j = 0; j < 3; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellStyle(cellstyle);
                    switch (j) {
                        case 0:
                            String userName = user.getUserName();
                            /*Integer count = userNum.get(userName);*/
                            // 非第一次写入且与上次用户名相同，则合并单元格
                            if (0 != i && null != userName && userName.equals(userInfo.get(i - 1).getUserName())) {
                                CellRangeAddress rangeAddress = new CellRangeAddress(i + 3, i + 4, 0, 0);
                                sheet.addMergedRegion(rangeAddress);
                            } else {
                                cell.setCellValue(user.getUserName());
                            }
                            break;
                        case 1:
                            cell.setCellValue(user.getPassWord());
                            break;
                        case 2:
                            cell.setCellValue(user.getAge());
                            break;
                        default:
                            break;
                    }
                }
            }
            //设置contentType为vnd.ms-excel
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=users.xlsx");
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            log.error(e.getMessage(), e);

        }
    }

    /**
     * 获取用户信息
     *
     * @param
     * @return {@link List<User>}
     */
    public List<User> getUserInfo() {
        return userMapper.getUserInfo();
    }

    /**
     * 获取同名的用户数
     *
     * @param
     * @return {@link Map<String, Integer>}
     */
    public Map<String, Integer> getUserNum() {
        List<Map<String, Integer>> userNum2Count = userMapper.selectUserNum();
        if (CollectionUtils.isNotEmpty(userNum2Count)) {
            return userNum2Count.stream().collect(Collectors.toMap(key -> String.valueOf(key.get("userNum")), value -> Integer.parseInt(String.valueOf(value.get("num"))), (x, y) -> x));
        }
        return Collections.emptyMap();
    }


    public User update(Integer id, String name) {
        userMapper.update(id, name);
        return userMapper.selectOne(name);
    }

    @Transactional(rollbackFor = Exception.class)
    public int delUser(int id) {
        userMapper.deleteById(id);
        int a = 1 / 0;
        return 0;
    }

    public void test(int id) {
        delUser(id);
    }

    public List<UserDTO> queryAll() {
        List<User> users = userMapper.selectAll();
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyList();
        }
        return users.stream().filter(Objects::nonNull).map(it -> {
            UserDTO dto = new UserDTO();
            BeanUtils.copyProperties(it, dto);
            dto.setRoleEnum(Objects.isNull(it.getRoleEnum()) ? StringUtils.EMPTY : it.getRoleEnum().getRole());
            return dto;
        }).collect(Collectors.toList());
    }
}
