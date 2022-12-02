package com.toy.refrigerator.sector.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FoodInfoDto {
    private Integer imminentFoodCount;
    private Integer expiredFoodCount;

    public FoodInfoDto(Integer imminentFoodCount, Integer expiredFoodCount) {
        this.imminentFoodCount = imminentFoodCount;
        this.expiredFoodCount = expiredFoodCount;
    }
}
