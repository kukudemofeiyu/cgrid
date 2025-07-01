package com.things.cgomp.common.core.handler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.things.cgomp.common.core.utils.JacksonUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * 参考 {@link com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler} 实现
 * 在我们将字符串反序列化为 Set 并且泛型为 Long 时
 */
public class JsonLongSetTypeHandler extends BaseTypeHandler<Object> {

    private static final TypeReference<Set<Long>> typeReference = new TypeReference<Set<Long>>(){};

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, toJson(parameter));
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        final String json = rs.getString(columnName);
        return StringUtils.isEmpty(json) ? null : parse(json);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        final String json = rs.getString(columnIndex);
        return StringUtils.isEmpty(json) ? null : parse(json);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        final String json = cs.getString(columnIndex);
        return StringUtils.isEmpty(json) ? null : parse(json);
    }

    protected Object parse(String json) {
        return JacksonUtils.parseObject(json, typeReference);
    }

    protected String toJson(Object obj) {
        return JacksonUtils.toJsonString(obj);
    }
}
