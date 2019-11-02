//package jpabook.memberteam.domain;
//
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by pasudo123 on 2019-09-02
// * Blog: https://pasudo123.tistory.com/
// * Email: oraedoa@gmail.com
// **/
//@Getter
//@Entity
//@Table(name = "team")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@ToString
//public class Team {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "name", length = 45)
//    private String name;
//
//    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
//    private List<Member> members = new ArrayList<>();
//
//    @Builder
//    public Team(String name){
//        this.name = name;
//    }
//
//}
