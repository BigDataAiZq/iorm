package com.zhangtq.iorm.sqlsession;

import com.zhangtq.iorm.config.XMLConfigBuilder;
import com.zhangtq.iorm.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * sqlsessionfactory构建类
 * 生成SqlSessionFactory类
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream in) throws DocumentException, PropertyVetoException {
        //1 使用Dom4j解析配置文件，将解析的内容封装到Configuration中
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(in);

        //2 创建SqlSessionFactory对象
        return new DefaultSqlSessionFactory(configuration);
    }
}
