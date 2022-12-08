package com.toy.refrigerator.food.repository;

import com.toy.refrigerator.food.entity.Food;
import com.toy.refrigerator.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    Food findByName(String name);
    List<Food> findAllByFoodStatus(Food.FoodStatus foodStatus);
    List<Food> findAllBySectors_Member(Member member);
}
