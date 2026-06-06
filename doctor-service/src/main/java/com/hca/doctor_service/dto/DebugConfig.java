//package com.hca.doctor_service.dto;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//@Configuration
//public class DebugConfig {
//
//    @Value("${spring.data.mongodb.uri}")
//    private String uri;
//    @Bean
//    CommandLineRunner beans(ApplicationContext context) {
//        return args -> {
//            String[] mongoTemplateBeans =
//                    context.getBeanNamesForType(
//                            org.springframework.data.mongodb.core.MongoTemplate.class);
//
//            System.out.println("=== MongoTemplate Beans ===");
//            for (String bean : mongoTemplateBeans) {
//                System.out.println(bean + " -> " + context.getBean(bean).getClass());
//            }
//        };
//    }
//
//    @Bean
//    CommandLineRunner runner() {
//        return args -> {
//            System.out.println("Mongo URI = " + uri);
//        };
//    }
//    @Bean
//    CommandLineRunner mongoDebug(MongoTemplate mongoTemplate) {
//        return args -> {
//            System.out.println("DB NAME = " + mongoTemplate.getDb().getName());
//        };
//    }
//}