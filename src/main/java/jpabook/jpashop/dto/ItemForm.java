package jpabook.jpashop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Item;
import lombok.*;

@Data
@NoArgsConstructor
@Getter @Setter
public class ItemForm {
    private Long id;
    @NotEmpty(message = " 제품 명을 등록해주세요 ")
    private String name;
    @Min(message = "최소 금액을 입력해주세요" ,value = 1)
    private int price;
    @Min(message = "최소 한 개 이상 등록해주세요" ,value = 1)
    private int stockQuantity;

    public ItemForm(Item item){
        this.id= item.getId();
        this.name=item.getName();
        this.price=item.getPrice();
        this.stockQuantity=item.getStockQuantity();
    }

}
