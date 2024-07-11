//package jpabook.jpashop;
//
//import jpabook.jpashop.config.RedisConfig;
//import jpabook.jpashop.dtos.ServiceDto;
//import jpabook.jpashop.service.TodoService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.cache.CacheManager;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.context.ContextConfiguration;
//
//import java.util.List;
//
//@SpringBootTest
//@ContextConfiguration(classes = {RedisConfig.class})
//public class TodoServiceTest {
//
//    @Autowired
//    TodoService todoService;
//
//    @Autowired
//    CacheManager cacheManager;
//
//    @Test
//    @DisplayName("캐싱 속도 테스트")
//    void testCacheSpeed(){
//        cacheManager.getCache("topMembers").clear();
//
//
//        List<ServiceDto.TopMember> notCaches = todoService.getTopMembers();
//
//
//
//        long startWithCache = System.currentTimeMillis();
//        List<ServiceDto.TopMember> withCaches = todoService.getTopMembers();
//        long endWithCache = System.currentTimeMillis();
//        System.out.println("캐시 미적용 시 : " + (endWithCache - startWithCache) + " ms");
//
//        assert notCaches.equals(withCaches);
//
//
//    }
//}
