package jpabook.jpql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

/**
 * Created by pasudo123 on 2019-10-13
 * Blog: https://pasudo123.tistory.com/
 * Email: oraedoa@gmail.com
 **/
@Embeddable
@Getter
@Setter
public class Address {

    private String city;
    private String street;
    private String zipcode;

}
