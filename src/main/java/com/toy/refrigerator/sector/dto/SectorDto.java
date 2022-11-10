package com.toy.refrigerator.sector.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SectorDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Patch{
        private String name;

        @Builder
        public Patch(String name) {
            this.name = name;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response{
        private Long sectorId;
        private String name;

        @Builder
        public Response(Long sectorId,String name) {
            this.sectorId = sectorId;
            this.name = name;
        }
    }
}
