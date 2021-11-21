package com.sun.springbootdemo.service.database;

import com.sun.springbootdemo.annotation.ColumnIndex;
import com.sun.springbootdemo.dto.UserDTO;
import com.sun.springbootdemo.entities.User;
import com.sun.springbootdemo.mapper.UserMapper;
import com.sun.springbootdemo.utils.ExcelUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
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
                throw new RuntimeException("没有导出数据，请重新选择筛选条件!");
            }
            CellStyle cellstyle = null;
            Map<String, Integer> userNumMap = getUserNum();
            int firstRow = 0;
            int repetitiveNum = 0;
            // 自适应列宽
            Map<Integer, Integer> autoAdaptWidthMap = new HashMap<>();
            for (int i = 0; i < size; i++) {
                Row row = sheet.createRow(i + 4);
                User user = userInfo.get(i);
                for (int j = 0; j < 3; j++) {
                    Cell cell = row.createCell(j);
                    String columnContent = String.valueOf(getColumnContent(j, user));
                    if (0 == j) {
                        // 合并单元格不需要单独特殊的格式
                        cellstyle = getCellStyle(workbook, 0);
                        cell.setCellStyle(cellstyle);
                        String userName = user.getUserName();
                        // 设置自适应宽度
                        setCellWidth(sheet, autoAdaptWidthMap, j, userName);
                        Integer userNum = userNumMap.get(userName);
                        // 非第一次写入且与上次用户名相同，则合并单元格
                        if (userNum > 1) {
                            // 记录合并单元格的首行
                            repetitiveNum++;
                            if (repetitiveNum == 1) {
                                firstRow = i + 4;
                                cell.setCellValue(columnContent);
                            }
                        } else {
                            cell.setCellValue(columnContent);
                        }
                        // 说明有重复需要合并
                        if (repetitiveNum == userNum) {
                            CellRangeAddress rangeAddress = new CellRangeAddress(firstRow, firstRow + repetitiveNum - 1, 0, 0);
                            sheet.addMergedRegion(rangeAddress);
                            repetitiveNum = 0;
                        }
                    } else {
                        // 设置填充颜色
                        if ((i & 1) == 1) {
                            cellstyle = getCellStyle(workbook, 1);
                        } else {
                            cellstyle = getCellStyle(workbook, 0);
                        }
                        cell.setCellStyle(cellstyle);
                        cell.setCellValue(columnContent);
                    }
                }
            }
            //设置contentType为vnd.ms-excel
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=users.xlsx");
            workbook.write(response.getOutputStream());
        } catch (IOException | IllegalAccessException e) {
            log.error(e.getMessage(), e);

        }
    }


    public CellStyle getCellStyle(Workbook workbook, int styleIndex) {
        CellStyle cellstyle = workbook.createCellStyle();
        // 设置边框
        cellstyle.setBorderLeft(BorderStyle.THIN);
        cellstyle.setBorderRight(BorderStyle.THIN);
        cellstyle.setBorderTop(BorderStyle.THIN);
        cellstyle.setBorderBottom(BorderStyle.THIN);
        // 设置字体居中
        cellstyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellstyle.setAlignment(HorizontalAlignment.CENTER);
        cellstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        if (styleIndex == 0) {
            cellstyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
        } else {
            cellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        }
        return cellstyle;
    }

    public Object getColumnContent(int columnIndex, User user) throws IllegalAccessException {
        Object result = null;
        for (Field field : User.class.getDeclaredFields()) {
            field.setAccessible(true);
            ColumnIndex annotation = AnnotationUtils.findAnnotation(field, ColumnIndex.class);
            if (Objects.isNull(annotation)) {
                continue;
            }
            if (annotation.index() == columnIndex) {
                result = field.get(user);
            }
        }
        return result;
    }

    /**
     * 设置列的宽度
     *
     * @param sheet             sheet页
     * @param autoAdaptWidthMap 自适应宽度
     * @param columnIndex       列索引
     * @param cellContent       列内容
     * @return void
     */
    public void setCellWidth(Sheet sheet, Map<Integer, Integer> autoAdaptWidthMap, int columnIndex, String cellContent) {
        Integer width = autoAdaptWidthMap.getOrDefault(columnIndex, 0);
        int length = cellContent.getBytes().length * 256;
        if (width < length) {
            width = length;
            autoAdaptWidthMap.put(columnIndex, width);
        }
        sheet.setColumnWidth(columnIndex, width);
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
