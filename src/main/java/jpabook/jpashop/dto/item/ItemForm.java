package jpabook.jpashop.dto.item;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.dto.item.BookForm;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class ItemForm {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    @Builder
    public ItemForm(Book book){
        this.id=book.getId();
        this.name=book.getName();
        this.price=book.getPrice();
        this.stockQuantity=book.getStockQuantity();
    }
}
