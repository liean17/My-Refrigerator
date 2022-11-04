package com.toy.refrigerator.food.service;

import com.toy.refrigerator.food.dto.FoodDto;
import com.toy.refrigerator.food.entity.Food;
import com.toy.refrigerator.food.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository repository;

    public FoodDto.Response saveFood(FoodDto.Post postDto) {
    }

    public FoodDto.Response getFood(Long foodId) {
    }

    public FoodDto.Response editFood(Long foodId, FoodDto.Patch patchDto) {
    }

    public void deleteFood(Long foodId) {
    }

    public FoodDto.Response findByName(String name) {
    }

    private FoodDto.Response mappingToResponse(Food food){

    }
}
