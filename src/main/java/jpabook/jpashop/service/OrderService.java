package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.dto.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    /**
     *  주문
     */
    @Transactional
    public Long order(Long memberId,Long itemId,int count){
        // 1. 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);
        if(item.getStockQuantity()<count){
            throw new NotEnoughStockException("수량이 없습니다.");
        }
        // 2. 배송정보 생성
        Delivery delivery= new Delivery(member.getAddress());
        // 3. 주문상품 생성
        OrderItem orderItem = new OrderItem(item,item.getPrice(),count);
        // 4. 주문 생성
        Order order = new Order(member,delivery,orderItem);

        // 5. 주문 저장
        orderRepository.save(order);
        return order.getId();
    }


    /**
     *  주문 취소
     */

    @Transactional
    public void cancleOrder(Long orderId){
        // 엔티티 조회 -> 로직 실행
        orderRepository.findOne(orderId).cancel();
    }

    /**
     *  검색
     */
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.queryDSL(orderSearch);
    }
}
