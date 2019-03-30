package com.learn.test;

import com.learn.platform.controller.UserController;
import com.learn.platform.service.UserService;
import com.learn.po.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @program: spring_template_1
 * @author: yjf
 * @create: 2019-03-22 21:55
 **/
public class SpringJdbcTemplateTest {


    private static final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
    private static final JdbcTemplate jdbcTemplate;
    static {
        jdbcTemplate = (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
    }

    @Test
    public void test1(){
//        User user = (User)applicationContext.getBean("user");
        UserController userCon = (UserController)applicationContext.getBean("userController");
        userCon.addUser();



    }

    @Test
    public void test2(){
        User user = (User)applicationContext.getBean("user");
        System.out.println(user.getUserName());

    }

    @Test
    public void test3(){
        Connection con ;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://192.168.5.188:3306/test","root","1234");
            PreparedStatement pst = con.prepareStatement("select * from t_student");
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString("user_name")+":"+rs.getString("age"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void test4(){
        UserService userService = (UserService)applicationContext.getBean("userService");
        userService.saveUser(new User("小语",26));
    }

    @Test
    public void test5(){
//        List<Object> users = jdbcTemplate.query("select * from t_student", new CommonObj(User.class));
        List<User> users = jdbcTemplate.query("select * from t_student", new BeanPropertyRowMapper<User>(User.class));
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()){
            User next =  iterator.next();
            System.out.println(next.getUserName());
        }
    }

    @Test
    public void test6(){
        UserService userService = (UserService)applicationContext.getBean("userService");
        userService.updateAge();
    }

    @Test
    public void test7(){
        User user =jdbcTemplate.queryForObject("select id,user_name,age from t_student where id = ? and age = ?",new Object[]{1,26},new BeanPropertyRowMapper<User>(User.class));
        System.out.println(user.getUserName());
    }




}

class UserRowMapper implements RowMapper<User>{
    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        Integer id = rs.getInt("id");
        String name = rs.getString("name");
        int age = rs.getInt("age");
        return new User(id,name,age);
    }
}

class CommonObj implements RowMapper<Object> {

    private Class<?> cl;

    public CommonObj(Class<?> cl) {
        this.cl = cl;
    }

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {

        try {
            Field[] fields = cl.getDeclaredFields();
            Object entity = cl.newInstance();
            for (Field f : fields) {
                f.setAccessible(true);
                this.typeMapper(f, entity, rs);
                f.setAccessible(false);
            }
            return entity;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private void typeMapper(Field field, Object obj, ResultSet rs) throws Exception {
        String type = field.getType().getName();
        String fieldName = field.getName();
        int upper = isAcronym(fieldName);
        if(upper != 99){
            fieldName = fieldName.substring(0,upper)+"_"+String.valueOf(fieldName.charAt(upper)).toLowerCase()
                    +fieldName.substring(upper+1,fieldName.length());
        }
        if (type.equals("java.lang.String")) {
            field.set(obj, rs.getString(fieldName));
        } else if (type.equals("int") || type.equals("java.lang.Integer")) {
            field.set(obj, rs.getInt(fieldName));
        } else if (type.equals("long") || type.equals("java.lang.Long")) {
            field.set(obj, rs.getLong(fieldName));
        } else if (type.equals("boolean") || type.equals("java.lang.Boolean")) {
            field.set(obj, rs.getBoolean(fieldName));
        } else if (type.equals("java.util.Date")) {
            field.set(obj, rs.getDate(fieldName));
        }
    }

    private  int isAcronym(String word) {
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!Character.isLowerCase(c) ) {
                return i;
            }
        }
        return 99;
    }

}
