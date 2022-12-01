package com.toy.refrigerator.config.batch;

import com.toy.refrigerator.food.entity.Food;
import com.toy.refrigerator.food.repository.FoodRepository;
import com.toy.refrigerator.food.service.FoodServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@RequiredArgsConstructor
@EnableBatchProcessing
@Configuration
public class BatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final FoodServiceImpl foodService;
    private final FoodRepository foodRepository;

    @Bean
    public Job checkFood(){
        return jobBuilderFactory.get("checkFood")
                .start(checkFoodStatus())
                .build();
    }

    @Bean
    public Step checkFoodStatus(){
        return stepBuilderFactory.get("checkFoodStatus")
                .tasklet(((contribution, chunkContext) -> {
                    List<Food> foodList = foodRepository.findAll();
                    if(foodList.size()>0){
                        for (Food food : foodList) {
                            if (!food.getFoodStatus().equals(Food.FoodStatus.EXPIRED)) {
                                Food.FoodStatus status = foodService.setFoodStatus(food.getRegistration(), food.getExpiration());
                                food.changeStatus(status);
                            }
                        }
                    }
                    return RepeatStatus.FINISHED;
                })).build();
    }
}
