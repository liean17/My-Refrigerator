package com.toy.refrigerator.food.repository;

import com.toy.refrigerator.food.entity.Food;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class FoodRepositoryTest {
    @Autowired
    private FoodRepository foodRepository;

    @Test
    void 등록(){
        //given
        Food kimchi = Food.builder()
                .name("김치")
                .description("신토불이")
                .category(Food.Category.SIDE_DISH)
                .foodStatus(Food.FoodStatus.FRESH)
                .build();
        //when
        Food savedFood = foodRepository.save(kimchi);
        //then
        assertThat(kimchi).isSameAs(savedFood);
        assertThat(kimchi.getRegistration()).isEqualTo(savedFood.getRegistration());
        assertThat(savedFood.getId()).isNotNull();
        assertThat(foodRepository.count()).isEqualTo(1);
    }

    @Test
    void 조회(){
        //given
        Food kimchi = Food.builder()
                .name("김치")
                .description("신토불이")
                .category(Food.Category.SIDE_DISH)
                .foodStatus(Food.FoodStatus.FRESH)
                .build();
        foodRepository.save(kimchi);

        //when
        Food findById = foodRepository.findById(2L).orElseThrow();


        //then
        assertThat(kimchi).isSameAs(findById);
    }
}