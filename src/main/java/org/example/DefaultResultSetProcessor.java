package org.example;

import java.lang.reflect.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultResultSetProcessor<T> implements ResultSetProcessor<T>{

    @Override
    public List<T> process(ResultSet rs) {
        List<T> list = new ArrayList<T>();
        Class tClass = ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);

        try {
            Constructor<T> constructor = tClass.getConstructor();
            while (rs.next()){

                T object = constructor.newInstance();
                Field[] fields = tClass.getFields();

                for (Field field : fields) {

                    Column annotation = field.getAnnotation(Column.class);
                    if (annotation == null){
                        continue;
                    }
                    String columnName = annotation.columnName();
                    String stringValue = rs.getString(columnName);

                    field.setAccessible(true);
                    field.set(object, stringValue);
                    field.setAccessible(false);
                }
                list.add(object);
            }

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return list;
    }
}
