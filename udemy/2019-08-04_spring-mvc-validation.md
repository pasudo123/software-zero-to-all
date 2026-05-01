
## Spring MVC Form Validation

* 스프링 4 이상부터 Bean 에 대한 유효성 검사 API  를 제공한다.
* 스프링 앱을 제작시, 선호되는 유효성 메소드이다.
* Bean Validation Features 는 아래와 같다.
    1. required
    2. valid length
    3. validate numbers
    4. validate with regular expressions
    5. custom validation
* Validation Annotation 의 종류는 아래와 같다.
    1. @NotNull
    2. @Min
    3. @Max
    4. @Size
    5. @Pattern
    6. @Future/@Past
    7. others...

Validation 어노테이션을 사용하기 위해선, https://hibernate.org/validator/ 에 접속하여 해당되는 라이브러리를 다운받는다. __요즘 기본 스프링부트에 내장되어 있는듯 하다.__   

__진행할 순서는 아래와 같다.__  
1. Customer Class 에 유효성 검사 삽입
2. Customer Controller 에 유효성 검사 실시(jsp 단 화면은 삽입하지 않는다.)

__Customer Class__
```java
public class Customer {

    private String firstName;

    @NotNull
    @Size(min = 1, message = "is required")
    private String lastName;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
```

__Customer Controller__
```
@RequestMapping("/showForm")
public String showForm(Model model){

    model.addAttribute("customer", new Customer());

    return "validation/customer-form";
}

@RequestMapping("/processForm")
public String processForm(
        @Valid @ModelAttribute("customer") Customer customer,
        BindingResult bindingResult) {

    System.out.println("|"+customer.getLastName()+"|");

    if (bindingResult.hasErrors()) {
        return "validation/customer-form";
    }

    return "validation/customer-confirmation";
}
```

여기서 한가지 볼 것은 bindingResult 인데 해당 객체는 유효성 검사에 대해서 에러를 잡아주는 객체이다. 해당 객체는 항상 모델 속성 뒤에 존재하여야 한다.   

* @Valid 어노테이션을 통해 해당 모델에 유효성 검사를 수행함을 알린다.
* 스프링에서 Validation 결과를 BindingResult 에 저장한다.
* [BindingResult 에 대한 설명](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-methods)


__@InitBinder__   
* 사전에 웹요청에 대해서 미리 처리하는 어노테이션
* 컨트롤러에 진입하기 전에 들어오는 요청들에 대해서 사전에 미리 처리한다. 따라서 그러한 메소드가 존재하는 경우, 해당 메소드를 붙인다.

```java
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){

        /** 공백으로 들어온 값에 대해서 null 로 치환한다. **/
        StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);

        webDataBinder.registerCustomEditor(String.class, trimmerEditor);
    }

```

위의 코드에서 만약에 문자열이 들어오는 내용이 있다면, 해당 문자열의 공백은 치환한다. __만약 공백만 들어온다면 null 값으로 치환한다.__
