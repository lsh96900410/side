package jpabook.jpashop.dto;

import jpabook.jpashop.domain.Item;
import lombok.*;

@Data
@NoArgsConstructor

public class ItemForm {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    public ItemForm(Item item){
        this.id= item.getId();
        this.name=item.getName();
        this.price=item.getPrice();
        this.stockQuantity=item.getStockQuantity();
    }

}
