package com.toy.refrigerator.sector.service;

import com.toy.refrigerator.sector.entity.Sectors;
import com.toy.refrigerator.sector.repository.SectorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
class SectorServiceTest {

    @Autowired
    private SectorService sectorService;
    @Autowired
    private SectorRepository repository;

    @Test
    void 섹터저장(){
        //given
        sectorService.createSector();

        //when
        int size = repository.findAll().size();

        //then
        Assertions.assertThat(size).isGreaterThan(0);

    }
    @Test
    void 섹터수정(){

        //when
        sectorService.createSector();
        sectorService.editSector(1L,"수정될이름");
        Sectors findSector = repository.findById(1L).orElseThrow();
        //then
        Assertions.assertThat(findSector.getName()).isEqualTo("수정될이름");

    }
    @Test
    void 섹터삭제(){
        //when
        sectorService.createSector();
        sectorService.deleteSector(1L);
        int size = repository.findAll().size();
        //then
        Assertions.assertThat(size).isEqualTo(0);

    }

}