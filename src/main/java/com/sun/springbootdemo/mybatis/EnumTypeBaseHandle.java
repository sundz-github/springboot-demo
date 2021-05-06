package com.sun.springbootdemo.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/5/6 21:30
 */
public class EnumTypeBaseHandle extends BaseTypeHandler<EnumTypeHandle> {

    Class<EnumTypeHandle> type;

    public EnumTypeBaseHandle(Class<EnumTypeHandle> type) {
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, EnumTypeHandle parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.order());
    }

    @Override
    public EnumTypeHandle getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (!rs.wasNull()) {
            int value = rs.getInt(columnName);
            return getInstance(value);
        }
        return null;
    }

    @Override
    public EnumTypeHandle getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (!rs.wasNull()) {
            int value = rs.getInt(columnIndex);
            return getInstance(value);
        }
        return null;
    }

    @Override
    public EnumTypeHandle getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (!cs.wasNull()) {
            int value = cs.getInt(columnIndex);
            return getInstance(value);
        }
        return null;
    }

    /**
     * @field 获取枚举实例
     */
    private EnumTypeHandle getInstance(int order) {
        for (EnumTypeHandle e : type.getEnumConstants()) {
            if (order == e.order()) {
                return e;
            }
        }
        return null;
    }
}
