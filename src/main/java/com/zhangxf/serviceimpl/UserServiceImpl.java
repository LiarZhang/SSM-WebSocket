package com.zhangxf.serviceimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhangxf.dao.UserMapper;
import com.zhangxf.pojo.User;
import com.zhangxf.service.UserService;
@Service("userService")  
@Transactional  //注入事务管理 
public class UserServiceImpl implements UserService {  
	@Autowired
    private UserMapper userDao;  
    @Override  
    public User getUserById(int userId) {  
        return this.userDao.selectByPrimaryKey(userId);  
    }
}
