package com.toy.refrigerator.food.controller;

import com.toy.refrigerator.food.dto.FoodDto;
import com.toy.refrigerator.food.entity.Food;
import com.toy.refrigerator.food.repository.FoodSearchCond;
import com.toy.refrigerator.food.service.FoodServiceImpl;
import com.toy.refrigerator.utils.multidto.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodServiceImpl foodService;

    @ModelAttribute("categories")
    public Food.Category[] categories() {
        return Arrays.stream(Food.Category.values()).toArray(Food.Category[]::new);
    }

    //음식 등록
    @GetMapping("/add/{sectorId}")
    public String getAddForm(@PathVariable Long sectorId,Model model){
        model.addAttribute("sectorId",sectorId);
        return "foods/addFood";
    }

    @PostMapping("/add/{sectorId}")
    public String addFood(FoodDto.Post postDto,@PathVariable Long sectorId, Model model){
        FoodDto.Response foodResponse = foodService.saveFood(postDto,sectorId);
        model.addAttribute("food",foodResponse);
        return "redirect:/foods/sector/"+sectorId;
    }

    //음식 조회
    @GetMapping("/sector/{foodId}")
    public String getFood(@PathVariable Long foodId,Model model){
        FoodDto.Response foodResponse = foodService.getFood(foodId);
        model.addAttribute("food",foodResponse);
        return "foods/food";
    }

    @GetMapping("/{sectorId}")
    public String getAllFood(@PathVariable Long sectorId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
                             Model model, FoodSearchCond cond) {
        PageRequest pageRequest = PageRequest.of(page-1, size);
        MultiResponseDto<FoodDto.Response> allFood = foodService.getAllFood(pageRequest, cond,sectorId);
        model.addAttribute("foods",allFood);
        return "foods/foods";
    }

    //음식 정보 수정
    @GetMapping("/edit/{foodId}")
    public String getEditFood(@PathVariable Long foodId,Model model){
        FoodDto.Response foodResponse = foodService.getFood(foodId);
        model.addAttribute("food",foodResponse);
        return "foods/editFood";
    }

    //Todo API
    @PostMapping("/edit/{foodId}")
    public String patchFood(@PathVariable Long foodId,FoodDto.Patch patchDto,Model model){
        FoodDto.Response foodResponse = foodService.editFood(foodId, patchDto);
        model.addAttribute("food",foodResponse);
        return "redirect:/foods/sector/"+foodId;
    }

    //음식 삭제
    @PostMapping("/{foodId}")
    public void deleteFood(@PathVariable Long foodId){
        foodService.deleteFood(foodId);
    }
}
