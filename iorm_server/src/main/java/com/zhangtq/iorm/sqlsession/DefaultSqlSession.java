package com.zhangtq.iorm.sqlsession;

import com.zhangtq.iorm.pojo.Configuration;
import com.zhangtq.iorm.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws  Exception{
        Exectuor exectuor = new SimpleExecutor();
        return exectuor.selectList(configuration,configuration.getMappedStatementMap().get(statementId),params);

    }

    @Override
    public <E> E selectOne(String statementId, Object... params) throws Exception{
        List<E> list = selectList(statementId,params);
        if(list.size() == 1){
            return list.get(0);
        }else{
            throw new RuntimeException("查询机结果为空或者结果过多");
        }
    }

    @Override
    public <T> T getMappper(Class<T> mapperClass) {
        Object proxyInstance = Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            //底层还是执行JDBC 根据不同的条件调用 findAll,还是findOne
            //准备参数 statementId :sql语句的唯一标识：namespace.id = 接口全限定名.方法名
            //方法名
            /**
             *
             * @param proxy 当前代理对象的引用
             * @param method 当前被调用方法的引用
             * @param args 方法的参数
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                //className:namespace
                String className = method.getDeclaringClass().getName();
                //statement
                String key = className + "." + methodName;
                MappedStatement mappedStatement = configuration.getMappedStatementMap().get(key);
                Type genericReturnType = method.getGenericReturnType();
                //判断是否存在泛型类型参数化
                if(genericReturnType instanceof ParameterizedType){
                    return selectList(key);
                }else{
                    return selectOne(key,args);
                }
            }
        });
        return (T)proxyInstance;
    }
}
