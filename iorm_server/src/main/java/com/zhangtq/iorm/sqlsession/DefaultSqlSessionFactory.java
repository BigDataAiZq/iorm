package com.zhangtq.iorm.sqlsession;

import com.zhangtq.iorm.pojo.Configuration;

public class DefaultSqlSessionFactory implements  SqlSessionFactory {
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
