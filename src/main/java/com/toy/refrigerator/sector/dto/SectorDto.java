package com.toy.refrigerator.sector.dto;

import com.toy.refrigerator.sector.entity.Sectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SectorDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Patch{
        private String name;
        private String type;

        @Builder
        public Patch(String name, String type) {
            this.name = name;
            this.type = type;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response{
        private Long sectorId;
        private String name;
        private Sectors.Type type;

        @Builder
        public Response(Long sectorId,String name,Sectors.Type type) {
            this.sectorId = sectorId;
            this.name = name;
            this.type = type;
        }
    }
}
