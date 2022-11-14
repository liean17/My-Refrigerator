package com.toy.refrigerator.food.entity;

import com.toy.refrigerator.food.dto.FoodDto;
import com.toy.refrigerator.sector.entity.Sectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Food {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    private String description;
    private LocalDateTime registration = LocalDateTime.now();
    private LocalDateTime expiration;
    @Enumerated(value = EnumType.STRING)
    private Category category;
    @Enumerated(value = EnumType.STRING)
    private FoodStatus foodStatus = FoodStatus.FRESH;

    //private String imgUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id")
    private Sectors sectors;

    public enum FoodStatus{
        FRESH(1,"신선함"),
        NORMAL(2,"보통"),
        IMMINENT(3,"유통기한 임박"),
        EXPIRED(4,"유통기한 만료"),
        CONSUMED(5,"소비됨");

        private int statusCode;

        private String description;

        FoodStatus(int statusCode, String description) {
            this.statusCode = statusCode;
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }

    public enum Category{

        MEAT(1,"육류"),
        SEA_FOOD(2,"해산물"),
        FRUIT(3,"과일"),
        VEGETABLE(4,"채소"),
        DAIRY(5,"유제품"),
        BAKERY(6,"빵류"),
        LIQUOR(7,"주류"),
        BEVERAGES(8,"음료"),
        DISH(9,"요리"),
        SIDE_DISH(10,"반찬"),
        ETC(11,"기타");

        private int categoryCode;

        private String description;

        Category(int categoryCode, String description) {
            this.categoryCode = categoryCode;
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public int code(){
            return categoryCode;
        }
    }

    @Builder
    public Food(Long id, String name, String description, LocalDateTime expiration, Category category, FoodStatus foodStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.expiration = expiration;
        this.category = category;
    }

    public void update(FoodDto.Patch patchDto,Category category){
        this.name = patchDto.getName();
        this.description = patchDto.getDescription();
        this.expiration = patchDto.getExpiration();
        this.category = category;
    }

    public void setExpiration(LocalDateTime time){
        this.expiration = time;
    }


    public void changeStatus(FoodStatus status){
        this.foodStatus = status;
    }
}



