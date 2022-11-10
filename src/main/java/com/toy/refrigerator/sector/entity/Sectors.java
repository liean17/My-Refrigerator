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
    //private Member member;
    @OneToMany(mappedBy = "sectors",cascade = CascadeType.ALL)
    private List<Food> foodList = new ArrayList<>();

    @Builder
    public Sectors(Long id, String name, List<Food> foodList) {
        this.id = id;
        this.name = name;
        this.foodList = foodList;
    }

    public void editName(String name){
        this.name = name;
    }
}
