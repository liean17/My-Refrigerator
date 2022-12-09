package com.toy.refrigerator.sector.controller;

import com.toy.refrigerator.auth.principal.PrincipalDetails;
import com.toy.refrigerator.sector.dto.FoodInfoDto;
import com.toy.refrigerator.sector.dto.SectorDto;
import com.toy.refrigerator.sector.entity.Sectors;
import com.toy.refrigerator.sector.service.SectorService;
import com.toy.refrigerator.utils.multidto.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/sectors")
@RequiredArgsConstructor
public class SectorController {

    private final SectorService sectorService;

    @ModelAttribute("types")
    public Sectors.Type[] types() {
        return Arrays.stream(Sectors.Type.values()).toArray(Sectors.Type[]::new);
    }

    @GetMapping
    public String getSectors(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        if(principalDetails!=null){
            String nickname = principalDetails.getMember().getNickname();
            model.addAttribute("nickname",nickname);
        }
        MultiResponseDto<SectorDto.Response> all = sectorService.getAll(principalDetails);
        FoodInfoDto foodInfo = sectorService.getFoodInfo(principalDetails);
        model.addAttribute("sectors",all.getData());
        model.addAttribute("foodInfo",foodInfo);
        return "sector/sectors";
    }

    @GetMapping("/add")
    public String addSector(@AuthenticationPrincipal PrincipalDetails principalDetails){
        sectorService.createSector(principalDetails);
        return "redirect:/sectors";
    }

    @GetMapping("/edit/{sectorId}")
    public String getEditPage(@PathVariable Long sectorId,Model model){
        SectorDto.Response response = sectorService.getSector(sectorId);
        model.addAttribute("sector",response);
        return "sector/editSector";
    }

    @PostMapping("/edit/{sectorId}")
    public String editSector(@PathVariable Long sectorId,SectorDto.Patch patchDto){
        sectorService.editSector(sectorId,patchDto);
        return "redirect:/sectors";
    }

    @GetMapping("/delete/{sectorId}")
    public String deleteSector(@PathVariable Long sectorId){
        sectorService.deleteSector(sectorId);
        return "redirect:/sectors";
    }

}
