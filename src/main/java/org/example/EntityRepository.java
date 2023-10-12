package org.example;

import java.util.List;

public class EntityRepository {
    private DAO dao;

    <T> T save(T entity){
        return null;
    };

    public <T> List<T> findAll(Class<T> entityClass){
        String sql = SQLBuilder.buildSelect(entityClass);
        DefaultResultSetProcessor<T> rsProcessor = new DefaultResultSetProcessor<T>();

        return dao.execute(sql, rsProcessor);
    };

    <T, ID> T findById(Class<T> entityClass, ID id){
        return null;
    }

    public EntityRepository(DAO dao) {
        this.dao = dao;
    }
}
