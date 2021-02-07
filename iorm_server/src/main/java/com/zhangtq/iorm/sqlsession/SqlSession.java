package com.zhangtq.iorm.sqlsession;

import java.util.List;

public interface SqlSession {

    //查询所有
    public <E> List<E> selectList(String statementId,Object...params) throws Exception;
    //查询单个
    public <E> E selectOne(String statementId,Object...params) throws Exception;
    //更新

    //删除

    //为Dao生成代理实现类
    public <T> T getMappper(Class<T> mapperClass);
}
