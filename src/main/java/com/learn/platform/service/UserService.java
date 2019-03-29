package com.learn.platform.service;

import com.learn.platform.dao.UserDao;
import com.learn.po.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @program: spring_template_1
 * @author: yjf
 * @create: 2019-03-23 16:46
 **/

//@Transactional
public class UserService {

    @Resource(name = "userDao")
    private UserDao userDao;

//    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(User user){
        userDao.addUser(user);
        int i = 10/0;
    }
//    @Transactional(propagation = Propagation.REQUIRED)
    public void updateAge(){
        userDao.ageLess();
        userDao.ageMore();
//        int i = 10/0;
    }
}
