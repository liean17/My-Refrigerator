package com.toy.refrigerator.food.service;

import com.toy.refrigerator.food.dto.FoodDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
}