package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.dto.ItemForm;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    public void update(ItemForm form){
        this.name=form.getName();
        this.price=form.getPrice();
        this.stockQuantity=form.getStockQuantity();
    }

    public Item(ItemForm form){
        this.name=form.getName();
        this.price=form.getPrice();
        this.stockQuantity=form.getStockQuantity();
    }


    //==비지니스 로직==//

    /**
     * stock 증가
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /**
     *  stock 감소
     */
    public void removeStock(int quantity){
        int restStock =this.stockQuantity-quantity;

        if(restStock <0) throw new NotEnoughStockException("Need more stock");

        this.stockQuantity = restStock;
    }

}
