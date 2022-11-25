package com.toy.refrigerator.food.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.refrigerator.food.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
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

    public Page<Food> findAllWithCond(FoodSearchCond cond, PageRequest pageRequest){
        List<Food> content = getFindAllQuery(cond,pageRequest);
        JPAQuery<Long> countQuery = getCountQuery(cond);
        return PageableExecutionUtils.getPage(content,pageRequest,countQuery::fetchOne);
    }

    public List<Food> getFindAllQuery(FoodSearchCond cond,PageRequest pageRequest){
        return query
                .select(food)
                .from(food)
                .where(
                        likeFoodName(cond.getFoodName()),
                        likeStatus(cond.getStatus()),
                        likeCategory(cond.getCategory()),
                        food.foodStatus.ne(Food.FoodStatus.CONSUMED))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();
    }

    public JPAQuery<Long> getCountQuery(FoodSearchCond cond){
        return query.select(food.count())
                .from(food)
                .where(
                        likeFoodName(cond.getFoodName()),
                        food.foodStatus.ne(Food.FoodStatus.CONSUMED)
                );
    }

    private BooleanExpression likeFoodName(String foodName){
        if(StringUtils.hasText(foodName)){
            return food.name.like("%"+foodName+"%");
        }
        return null;
    }

    private BooleanExpression likeStatus(Food.FoodStatus status){
        if(status!=null){
            return food.foodStatus.eq(status);
        }
        return null;
    }

    private BooleanExpression likeCategory(Food.Category category){
        if(category!=null){
            return food.category.eq(category);
        }
        return null;
    }
}
