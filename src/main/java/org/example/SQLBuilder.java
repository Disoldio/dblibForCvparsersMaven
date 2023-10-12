package org.example;


import java.lang.reflect.Field;

public class SQLBuilder {

    public static String buildSelect(Class<?> entityClass){
        Table annotation = entityClass.getAnnotation(Table.class);

        if (annotation == null){
            throw new RuntimeException("this is not entity");
        }
        String s = annotation.tableName();


        return "Select * FROM " + s + ";";
    }

    public static String buildInsert(Object object, Class entityClass){
        StringBuilder stringBuilder = new StringBuilder();
        Table annotation = (Table) entityClass.getAnnotation(Table.class);

        if(annotation == null){
            throw  new RuntimeException("this is not entity");
        }

        String tableName = annotation.tableName();
        stringBuilder.append("INSERT INTO ").append(tableName).append(" (");
        Field[] fields = entityClass.getFields();
        int countField = 0;

        for (Field field : fields) {
            Column annotation1 = field.getAnnotation(Column.class);
            if (annotation1 != null){
                stringBuilder.append(annotation1.columnName()).append(",");
                countField++;
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.append(") VALUES(");

        for (int i = 0; i < countField; i++) {
            stringBuilder.append("?").append(",");
        }

        stringBuilder.deleteCharAt(stringBuilder.length()-1).append(");");

        return stringBuilder.toString();
    }
}
