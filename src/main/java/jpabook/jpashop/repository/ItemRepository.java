package jpabook.jpashop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.QItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository{

    private final EntityManager em;

    public void save(Item item){
        em.persist(item);}

    public Item findByName(String name){
        return em.createQuery("select i from Item i where i.name=:name",Item.class)
                .setParameter("name",name).getSingleResult();
    }


    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    public List<Item> findAll(){
        JPAQuery<Item> query = new JPAQuery<>(em);
        QItem item = QItem.item;
        BooleanBuilder builder = new BooleanBuilder();
        return em.createQuery("select i from Item i",Item.class).getResultList();
    }
}
