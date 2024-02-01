package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;


    //==생성 메서드==//
    @Builder
    public OrderItem(Item item,int orderPrice,int count){
        item.removeStock(count);

        this.item = item;
        this.orderPrice=orderPrice;
        this.count=count;
    }


    //==비지니스 로직==//

    // 1. 재고수량 복구
    public void cancel() {
        getItem().addStock(count);
    }

    //==조회 로직==//

    // 주문상품 전체 가격
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
