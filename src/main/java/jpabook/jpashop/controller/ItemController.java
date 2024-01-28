package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form",new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm bookForm){

        Book book = new Book();
        book.setName(bookForm.getName());
        book.setPrice(bookForm.getPrice());
        book.setAuthor(bookForm.getAuthor());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setIsbn(bookForm.getIsbn());

        itemService.saveItem(book);

        return "redirect:/";

    }

    @GetMapping("/items")
    public String list(Model model){
        model.addAttribute("items",itemService.findItems());
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId")Long itemId,Model model){
        Book item = (Book) itemService.findOne(itemId);

        BookForm form =new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());
        form.setStockQuantity(item.getStockQuantity());

        model.addAttribute("form",form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId,@ModelAttribute BookForm form){

        // 준영속 상태의 객체 : JPA가 식별할 수 있는 PK 존재
        // ==> 영속성 컨텍스트의 관리 영역 밖 = 변경 감지 X -> DB 업데이트 안됨
        // ==> 1. 변경 감지 기능 사용하기 , 병합 사용

        Book book = new Book();
        book.setId(form.getId());
        book.setAuthor(form.getAuthor());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setIsbn(form.getIsbn());
        itemService.saveItem(book);

        // 트랜잭션이 있는 서비스 계층에 식별자 ID 와 변경할 데이터들 정확하게 전달
        // itemService.updateItem(Long itemId,int form.getPrice(),form.getName(),form.getStockPrice())
        // itemService.updateItem(Long itemId,UpdateDto updateDto)
        return "redirect:/items";
    }

}
