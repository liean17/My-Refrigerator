package com.toy.refrigerator.oauth;

import com.toy.refrigerator.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class Oauth2Attribute {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    @Builder
    public Oauth2Attribute(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static Oauth2Attribute of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes){
        if("kakao".equals(registrationId)){
            return ofKakao("id",attributes);
        }
        return ofGoogle("id", attributes);
    }

    private static Oauth2Attribute ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return Oauth2Attribute.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static Oauth2Attribute ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return Oauth2Attribute.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .nickname(name)
                .build();
    }
}
