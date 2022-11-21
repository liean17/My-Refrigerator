package com.toy.refrigerator.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Post{
        private String email;
        private String password;
        private String nickname;

        @Builder
        public Post(String email, String password, String nickname) {
            this.email = email;
            this.password = password;
            this.nickname = nickname;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Patch{
        private String password;
        private String nickname;

        @Builder
        public Patch(String password, String nickname) {
            this.password = password;
            this.nickname = nickname;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response{
        private Long memberId;
        private String email;
        private String password;
        private String nickname;

        @Builder
        public Response(Long memberId, String email, String password, String nickname) {
            this.memberId = memberId;
            this.email = email;
            this.password = password;
            this.nickname = nickname;
        }
    }
}