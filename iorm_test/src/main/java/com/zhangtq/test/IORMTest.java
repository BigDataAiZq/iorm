package com.zhangtq.test;

import com.zhangtq.dao.IUserDao;
import com.zhangtq.iorm.io.Resources;
import com.zhangtq.iorm.sqlsession.SqlSession;
import com.zhangtq.iorm.sqlsession.SqlSessionFactory;
import com.zhangtq.iorm.sqlsession.SqlSessionFactoryBuilder;
import com.zhangtq.pojo.User;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;


public class IORMTest {
    @Test
    public void test() throws PropertyVetoException, DocumentException,Exception {
        InputStream resourceStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //测试调用
       /* User u = new User();
        u.setId(1);
        u.setUsername("ztq");
        User u2 = sqlSession.selectOne("user.findOne",u);
        System.out.println(u2);*/
       /* List<User>  users = sqlSession.selectList("user.findAll");
        for (User user : users) {
            System.out.println(user);
        }*/

        IUserDao userDao = sqlSession.getMappper(IUserDao.class);
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
