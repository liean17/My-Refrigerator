package com.toy.refrigerator.food.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.refrigerator.food.entity.Food;
import com.toy.refrigerator.food.entity.QFood;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.toy.refrigerator.food.entity.QFood.*;

@Repository
public class FoodQueryRepository {

    private final JPAQueryFactory query;

    public FoodQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<Food> findAll(FoodSearchCond cond){
        return query.select(food)
                .from(food)
                .where(
                        likeFoodName(cond.getName())
                )
                .fetch();
    }

    private BooleanExpression likeFoodName(String foodName){
        if(StringUtils.hasText(foodName)){
            return food.name.like("%"+foodName+"%");
        }
        return null;
    }
}
