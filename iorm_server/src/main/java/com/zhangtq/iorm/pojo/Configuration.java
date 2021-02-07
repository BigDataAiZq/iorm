package com.zhangtq.iorm.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    //数据源
    private DataSource dataSource;
    //map集合： key:statementId(namespace + sql id) value:MappedStatement
    private Map<String,MappedStatement> mappedStatementMap = new HashMap<String,
                MappedStatement>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
