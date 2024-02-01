package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.dto.Response;
import jpabook.jpashop.dto.item.BookForm;
import jpabook.jpashop.dto.item.ItemForm;
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
    public void saveItem(BookForm form){
        Book book = new Book(form);
        itemRepository.save(book);
    }

    @Transactional
    public void updateItem(Long itemId, BookForm form){
        Book findItem = (Book)itemRepository.findOne(itemId);
        findItem.update(form);
    }

    public BookForm updateViewForm(Long itemId){
        Book one =(Book)itemRepository.findOne(itemId);
        BookForm form=new BookForm(one);
        return form;
    }




    public List<Item> findItems(){
        return itemRepository.findAll();
    }

//    public Response findOne(Long itemId){
//        return Response.builder().data(itemRepository.findOne(itemId)).build();
//
//    }
}
