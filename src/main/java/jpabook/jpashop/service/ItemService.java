package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
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
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    // findItem == 영속 상태 -> jpa flush() 변경 감지 기능 사용
    // vs 병합 : 모든 속성이 변경된다 --> 값이 안들언 필드가 있으면 null 값으로 업데이트 쿼리 날림
    // 실무에서는 merge() 보다는 변경 감지 기능을 사용하는 것 권장
    @Transactional
    public void updateItem(Long itemId, Book param){
        Item findItem = itemRepository.findOne(itemId);
        //findItem.change(); -> set < 의미있는 메쏘드 생성 -> 변경 지점이 파악 가능 (Entity 위치로)
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());
    }



    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
