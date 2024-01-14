package com.hello.config;

import com.hello.entry.User;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserListTypeHandler extends BaseTypeHandler<List<User>> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<User> users, JdbcType jdbcType) throws SQLException {
        // 将 List<User> 转换为逗号分隔的字符串并设置到 PreparedStatement 中
        preparedStatement.setString(i, users.stream().map(User::getUserId).map(String::valueOf).collect(Collectors.joining(",")));
    }

    @Override
    public List<User> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        // 从 ResultSet 中获取字符串并转换为 List<User>
        return stringToUserList(resultSet.getString(s));
    }

    @Override
    public List<User> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return stringToUserList(resultSet.getString(i));
    }

    @Override
    public List<User> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return stringToUserList(callableStatement.getString(i));
    }

    private List<User> stringToUserList(String s) {
        // 将逗号分隔的字符串转换为 List<User>
        return Arrays.stream(s.split(",")).map(username -> {
            User user = new User();
            user.setUsername(username);
            return user;
        }).collect(Collectors.toList());

    }
}

