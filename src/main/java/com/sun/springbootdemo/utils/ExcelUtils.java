/*
package com.sun.springbootdemo.utils;

import com.sun.springbootdemo.entities.User;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

*/
/**
 * <p> excel处理工具 </p>
 *
 * @author Sundz
 * @date 2021/5/9 9:27
 * <p>
 * 导出excel数据
 * @param heads
 * @param users
 * @return {@link Workbook}
 * <p>
 * 导入数据
 * @param filePath
 * @return {@link List<List<String>>}
 * <p>
 * 初始化反射参数
 * @param content
 * @param type
 * @return {@link Object}
 * <p>
 * 获取工作簿
 * @param filePath 文件路径
 * @return {@link Workbook}
 * <p>
 * 设置excel样式
 * @param workbook
 * @param ishead
 * @return {@link CellStyle}
 * <p>
 * 获取其中的get方法
 * @param t
 * @return {@link Map<Integer, Method>}
 *//*

@Log4j2
public final class ExcelUtils {

    private static final String EXCEL_XLS = "xls";

    private static final String EXCEL_XLSX = "xlsx";

    */
/**
 * 导出excel数据
 *
 * @param heads
 * @param users
 * @return {@link Workbook}
 *//*

    public static Workbook exportData(String[] heads, List<User> users) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("用户信息");
        sheet.setDefaultColumnWidth(30);
        Row head = sheet.createRow(0);
        head.setHeight((short) 250);
        int headNum = heads.length;
        // 设置表头
        for (int i = 0; i < headNum; i++) {
            Cell cell = head.createCell(i, 1);
            cell.setCellStyle(getCellStyle(workbook, true));
            cell.setCellValue(heads[i]);
        }
        Map<Integer, Method> methodMap = getMethods(User.class);
        Row row;
        for (int i = 0; i < users.size(); i++) {
            try {
                row = sheet.createRow(i + 1);
                row.setHeight((short) 250);
                User user = users.get(i);
                if (Objects.isNull(user)) {
                    continue;
                }
                for (int j = 0; j < headNum; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellStyle(getCellStyle(workbook, false));
                    cell.setCellValue(String.valueOf(methodMap.get(j).invoke(user, null)));
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error(e.getMessage(), e);
            }
        }
        return workbook;
    }


    */
/**
 * 导入数据
 *
 * @param filePath
 * @return {@link List<List<String>>}
 *//*

    public static List<User> importData(final String filePath) {
        Workbook workbook = getWorkbook(filePath);
        if (Objects.isNull(workbook)) {
            return null;
        }
        // 获取工作簿的sheet数
        int sheetSize = workbook.getNumberOfSheets();
        if (sheetSize <= 0) {
            return null;
        }
        List<User> result = new ArrayList<>();
        User user;
        Map<Integer, Method> methodMap = getMethods(User.class);
        for (int i = 0; i < sheetSize; i++) {
            // 获取sheet
            Sheet sheet = workbook.getSheetAt(i);
            int rowNum = sheet.getLastRowNum();
            if (rowNum <= 0) {
                continue;
            }
            for (int j = 1; j < rowNum + 1; j++) {
                try {
                    user = new User();
                    // 获取行
                    Row row = sheet.getRow(j);
                    short cellNum = row.getLastCellNum();
                    if (cellNum <= 0) {
                        continue;
                    }
                    for (short k = 0; k < cellNum; k++) {
                        Cell cell = row.getCell(k);
                        String content = cell.getStringCellValue();
                        Method method = methodMap.get(k + 6);
                        String type = method.getParameters()[0].getType().getName();
                        Object arg = transformType(content, type);
                        if (Objects.isNull(arg)) {
                            continue;
                        }
                        // 反射初始化实例
                        method.invoke(user, arg);
                    }
                    result.add(user);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    */
