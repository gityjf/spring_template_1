package com.learn.platform.dao;

import com.learn.po.User;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @program: spring_template_1
 * @author: yjf
 * @create: 2019-03-23 16:44
 **/
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int addUser(User user){
        String insertSql = "insert into t_student (user_name,age) values (?,?)";
        int row = jdbcTemplate.update(insertSql, user.getUserName(), user.getAge());
        return row;
    }

    public void ageLess(){
        String lessSql = "update t_student set age = age - ? where user_name =?";
        jdbcTemplate.update(lessSql,2,"小小");
    }

    public void ageMore(){
        String lessSql = "update t_student set age = age + ? where user_name =?";
        jdbcTemplate.update(lessSql,2,"小网");
    }

}
