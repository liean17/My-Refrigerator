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

        //유통기한을 지정했다면
        if (postDto.getExpiration()!=null){
            LocalDateTime expirations = LocalDateTime.parse(postDto.getExpiration());
            food.setExpiration(expirations);

            Food.FoodStatus status = setFoodStatus(food.getRegistration(), food.getExpiration());
            food.changeStatus(status);
        }

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
                .filter(f->f.getSectors().getId()==sectorId)
                .map(this::mappingToResponse)
                .collect(Collectors.toList());

        return new MultiResponseDto<>(responseList,allWithCond);
    }

    @Override
    public FoodDto.Response editFood(Long foodId, FoodDto.Patch patchDto) {
        Food food = foodRepository.findById(foodId).orElseThrow();
        Food.Category category = getCategory(patchDto.getCategoryCode());
        food.update(patchDto,category);

        return mappingToResponse(food);
    }

    @Override
    public void deleteFood(Long foodId) {
        foodRepository.findById(foodId).orElseThrow()
                .changeStatus(Food.FoodStatus.CONSUMED);
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

        Food food = Food.builder()
                .name(postDto.getName())
                .description(postDto.getDescription())
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
                .build();
        return response;
    }

    private Food.FoodStatus setFoodStatus(LocalDateTime registration, LocalDateTime expiration) {
        //TODO 유통기한과 등록일을 퍼센트로 계산하면 좋을 것 같다.
        if(expiration.isBefore(registration)){
            return Food.FoodStatus.EXPIRED;
        }
        return Food.FoodStatus.NORMAL;
    }

    private Food.Category getCategory(int categoryCode) {
        return Arrays.stream(Food.Category.values())
                .filter(c->c.code()==categoryCode).findAny().orElse(null);
    }
}
