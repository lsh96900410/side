package jpabook.jpashop.service;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.dto.ItemForm;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    @Transactional
    public void saveItem(ItemForm form) {
        Item item = new Item(form);
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, ItemForm form){
        Item findItem = itemRepository.findOne(itemId);
        findItem.update(form);
    }

    public ItemForm updateViewForm(Long itemId){
        Item one =itemRepository.findOne(itemId);
        ItemForm form=new ItemForm(one);
        return form;
    }




    public List<Item> findItems(){
        return itemRepository.findAll();
    }


}
