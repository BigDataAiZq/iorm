package com.zhangtq.iorm.sqlsession;

import com.zhangtq.iorm.config.BoundSql;
import com.zhangtq.iorm.pojo.Configuration;
import com.zhangtq.iorm.pojo.MappedStatement;
import com.zhangtq.iorm.utils.GenericTokenParser;
import com.zhangtq.iorm.utils.ParameterMapping;
import com.zhangtq.iorm.utils.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements  Exectuor {
    @Override
    public <E> List<E> selectList(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException,ClassNotFoundException,
            IllegalAccessException,NoSuchFieldException, IntrospectionException,InstantiationException, InvocationTargetException {
        //1 注册驱动、获取连接
        Connection connection = configuration.getDataSource().getConnection();

        //2. 获取sql语句: select * from user where id= #{id} and userName= #{userName}
        // 转换sql语句： select * from user where id = ? and userName = ? 解析过程中要对#{}里面的数值进行解析存储
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        //3.获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        //4.设置参数
        //获取参数的全路径
        String paramterType = mappedStatement.getParamterType();
        Class<?> paramterTypeClass = getClassType(paramterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for(int i = 0 ;i < parameterMappingList.size(); i++){
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();  // 对象的属性名，#{}里面的变量  #{id} #{userName}

            //反射
            Field declaredField = paramterTypeClass.getDeclaredField(content);
            //设置暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i + 1,o);
        }

        //5.执行sql
        ResultSet rs = preparedStatement.executeQuery();

        //6.封装返回结果集
        String resutType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resutType);
        List<Object> objects = new ArrayList<>();

        while(rs.next()){
            Object o = resultTypeClass.newInstance();
            //元数据
            ResultSetMetaData rsm = rs.getMetaData();
            for(int i = 1; i <= rsm.getColumnCount(); i ++){
                //字段名
                String columName = rsm.getColumnName(i);
                //字段的数值
                Object object = rs.getObject(i);

                //使用反射或内省，根据数据库表中的字段名和实体的对应关系，完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columName,resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o,object);
            }
            objects.add(o);
        }
        return (List<E>)objects;
    }

    private Class<?> getClassType(String classType) throws ClassNotFoundException{
        if(classType != null){
            Class<?> aClass =  Class.forName(classType);
            return aClass;
        }
        return null;
    }
    /**
     * 完成对 #{}的解析工作： 1.将#{}使用？进行代替，2.解析出#{}里面的值进行存储
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql){
        //标记处理类：配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{","}",parameterMappingTokenHandler);
        //解析出来的sql
        String parseSql = genericTokenParser.parse(sql);
        //#{}解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        return new BoundSql(parseSql,parameterMappings);

    }


}
