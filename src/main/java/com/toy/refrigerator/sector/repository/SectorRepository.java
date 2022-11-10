package com.toy.refrigerator.sector.repository;

import com.toy.refrigerator.sector.entity.Sectors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends JpaRepository<Sectors,Long> {
}
