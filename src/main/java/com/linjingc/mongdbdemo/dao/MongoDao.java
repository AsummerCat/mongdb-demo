package com.linjingc.mongdbdemo.dao;


import com.linjingc.mongdbdemo.entity.User;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MongoDao {
	@Autowired
	private MongoTemplate mongoTemplate;


	/**
	 * 保存
	 */
	public User save(User user) {
		User resultData = mongoTemplate.save(user);
		return resultData;
	}

	/**
	 * 批量插入
	 * @param users
	 * @return
	 */
	public List<User> batchSave(List<User> users) {
		List<User> data = mongoTemplate.insert(users);
		return data;
	}



	/**
	 * 查找名称
	 */
	public User findUserByName(String name) {
		Query query = new Query(Criteria.where("name").is(name));
		User one = mongoTemplate.findOne(query, User.class);
		return one;
	}

	/**
	 * 更新对象
	 */
	public void updateUser(User user) {
		Query query = new Query(Criteria.where("id").is(user.getId()));


		Query query1 = new Query(Criteria.where("id").lt(user.getId()).and("id").in("1"));

		Update update = new Update().set("age", user.getAge()).set("name", user.getName());
		//更新查询返回结果集的第一条
		UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User.class);
		//更新查询返回结果集的所有
//		   mongoTemplate.updateMulti(query, update, User.class);
	}


	/**
	 * 删除对象
	 */
	public void deleteUser(User user) {
		Query query = new Query(Criteria.where("id").is(user.getId()));
		DeleteResult remove = mongoTemplate.remove(query, User.class);
//		mongoTemplate.remove(user);
	}


}
