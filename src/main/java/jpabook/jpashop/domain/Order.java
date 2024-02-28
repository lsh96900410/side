package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // xToOne == default 값이 EAGER 거의 사용 X
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL) // Cascade == 영속성 전이 -> 함께 등록,제거 등 생명 주기가 같음
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    @CreationTimestamp
    private LocalDateTime orderDate; // 주문 시간 -> 자바8부터 하이버네이트 자동 지원

    @Enumerated(EnumType.STRING) // Enum 타입 지원 어노테이션
    private OrderStatus status;

    private int totalPrice;

    //== 연관관계 메서드 ==//
    private void setDelivery(Delivery delivery){
        this.delivery=delivery;
        delivery.setOrder(this);
    }
    private void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    @Builder
    public Order(Member member,Delivery delivery,OrderItem... orderItems){
        this.member=member;
        setDelivery(delivery);
        this.status=OrderStatus.ORDER;
        this.orderDate=LocalDateTime.now();
        this.totalPrice=getTotalPrice();
        for (OrderItem orderItem:orderItems){
            addOrderItem(orderItem);
        }
    }
    //== 비지니스 로직 ==//

     // 주문 취소
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw  new IllegalStateException("이미 배송완료된 상품");
        }
        this.status=OrderStatus.CANCLE;
        //this.setStatus(OrderStatus.CANCLE);
        for (OrderItem orderItem: orderItems) {
            orderItem.cancel();
        }
    }
    
    //==조회 로직==//
    //전체 주문 가격 조회
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice+=orderItem.getTotalPrice();
        }
        return totalPrice;
    }

}
