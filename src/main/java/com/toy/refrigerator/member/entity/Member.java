package com.toy.refrigerator.member.entity;

import com.toy.refrigerator.sector.entity.Sectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private Status status = Status.ACTIVE;
    private Role role = Role.ROLE_MEMBER;
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Sectors> sectors = new ArrayList<>();

    @Builder
    public Member(Long id, String email, String password, String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void deactivate(){
        this.status = Status.INACTIVE;
    }

    public void sleep(){
        this.status = Status.SLEEP;
    }

    public enum Status{
        ACTIVE("활동 중인 계정"),
        INACTIVE("비활성상태"),
        SLEEP("휴면 계정");

        private String description;

        Status(String description) {
            this.description = description;
        }
    }

    public enum Role{
        ROLE_ADMIN("관리자"), ROLE_MEMBER("일반사용자");

        private String description;

        Role(String description) {
            this.description = description;
        }
    }

    public Role getRole() {
        return role;
    }
}
