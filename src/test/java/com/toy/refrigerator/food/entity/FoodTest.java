package com.toy.refrigerator.food.entity;

import com.toy.refrigerator.food.dto.FoodDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    //given
    Food kimchi = Food.builder()
            .id(1L)
            .name("김치")
            .description("신토불이")
            .category(Food.Category.SIDE_DISH)
            .foodStatus(Food.FoodStatus.FRESH)
            .build();


    @Test
    void 정보변경() {
        //when
        FoodDto.Patch patchDto = FoodDto.Patch.builder()
                .name("깍두기")
                .categoryCode(9)
                .description("깍두기")
                .expiration(LocalDateTime.of(2030,1,27,0,0).toString()).build();

        kimchi.changeStatus(Food.FoodStatus.CONSUMED);

        kimchi.update(patchDto,null);
        //then
        assertThat(kimchi.getFoodStatus()).isEqualTo(Food.FoodStatus.CONSUMED);
        assertThat(kimchi.getDescription()).isEqualTo("깍두기");
        assertThat(kimchi.getExpiration()).isAfter(LocalDateTime.now());
    }

    @Test
    void 카테고리(){
        int length = Food.Category.values().length;
        System.out.println(length);
    }

}