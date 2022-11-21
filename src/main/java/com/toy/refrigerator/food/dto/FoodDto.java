package com.toy.refrigerator.food.dto;

import com.toy.refrigerator.food.entity.Food;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class FoodDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Post{
        @NotBlank(message = "이름을 입력해주세요")
        private String name;
        private String description;
        @DateTimeFormat(pattern = "yyyy-MM-dd 'T' HH:mm:ss")
        private String expiration;
        @NotNull(message = "카테고리를 지정해주세요")
        private int categoryCode;

        @Builder
        public Post(String name, String description, String expiration, int categoryCode) {
            this.name = name;
            this.description = description;
            this.expiration = expiration;
            this.categoryCode = categoryCode;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Patch{
        @NotBlank(message = "이름을 입력해주세요")
        private String name;
        private String description;
        private String expiration;
        @NotNull(message = "카테고리를 지정해주세요")
        private int categoryCode;

        @Builder
        public Patch(String name, String description, String expiration, int categoryCode) {
            this.name = name;
            this.description = description;
            this.expiration = expiration;
            this.categoryCode = categoryCode;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response{
        private Long foodId;
        private String name;
        private String description;
        private LocalDateTime registration;
        private LocalDateTime expiration;
        private Food.Category category;
        private Food.FoodStatus status;
        private Long sectorId;

        @Builder
        public Response(Long foodId, String name, String description, LocalDateTime registration, LocalDateTime expiration, Food.Category category, Food.FoodStatus status,Long sectorId) {
            this.foodId = foodId;
            this.name = name;
            this.description = description;
            this.registration = registration;
            this.expiration = expiration;
            this.category = category;
            this.status = status;
            this.sectorId = sectorId;
        }
    }
}
