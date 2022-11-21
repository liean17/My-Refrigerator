package com.toy.refrigerator.sector.service;

import com.toy.refrigerator.sector.dto.SectorDto;
import com.toy.refrigerator.sector.entity.Sectors;
import com.toy.refrigerator.sector.repository.SectorRepository;
import com.toy.refrigerator.utils.multidto.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
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
        Sectors sector = new Sectors();
        System.out.println("sector = " + sector.getType());
        repository.save(sector);
    }

    public SectorDto.Response getSector(Long id){
        Sectors sectors = repository.findById(id).orElseThrow();
        return mappingToResponse(sectors);
    }

    public void editSector(Long sectorId,SectorDto.Patch patchDto) {
        Sectors sectors = repository.findById(sectorId).orElseThrow();
        Sectors.Type type = Sectors.Type.valueOf(patchDto.getType());
        sectors.editSector(patchDto.getName(),type);
    }

    public void deleteSector(Long sectorId) {
        repository.deleteById(sectorId);
    }

    public MultiResponseDto<SectorDto.Response> getAll() {
        List<Sectors> sectorsList = repository.findAll();
        List<SectorDto.Response> responseList = sectorsList.stream().map(this::mappingToResponse).collect(Collectors.toList());
        //Todo page 가 null 이면 오류남. 그렇다고 저래도 되는가
        return new MultiResponseDto<>(responseList,new PageImpl(responseList));
    }

    private SectorDto.Response mappingToResponse(Sectors sectors){
        SectorDto.Response response = SectorDto.Response.builder()
                .sectorId(sectors.getId())
                .name(sectors.getName())
                .type(sectors.getType())
                .build();
        return response;
    }
}
