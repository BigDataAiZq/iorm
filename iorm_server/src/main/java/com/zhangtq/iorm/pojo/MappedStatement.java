package com.zhangtq.iorm.pojo;

/**
 * mapper映射配置 对应一个 select、update、insert、delete标签
 * 即 一条sql语句
 */
public class MappedStatement {
    //id标识
    private String id;
    //参数类型
    private  String paramterType;
    //返回值类型
    private String resultType;
    //sql语句
    private String sql;

    public String getId() {
        return id;
    }

    public String getParamterType() {
        return paramterType;
    }

    public String getResultType() {
        return resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setParamterType(String paramterType) {
        this.paramterType = paramterType;
    }
}
