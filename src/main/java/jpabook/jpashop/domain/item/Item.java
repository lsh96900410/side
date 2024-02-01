package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.CategoryItem;
import jpabook.jpashop.dto.item.BookForm;
import jpabook.jpashop.dto.item.ItemForm;
import jpabook.jpashop.exception.NotEnoughStockExcption;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter //@Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<CategoryItem> categoryItems = new ArrayList<>();


    public void update(BookForm form){
        this.name=form.getName();
        this.price=form.getPrice();
        this.stockQuantity=form.getStockQuantity();
    }

    public Item(BookForm form){
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

        if(restStock <0) throw new NotEnoughStockExcption("Need more stock");

        this.stockQuantity = restStock;
    }

}
