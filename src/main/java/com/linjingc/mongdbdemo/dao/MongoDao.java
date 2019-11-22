package com.linjingc.mongdbdemo.dao;


import com.linjingc.mongdbdemo.entity.User;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

import static org.springframework.data.domain.Sort.Direction;

@Component
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
	 *
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
	@Transactional(rollbackFor = {Exception.class})
	public void updateUserAddress(User user) {
		Query query = new Query(Criteria.where("name").is(user.getName()));


//		Query query1 = new Query(Criteria.where("id").lt(user.getId()).and("id").in("1"));

		Update update = new Update().set("address", "第四次");
		//更新查询返回结果集的第一条
		UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User.class);
		updateResult.getMatchedCount();
		//更新查询返回结果集的所有
//		   mongoTemplate.updateMulti(query, update, User.class);
	}


	/**
	 * 删除对象
	 */
	public void deleteUser(User user) {
		Query query = new Query(Criteria.where("name").is(user.getName()));
		DeleteResult remove = mongoTemplate.remove(query, User.class);
//		mongoTemplate.remove(user);
	}

	/**
	 * 模糊查询
	 *
	 * @param search
	 * @return
	 */
	public List<User> findByLikes(String search) {
		Query query = new Query();

//完全匹配
		//	Pattern pattern1 = Pattern.compile("^张$", Pattern.CASE_INSENSITIVE);
//右匹配
		//	Pattern pattern2 = Pattern.compile("^.*张$", Pattern.CASE_INSENSITIVE);
//左匹配
		//Pattern pattern3 = Pattern.compile("^张.*$", Pattern.CASE_INSENSITIVE);
//模糊匹配
		//	Pattern pattern4 = Pattern.compile("^.*张.*$", Pattern.CASE_INSENSITIVE);

		Pattern pattern = Pattern.compile("^.*" + search + ".*$", Pattern.CASE_INSENSITIVE);
		Criteria criteria = Criteria.where("name").regex(pattern);
		query.addCriteria(criteria);
		List<User> lists = mongoTemplate.findAllAndRemove(query, User.class);
		return lists;
	}


	/**
	 * 排序查询
	 */
	public List<User> sortData() {
		Sort sort = Sort.by(Direction.ASC, "age");
//		Criteria criteria = Criteria.where("address").is(0);//查询条件
//		Query query = new Query(criteria);
		List<User> users = mongoTemplate.find(new Query().with(sort), User.class);
		return users;
	}

	/**
	 * 获取指定条数
	 */
	public List<User> limitData() {
		Sort sort = Sort.by(Direction.ASC, "age");
//		Criteria criteria = Criteria.where("address").is(0);//查询条件
//		Query query = new Query(criteria);
		List<User> users = mongoTemplate.find(new Query().limit(3), User.class);
		return users;
	}

	/**
	 * 分页查询
	 * @return
	 */
	public List<User> pageData(){
		//页数从0开始
		int page=0;
		int size=2;
	Query query = new Query();
	Pageable pageable = PageRequest.of(page, size);
	List<User> resolveRules = mongoTemplate.find(query.with(pageable), User.class);

	return resolveRules;
}

}
