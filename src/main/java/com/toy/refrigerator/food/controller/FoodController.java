package com.toy.refrigerator.food.controller;

import com.toy.refrigerator.food.dto.FoodDto;
import com.toy.refrigerator.food.repository.FoodSearchCond;
import com.toy.refrigerator.food.service.FoodServiceImpl;
import com.toy.refrigerator.utils.multidto.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodServiceImpl foodService;

    //음식 등록
    @PostMapping("/add")
    public String addFood(@RequestBody FoodDto.Post postDto, Model model){
        FoodDto.Response foodResponse = foodService.saveFood(postDto);
        model.addAttribute("food",foodResponse);
        return "foods/food";
    }

    //음식 조회
    @GetMapping("/{foodId}")
    public String getFood(@PathVariable Long foodId,Model model){
        FoodDto.Response foodResponse = foodService.getFood(foodId);
        model.addAttribute("food",foodResponse);
        return "foods/food";
    }

    @GetMapping
    public String getAllFood(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
            ,Model model, FoodSearchCond cond) {
        PageRequest pageRequest = PageRequest.of(page, size);
        MultiResponseDto<FoodDto.Response> allFood = foodService.getAllFood(pageRequest, cond);
        model.addAttribute("foods",allFood);
        return "foods/foods";
    }

    //음식 정보 수정
    @GetMapping("/edit/{foodId}")
    public String getEditFood(@PathVariable Long foodId,Model model){
        FoodDto.Response foodResponse = foodService.getFood(foodId);
        model.addAttribute("food",foodResponse);
        return "foods/editForm";
    }

    //Todo API
    @PostMapping("/edit/{foodId}")
    public String patchFood(@PathVariable Long foodId,@RequestBody FoodDto.Patch patchDto,Model model){
        FoodDto.Response foodResponse = foodService.editFood(foodId, patchDto);
        model.addAttribute("food",foodResponse);
        return "foods/food";
    }

    //음식 삭제
    @PostMapping("/{foodId}")
    public void deleteFood(@PathVariable Long foodId){
        foodService.deleteFood(foodId);
    }
}
