package com.toy.refrigerator.sector.entity;

import com.toy.refrigerator.food.entity.Food;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Sectors {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name = "새로운 칸";
    private Type type = Type.FRIDGE;
    //private Member member;
    @OneToMany(mappedBy = "sectors",cascade = CascadeType.ALL)
    private List<Food> foodList = new ArrayList<>();

    @Builder
    public Sectors(Long id, String name, List<Food> foodList) {
        this.id = id;
        this.name = name;
        this.foodList = foodList;
    }

    public void editSector(String name,Type type){
        this.name = name;
        this.type = type;
    }

    public enum Type{
        FRIDGE("냉장실"),
        FREEZER("냉동실"),
        KIMCHI("김치냉장고"),
        ETC("기타");

        private String description;

        Type(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public void setType(Type type) {
        this.type = type;
    }
}
