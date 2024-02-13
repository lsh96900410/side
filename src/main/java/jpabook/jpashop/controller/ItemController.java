package jpabook.jpashop.controller;

import jpabook.jpashop.dto.ItemForm;
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
        model.addAttribute("form",new ItemForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(ItemForm itemForm){
            itemService.saveItem(itemForm);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model){
        model.addAttribute("items",itemService.findItems());
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId")Long itemId,Model model){
        ItemForm form = itemService.updateViewForm(itemId);
        model.addAttribute("form",form);
        return "items/updateItemForm";
    }

    // 찾기 -> 수정 ?
    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId,@ModelAttribute ItemForm form){
        itemService.updateItem(itemId,form);
        return "redirect:/items";
    }

}
