package com.sun.springbootdemo;

import com.mongodb.client.result.UpdateResult;
import com.sun.springbootdemo.entities.mongo.ClassMongo;
import com.sun.springbootdemo.entities.mongo.StudentMongo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * <p> mongodb测试 </p>
 *
 * @author Sundz
 * @date 2021/7/24 11:57
 */
public class MongoDbTest extends BaseJnuit5Test {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void queryTest() {
        Query query = new Query(Criteria.where("id").is("60ccb34dfc502c2028351188"));
        StudentMongo stu = mongoTemplate.findOne(query, StudentMongo.class);
        System.out.println(stu);
    }

    @Test
    public void updateTest() {
        Query query = new Query(Criteria.where("name").is("一班"));
        Update update = new Update().set("stu_count", 50);
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, ClassMongo.class);
        long matchedCount = updateResult.getMatchedCount();
        

    }

    @Test
    public void insertTest() {
        ClassMongo save = new ClassMongo();
        save.setName("五班");
        save.setStuCount(60L);
        save.setTeacher("123");
        ClassMongo result = mongoTemplate.save(save);
        System.out.println(result);

    }
}
