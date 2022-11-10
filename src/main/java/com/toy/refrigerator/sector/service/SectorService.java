package com.toy.refrigerator.sector.service;

import com.toy.refrigerator.sector.dto.SectorDto;
import com.toy.refrigerator.sector.entity.Sectors;
import com.toy.refrigerator.sector.repository.SectorRepository;
import com.toy.refrigerator.utils.multidto.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class SectorService {

    private final SectorRepository repository;

    public void createSector() {
        repository.save(new Sectors());
    }

    public SectorDto.Response getSector(Long id){
        Sectors sectors = repository.findById(id).orElseThrow();
        return mappingToResponse(sectors);
    }

    public void editSector(Long sectorId,String name) {
        Sectors sectors = repository.findById(sectorId).orElseThrow();
        sectors.editName(name);
    }

    public void deleteSector(Long sectorId) {
        repository.deleteById(sectorId);
    }

    public MultiResponseDto<SectorDto.Response> getAll() {
        List<Sectors> sectorsList = repository.findAll();
        List<SectorDto.Response> responseList = sectorsList.stream().map(this::mappingToResponse).collect(Collectors.toList());
        return new MultiResponseDto<>(responseList,null);
    }

    private SectorDto.Response mappingToResponse(Sectors sectors){
        SectorDto.Response response = SectorDto.Response.builder()
                .sectorId(sectors.getId())
                .name(sectors.getName())
                .build();
        return response;
    }
}
