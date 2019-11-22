//package com.linjingc.mongdbdemo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.MongoTransactionManager;
//
///**
// * mongodb添加事务配置
// * 需要注意的是
// *升级独立版本到mongodb 4.0
// * 升级副本集到mongodb 4.0
// * 升级分片到mongodb 4.0
// * ***********************
// * ****事务仅针对集群*****
// * ***********************
// * @author cxc
// *
// */
//@Configuration
//public class TransactionConfig {
//
//    @Bean
//    MongoTransactionManager transactionManager(MongoDbFactory factory){
//        return new MongoTransactionManager(factory);
//    }
//
//}