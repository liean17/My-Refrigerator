package com.toy.refrigerator.food.service;

import com.toy.refrigerator.food.dto.FoodDto;
import com.toy.refrigerator.food.entity.Food;
import com.toy.refrigerator.food.repository.FoodQueryRepository;
import com.toy.refrigerator.food.repository.FoodRepository;
import com.toy.refrigerator.food.repository.FoodSearchCond;
import com.toy.refrigerator.sector.entity.Sectors;
import com.toy.refrigerator.sector.repository.SectorRepository;
import com.toy.refrigerator.utils.multidto.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodServiceImpl implements FoodService{

    private final FoodRepository foodRepository;
    private final SectorRepository sectorRepository;
    private final FoodQueryRepository queryRepository;

    @Override
    public FoodDto.Response saveFood(FoodDto.Post postDto,Long sectorId) {

        Food food = dtoToFood(postDto,sectorId);

        Food.FoodStatus status = setFoodStatus(food.getRegistration(), food.getExpiration());
        food.changeStatus(status);

        //저장
        Food saved = foodRepository.save(food);

        return mappingToResponse(saved);
    }
    //Todo 예이예이예외처리예이예어어어컴온
    @Override
    public FoodDto.Response getFood(Long foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow();
        return mappingToResponse(food);
    }

    @Override
    public MultiResponseDto<FoodDto.Response> getAllFood(PageRequest pageRequest, FoodSearchCond cond,Long sectorId) {
        Page<Food> allWithCond = queryRepository.findAllWithCond(cond, pageRequest);

        List<FoodDto.Response> responseList = allWithCond.getContent().stream()
                .filter(food -> food.getSectors().getId()==sectorId)
                .map(this::mappingToResponse)
                .collect(Collectors.toList());

        return new MultiResponseDto<>(responseList,allWithCond);
    }

    @Override
    public FoodDto.Response editFood(Long foodId, FoodDto.Patch patchDto) {
        Food food = foodRepository.findById(foodId).orElseThrow();
        int categoryCode = patchDto.getCategoryCode();

        int length = Food.Category.values().length;

        Food.Category category = null;
        if(categoryCode <= length && categoryCode >0){
            category = getCategory(categoryCode);
        }

        food.update(patchDto,category);

        return mappingToResponse(food);
    }

    @Override
    public Long deleteFood(Long foodId) {
        Long sectorId = foodRepository.findById(foodId).orElseThrow().getSectors().getId();
        foodRepository.findById(foodId).orElseThrow()
                .changeStatus(Food.FoodStatus.CONSUMED);
        return sectorId;
    }

    public FoodDto.Response findByName(String name) {
        Food food = foodRepository.findByName(name);
        return mappingToResponse(food);
    }

    private Food dtoToFood(FoodDto.Post postDto, Long sectorId) {
        int categoryCode = postDto.getCategoryCode();

        int length = Food.Category.values().length;

        Food.Category category = null;
        if(categoryCode <= length && categoryCode >0){
            category = getCategory(categoryCode);
        }

        Sectors sectors = sectorRepository.findById(sectorId).orElseThrow();

        LocalDateTime time = LocalDateTime.of(9999,12,31,23,59);

        if(!postDto.getExpiration().equals("")){
            time = LocalDateTime.parse(postDto.getExpiration());
        }

        Food food = Food.builder()
                .name(postDto.getName())
                .description(postDto.getDescription())
                .expiration(time)
                .category(category)
                .sectors(sectors)
                .build();
        return food;
    }

    private FoodDto.Response mappingToResponse(Food food){
        FoodDto.Response response = FoodDto.Response.builder()
                .foodId(food.getId())
                .name(food.getName())
                .description(food.getDescription())
                .registration(food.getRegistration())
                .expiration(food.getExpiration())
                .category(food.getCategory())
                .status(food.getFoodStatus())
                .sectorId(food.getSectors().getId())
                .build();
        return response;
    }
    //TODO 효율적인 상태 계산식 필요
    private Food.FoodStatus setFoodStatus(LocalDateTime registration, LocalDateTime expiration) {
        LocalDateTime now = LocalDateTime.now();

        if(expiration.isBefore(now)){
            return Food.FoodStatus.EXPIRED;
        }

        Duration registryMinusExpire = Duration.between(registration, expiration);
        long days = registryMinusExpire.toDays();
        Duration nowToExpire = Duration.between(now, expiration);
        //등록일과 유통기한의 차이가 긴, 덜 민감한 식품
        if(days>=7L){
            if(nowToExpire.toDays()<7L) return Food.FoodStatus.IMMINENT;
        }
        //등록일과 유통기한의 차이가 짧은 민감한 식품
        else if(nowToExpire.toDays()<3L) return Food.FoodStatus.IMMINENT;
        return Food.FoodStatus.NORMAL;
    }

    private Food.Category getCategory(int categoryCode) {
        return Arrays.stream(Food.Category.values())
                .filter(c->c.code()==categoryCode).findAny().orElse(null);
    }
}
