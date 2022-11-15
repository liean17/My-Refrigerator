package com.toy.refrigerator.food.service;

import com.toy.refrigerator.food.dto.FoodDto;
import com.toy.refrigerator.food.repository.FoodSearchCond;
import com.toy.refrigerator.utils.multidto.MultiResponseDto;
import org.springframework.data.domain.PageRequest;

public interface FoodService {

    FoodDto.Response saveFood(FoodDto.Post postDto,Long sectorId);
    FoodDto.Response getFood(Long foodId);
    MultiResponseDto<FoodDto.Response> getAllFood(PageRequest pageRequest, FoodSearchCond cond,Long sectorId);
    FoodDto.Response editFood(Long foodId, FoodDto.Patch patchDto);
    void deleteFood(Long foodId);
}
