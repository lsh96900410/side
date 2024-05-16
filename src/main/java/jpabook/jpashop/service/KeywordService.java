package jpabook.jpashop.service;

import jpabook.jpashop.domain.keyword.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;


    @Transactional
    public void createKeyword(List<String> names){




    }

    public void findKeyword(String name){

    }

}
