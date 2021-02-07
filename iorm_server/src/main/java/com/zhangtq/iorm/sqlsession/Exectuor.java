package com.zhangtq.iorm.sqlsession;

import com.zhangtq.iorm.pojo.Configuration;
import com.zhangtq.iorm.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * 执行器接口
 */
public interface Exectuor {
    public <E> List<E> selectList(Configuration configuration, MappedStatement mappedStatement,Object... params) throws SQLException,ClassNotFoundException,
            IllegalAccessException,NoSuchFieldException, IntrospectionException,InstantiationException, InvocationTargetException;
}
