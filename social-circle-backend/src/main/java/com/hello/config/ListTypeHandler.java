package com.hello.config;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListTypeHandler implements TypeHandler<List<String>> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
        if (strings != null) {
            preparedStatement.setString(i, String.join(",", strings));
        } else {
            preparedStatement.setString(i, null);
        }
    }

    @Override
    public List<String> getResult(ResultSet resultSet, String s) throws SQLException {
        String result = resultSet.getString(s);
        return mapResult(result);
    }

    @Override
    public List<String> getResult(ResultSet resultSet, int i) throws SQLException {
        String result = resultSet.getString(i);
        return mapResult(result);
    }

    @Override
    public List<String> getResult(CallableStatement callableStatement, int i) throws SQLException {
        String result = callableStatement.getString(i);
        return mapResult(result);
    }

    private List<String> mapResult(String result) {
        if (result != null && !result.isEmpty()) {
            return Arrays.asList(result.split(","));
        } else {
            return Collections.emptyList();
        }
    }
}
