package com.linjingc.mongdbdemo.controller;

import com.linjingc.mongdbdemo.dao.MongoDao;
import com.linjingc.mongdbdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * MongDb测试类
 */
@RestController
@RequestMapping("test")
public class MongoDbController {

	@Autowired
	private MongoDao mongoDao;

	@RequestMapping("add")
	public String add() {
		User user = new User();
		user.setId(String.valueOf(System.currentTimeMillis()));
		user.setAddress("小明的地址");
		Random rand = new Random();
		user.setAge(rand.nextInt(100));
		user.setName("小明"+rand.nextInt(100));
		mongoDao.save(user);
		return "插入成功---->"+user.toString();
	}


	@RequestMapping("update")
	public String update(int num) {
		User user = new User();
		user.setId(String.valueOf(System.currentTimeMillis()));
		user.setAddress("小明的更新的地址");
		user.setName("小明"+num);
		mongoDao.updateUserAddress(user);
		return "更新成功---->";
	}

	@RequestMapping("find")
	public String find(int num) {
		User user = new User();
		user.setName("小明"+num);
		User userByName = mongoDao.findUserByName(user.getName());
		return "查找成功---->"+userByName.toString();
	}

	@RequestMapping("delete")
	public String delete(int num) {
		User user = new User();
		user.setName("小明"+num);
		 mongoDao.deleteUser(user);
		return "删除成功---->";
	}



}