/**
 * 初始化反射参数
 *
 * @param content
 * @param type
 * @return {@link Object}
 *//*

    private static Object transformType(String content, String type) {
        Object obj = null;
        if (StringUtils.isBlank(content)) {
            return null;
        }
        switch (type) {
            case "java.lang.Long":
                obj = new Long(content);
                break;
            case "java.lang.Integer":
                obj = new Integer(content);
                break;
            case "java.lang.Short":
                obj = new Short(content);
                break;
            case "java.lang.Double":
                obj = new Double(content);
                break;
            case "java.lang.Float":
                obj = new Float(content);
                break;
            case "java.lang.Character":

                obj = Character.valueOf(content.toCharArray()[0]);
                break;
            case "java.lang.Byte":
                obj = new Byte(content);
                break;
            case "java.lang.Boolean":
                obj = Boolean.valueOf(content);
                break;
            case "java.lang.String":
                obj = content;
                break;
            case "java.util.Date":
                TemporalAccessor parse = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").parse(content);
                LocalDateTime localDateTime = LocalDateTime.from(parse);
                obj = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                break;
            default:
                //obj = content;
                break;
        }
        return obj;
    }


    */
/**
 * 获取工作簿
 *
 * @param filePath 文件路径
 * @return {@link Workbook}
 *//*

    private static Workbook getWorkbook(final String filePath) {
        File file = new File(filePath);
        Workbook workbook = null;
        try {
            InputStream in = new FileInputStream(file);
            if (file.getName().endsWith(EXCEL_XLS)) {
                workbook = new HSSFWorkbook(in);
            } else {
                workbook = new XSSFWorkbook(in);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return workbook;
    }


    */
/**
 * 设置excel样式
 *
 * @param workbook
 * @param ishead
 * @return {@link CellStyle}
 *//*

    private static CellStyle getCellStyle(Workbook workbook, boolean ishead) {
        CellStyle centerstyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);
        if (ishead) {
            font.setFontHeightInPoints((short) 11);
            font.setBold(true);
        }
        centerstyle.setFont(font);
        centerstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        centerstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        centerstyle.setWrapText(true);
        centerstyle.setLeftBorderColor(HSSFColor.BLACK.index);
        centerstyle.setBorderLeft(BorderStyle.THIN);
        centerstyle.setRightBorderColor(HSSFColor.BLACK.index);
        centerstyle.setBorderRight(BorderStyle.THIN);
        centerstyle.setBorderBottom(BorderStyle.THIN); // 设置单元格的边框为粗体
        centerstyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．
        centerstyle.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．
        return centerstyle;
    }

    */
/**
 * 获取其中的get方法
 *
 * @param t
 * @return {@link Map<Integer, Method>}
 *//*

    private static <T> Map<Integer, Method> getMethods(Class<T> t) {
        Map<Integer, Method> methodMap = new HashMap<>(8);
        Method[] methods = t.getDeclaredMethods();
        for (Method method : methods) {
            if (Objects.isNull(method)) {
                continue;
            }
            String name = method.getName();
            switch (name) {
                case "getId":
                    methodMap.put(0, method);
                    break;
                case "getUserName":
                    methodMap.put(1, method);
                    break;
                case "getPassWord":
                    methodMap.put(2, method);
                    break;
                case "getAge":
                    methodMap.put(3, method);
                    break;
                case "getRoleEnum":
                    methodMap.put(4, method);
                    break;
                case "getDate":
                    methodMap.put(5, method);
                    break;
                case "setId":
                    methodMap.put(6, method);
                    break;
                case "setUserName":
                    methodMap.put(7, method);
                    break;
                case "setPassWord":
                    methodMap.put(8, method);
                    break;
                case "setAge":
                    methodMap.put(9, method);
                    break;
                case "setRoleEnum":
                    methodMap.put(10, method);
                    break;
                case "setDate":
                    methodMap.put(11, method);
                    break;
                default:
                    break;
            }
        }
        return methodMap;
    }
}
*/
