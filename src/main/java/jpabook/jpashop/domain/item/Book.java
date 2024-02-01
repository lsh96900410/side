package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpashop.dto.item.BookForm;
import jpabook.jpashop.dto.item.ItemForm;
import lombok.*;

@Entity
@DiscriminatorValue("B")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item{

    private String author;
    private String isbn;


    @Builder
    public Book(BookForm form){
        super(form);
        this.author=form.getAuthor();
        this.isbn=form.getIsbn();
    }



    // update 로직
    public void update(BookForm form){
        super.update(form);
        this.author=form.getAuthor();
        this.isbn=form.getAuthor();
    }

}
