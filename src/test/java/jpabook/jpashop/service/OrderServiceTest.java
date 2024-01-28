package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockExcption;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    // JPA가 잘엮여서 동작하는가가 중점이기에 단위테스트보단 통합적으로 테스트 진행
    @Test
    void 상품주문() throws Exception{
        //given
        Member member = createMember();
        Book book = createBook("JPA 연습", 10000, 10);

        int orderCount = 2;
        
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        
        //then
        Order getOrder= orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1,getOrder.getOrderItems().size(),"주문한 상품 종류 수가 정확해야함");
        assertEquals(10000*orderCount,getOrder.getTotalPrice(),"주문한 상품 종류 수가 정확해야함");
        assertEquals(8,book.getStockQuantity()," 주문 수량만큼 재고가 감소해야 함");
    }



    @Test
    void 상품주문_재고수량초과() throws Exception{
        //given
        Member member = createMember();
        Book book = createBook("JPA 연습", 10000, 10);

        int orderCount =11;

        //when

        //then
        assertThrows(NotEnoughStockExcption.class,
                ()->orderService.order(member.getId(), book.getId(), orderCount)
                ,"재고 수량보다 많이 주문 예외 상황");
    }

    @Test
    void 주문취소() throws Exception{
        //given
        Member member = createMember();
        Book item = createBook("jpa 학습", 10000, 10);

        int orderCount=2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        //when
        orderService.cancleOrder(orderId);
        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCLE,getOrder.getStatus(),"주문 취소시 상태는 Cancle");
        assertEquals(10,item.getStockQuantity(),"주문 취소시 재고 수량 복구");
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강남","123-123"));
        em.persist(member);
        return member;
    }

}