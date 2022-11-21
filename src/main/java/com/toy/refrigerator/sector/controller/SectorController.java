package com.toy.refrigerator.sector.controller;

import com.toy.refrigerator.sector.dto.SectorDto;
import com.toy.refrigerator.sector.service.SectorService;
import com.toy.refrigerator.utils.multidto.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sectors")
@RequiredArgsConstructor
public class SectorController {

    private final SectorService sectorService;

    @GetMapping
    public String getSectors(Model model){
        MultiResponseDto<SectorDto.Response> all = sectorService.getAll();
        model.addAttribute("sectors",all.getData());
        return "sector/sectors";
    }

    @GetMapping("/add")
    public String addSector(){
        sectorService.createSector();
        return "redirect:/sectors";
    }

    @GetMapping("/edit/{sectorId}")
    public String getEditPage(@PathVariable Long sectorId,Model model){
        SectorDto.Response response = sectorService.getSector(sectorId);
        model.addAttribute("sector",response);
        return "sector/editSector";
    }

    @PostMapping("/edit/{sectorId}")
    public String editSector(@PathVariable Long sectorId,String name){
        sectorService.editSector(sectorId,name);
        return "redirect:/sectors";
    }

    @GetMapping("/delete/{sectorId}")
    public String deleteSector(@PathVariable Long sectorId){
        sectorService.deleteSector(sectorId);
        return "redirect:/sectors";
    }

}
