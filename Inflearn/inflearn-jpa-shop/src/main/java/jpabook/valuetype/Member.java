//package jpabook.valuetype;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * Created by pasudo123 on 2019-09-29
// * Blog: https://pasudo123.tistory.com/
// * Email: oraedoa@gmail.com
// **/
//@Entity
//@Setter
//@Getter
//public class Member {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "member_id")
//    private Long id;
//
//    @Column(name = "username")
//    private String username;
//
//    @Embedded
//    private Address address;
//
//    @ElementCollection
//    @CollectionTable(
//            name = "favorite_food",
//            joinColumns = {@JoinColumn(name = "member_id")}
//    )
//    @Column(name = "food_name")
//    private Set<String> favoriteFoods = new HashSet<>();
//
////    @ElementCollection
////    @CollectionTable(
////            name = "address",
////            joinColumns = {@JoinColumn(name = "member_id")}
////    )
////    private List<Address> addressHistory = new ArrayList<>();
//
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "member_id")
//    private List<AddressEntity> addressHistory = new ArrayList<>();
//}
