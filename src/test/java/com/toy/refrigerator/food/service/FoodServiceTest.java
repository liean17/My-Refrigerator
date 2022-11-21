package com.toy.refrigerator.food.service;

import com.toy.refrigerator.food.dto.FoodDto;
import com.toy.refrigerator.food.entity.Food;
import com.toy.refrigerator.sector.entity.Sectors;
import com.toy.refrigerator.sector.repository.SectorRepository;
import com.toy.refrigerator.sector.service.SectorService;
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
    @Autowired
    private SectorService sectorService;

    FoodDto.Post postDto = FoodDto.Post.builder()
            .name("김치")
            .description("신토불이")
            .expiration(LocalDateTime.of(2025,11,4,21,05).toString())
            .categoryCode(10).build();

    @Test
    void 음식등록및조회(){
        sectorService.createSector();

        //when
        FoodDto.Response response = foodService.saveFood(postDto,1L);
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
                .expiration(LocalDateTime.of(2030,11,11,11,11).toString())
                .categoryCode(9)
                .build();

        //when
        sectorService.createSector();
        foodService.saveFood(postDto,1L);
        FoodDto.Response editFood = foodService.editFood(1L, patchDto);

        FoodDto.Response findFood = foodService.getFood(1L);

        //then
        assertThat(editFood.getName()).isEqualTo(findFood.getName());
        assertThat(editFood.getCategory()).isEqualTo(Food.Category.DISH);

    }

    @Test
    void 삭제테스트(){
        //when
        sectorService.createSector();
        foodService.saveFood(postDto,1L);
        foodService.deleteFood(1L);

        FoodDto.Response findFood = foodService.getFood(1L);
        //then
        assertThat(findFood.getStatus()).isEqualTo(Food.FoodStatus.CONSUMED);

    }
}