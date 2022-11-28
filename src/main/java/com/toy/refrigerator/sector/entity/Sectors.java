package com.toy.refrigerator.sector.entity;

import com.toy.refrigerator.food.entity.Food;
import com.toy.refrigerator.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Sectors {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name = "새로운 칸";
    private Type type = Type.FRIDGE;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "sectors",cascade = CascadeType.ALL)
    private List<Food> foodList = new ArrayList<>();
    private Status status = Status.ACTIVATE;

    @Builder
    public Sectors(Long id, String name,Member member, List<Food> foodList) {
        this.id = id;
        this.name = name;
        this.member = member;
        this.foodList = foodList;
    }

    public void editSector(String name,Type type){
        this.name = name;
        this.type = type;
    }

    public enum Type{
        FRIDGE("냉장실"),
        FREEZER("냉동실"),
        KIMCHI("김치냉장고"),
        ETC("기타");

        private String description;

        Type(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    @Getter
    public enum Status{
        ACTIVATE("활성"),
        INACTIVE("비활성");

        private String description;

        Status(String description) {
            this.description = description;
        }

    }

    public void setType(Type type) {
        this.type = type;
    }
    public void setMember(Member member){this.member = member;}

    public void setStatus(Status status) {
        this.status = status;
    }
}
