package com.toy.refrigerator.food.repository;

import com.toy.refrigerator.food.entity.Food;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodSearchCond {

    private String foodName;
    private Food.FoodStatus status;
    private Food.Category category;

}
