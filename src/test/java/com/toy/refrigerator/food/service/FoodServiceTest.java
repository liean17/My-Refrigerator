package com.toy.refrigerator.food.service;

import com.toy.refrigerator.food.dto.FoodDto;
import com.toy.refrigerator.food.entity.Food;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class FoodServiceTest {
    @Autowired
    private FoodServiceImpl foodService;

    FoodDto.Post postDto = FoodDto.Post.builder()
            .name("김치")
            .description("신토불이")
            .expiration(LocalDateTime.of(2025,11,4,21,05))
            .categoryCode(10).build();

    @Test
    void 음식등록및조회(){

        //when
        FoodDto.Response response = foodService.saveFood(postDto);
        FoodDto.Response findByName = foodService.findByName(response.getName());

        //then
        assertThat(response.getName()).isEqualTo(postDto.getName());
        assertThat(findByName.getName()).isEqualTo(postDto.getName());

    }

    @Test
    void 음식정보수정(){
        //given
        FoodDto.Patch patchDto = FoodDto.Patch.builder()
                .name("코울슬로")
                .description("사실한국음식이었던거임!!")
                .expiration(LocalDateTime.of(2030,11,11,11,11))
                .categoryCode(9)
                .build();

        //when
        foodService.saveFood(postDto);
        FoodDto.Response editFood = foodService.editFood(1L, patchDto);

        FoodDto.Response findFood = foodService.getFood(1L);

        //then
        assertThat(editFood.getName()).isEqualTo(findFood.getName());
        assertThat(editFood.getCategory()).isEqualTo(Food.Category.DISH);

    }

    @Test
    void 삭제테스트(){
        //when
        foodService.saveFood(postDto);
        foodService.deleteFood(1L);

        FoodDto.Response findFood = foodService.getFood(1L);
        //then
        assertThat(findFood.getStatus()).isEqualTo(Food.FoodStatus.CONSUMED);

    }
}