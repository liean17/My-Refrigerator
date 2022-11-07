package com.toy.refrigerator.food.service;

import com.toy.refrigerator.food.dto.FoodDto;
import com.toy.refrigerator.utils.multidto.MultiResponseDto;

public interface FoodService {

    FoodDto.Response saveFood(FoodDto.Post postDto);
    FoodDto.Response getFood(Long foodId);
    MultiResponseDto<FoodDto.Response> getAllFood();
    FoodDto.Response editFood(Long foodId, FoodDto.Patch patchDto);
    void deleteFood(Long foodId);
}
