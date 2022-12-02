package com.toy.refrigerator.sector.service;

import com.toy.refrigerator.auth.principal.PrincipalDetails;
import com.toy.refrigerator.exception.BusinessLogicException;
import com.toy.refrigerator.exception.ExceptionCode;
import com.toy.refrigerator.food.entity.Food;
import com.toy.refrigerator.food.repository.FoodRepository;
import com.toy.refrigerator.member.entity.Member;
import com.toy.refrigerator.sector.dto.FoodInfoDto;
import com.toy.refrigerator.sector.dto.SectorDto;
import com.toy.refrigerator.sector.entity.Sectors;
import com.toy.refrigerator.sector.repository.SectorRepository;
import com.toy.refrigerator.utils.multidto.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class SectorService {

    private final SectorRepository sectorRepository;
    private final FoodRepository foodRepository;

    public void createSector(PrincipalDetails principalDetails) {
        //TODO 예외처리
        if(sectorRepository.findAll().stream()
                .filter(s->s.getStatus().equals(Sectors.Status.ACTIVATE))
                .collect(Collectors.toList()).size()>10) {
            log.error("칸 수 초과");
            return;
        }
        Sectors sector = new Sectors();
        Member member = principalDetails.getMember();

        sector.setMember(member);
        sectorRepository.save(sector);
    }

    public SectorDto.Response getSector(Long id){
        Sectors sectors = getSingleSector(id);
        return mappingToResponse(sectors);
    }

    public void editSector(Long sectorId,SectorDto.Patch patchDto) {
        Sectors sectors = sectorRepository.findById(sectorId).orElseThrow();
        Sectors.Type type = Sectors.Type.valueOf(patchDto.getType());
        sectors.editSector(patchDto.getName(),type);
    }

    public void deleteSector(Long sectorId) {
        Sectors singleSector = getSingleSector(sectorId);
        singleSector.setStatus(Sectors.Status.INACTIVE);
        //repository.deleteById(sectorId);
    }

    public MultiResponseDto<SectorDto.Response> getAll(PrincipalDetails principalDetails) {
        List<Sectors> sectorsList = sectorRepository.findAllByMember(principalDetails.getMember());
        List<SectorDto.Response> responseList = sectorsList.stream().filter(s->!s.getStatus().equals(Sectors.Status.INACTIVE)).map(this::mappingToResponse).collect(Collectors.toList());
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

    private Sectors getSingleSector(Long id) {
        Sectors sectors = sectorRepository.findById(id)
                .orElseThrow(()->new BusinessLogicException(ExceptionCode.SECTOR_NOT_FOUND));
        return sectors;
    }

    public FoodInfoDto getFoodInfo() {
        //TODO 조회 효율
        int imminentCount = foodRepository.findAll()
                .stream()
                .filter(f->f.getFoodStatus().equals(Food.FoodStatus.IMMINENT))
                .collect(Collectors.toList()).size();
        int expiredCount = foodRepository.findAll()
                .stream()
                .filter(f->f.getFoodStatus().equals(Food.FoodStatus.EXPIRED))
                .collect(Collectors.toList()).size();
        return new FoodInfoDto(imminentCount,expiredCount);
    }
}
