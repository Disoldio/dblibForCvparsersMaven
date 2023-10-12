package org.example;

import javax.swing.plaf.nimbus.State;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {
    private static  String url = null;
    private static  String username = null;
    private static  String password = null;

    public static void setCredentials(String url, String username, String password){
        DAO.url = url;
        DAO.username = username;
        DAO.password = password;
    }

    private static DAO dao;
    private Connection connection;

    public static DAO getInstance(){
        if (dao == null){
            return new DAO();
        }
        return dao;
    }
    private DAO(){
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Don`t work");
        }
    }

    public Boolean executeUpdate(String sql, PreparedStatementFiller preparedStatementFiller){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatementFiller.fill(preparedStatement);

            return preparedStatement.execute();
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    public <T> List<T> execute(String sql, ResultSetProcessor processor){
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            return processor.process(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean executeUpdate(String sql, Object object, Class entityClass){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            PreparedStatementFiller defaultPreparedStatementFiller = (ps) -> {
                Class<?> aClass = entityClass;
                Field[] fields = aClass.getFields();
                int index = 1;

                for (Field field : fields) {
                    if(field.getAnnotation(Column.class) != null){
                        field.setAccessible(true);
                        ps.setString(index,(String) field.get(object));
                        field.setAccessible(false);
                    }
                }
            };
            defaultPreparedStatementFiller.fill(preparedStatement);

            return preparedStatement.execute();
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }


}
