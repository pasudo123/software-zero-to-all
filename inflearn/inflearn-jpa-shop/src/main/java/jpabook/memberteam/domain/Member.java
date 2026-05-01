//package jpabook.memberteam.domain;
//
//import lombok.*;
//
//import javax.persistence.*;
//
///**
// * Created by pasudo123 on 2019-09-02
// * Blog: https://pasudo123.tistory.com/
// * Email: oraedoa@gmail.com
// **/
//@Getter
//@Entity
//@Table(name = "member")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@ToString(exclude = "team")
//public class Member {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "name", length = 45, nullable = false)
//    private String name;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
//    private Team team;
//
//    @Builder
//    private Member(String name, Team team){
//        this.name = name;
//        this.team = team;
//    }
//}
