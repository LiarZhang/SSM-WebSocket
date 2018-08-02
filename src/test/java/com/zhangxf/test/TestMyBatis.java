package com.zhangxf.test;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.zhangxf.pojo.User;
import com.zhangxf.service.UserService;


@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
  
public class TestMyBatis {  
   
	private ApplicationContext ac = null;  
    @Autowired  
    private UserService userService;  
  
    @Test  
    public void before() {  
      ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");  
      userService = (UserService) ac.getBean("userService"); 
  	  System.out.println(userService);
      User user = userService.getUserById(2);  
      System.out.println(user.getUsername()); 
  }  
}
