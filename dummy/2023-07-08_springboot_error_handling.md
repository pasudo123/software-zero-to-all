## springboot 에서 에러핸들링하기
* 스프링부트에서 에러로 핸들링할 필요가 있는 경우가 있다.
* 단순히 throw RuntimeException(message) 형태로 날아가는게 아닌 에러응답을 디테일하게 그리고 전역으로 처리하는 방법을 기술한다.

### 1. 에러코드 정의 및 악셉션, 에러클래스  작성
* 클라이언트와 약속된 에러코드가 필요할 수 있다.
* 클라이언트는 앱클라 혹은 웹클라가 될 수 있을텐데, 특정 코드값에 맞는 화면을 띄우거나 클라단에서도 별도 처리가 필요할 수 있기 때문이다.

#### 에러코드 정의
* enum class 로 작성하여 구조화한다.
```kotlin
enum class ErrorCode(val code: Long, val desc: String) {
    A_EXCEPTION(30000, "A 익셉션 발생"),
    B_EXCEPTION(31000, "B 익셉션 발생"),
    C_EXCEPTION(32000, "C 익셉션 발생"),
    D_EXCEPTION(33000, "D 익셉션 발생"),
}
```

#### 익셉션 정의
* RuntimeException 을 상속하는 상위 CustomException 클래스를 만들다.
* 그리고 CustomException 을 상속받는 각각의 형식을 가진 익셉션 클래스를 만든다.
```kotlin
// sealed 로 선언하여 타 패키지에서는 해당 클래스를 상속할 수 없도록 구조를 잡는다.
sealed class CustomException(
    errorDetail: ErrorDetail
): RuntimeException(errorDetail.message)

/**
 * A 에 관련한 익셉션
 */
class AException(
    val errorDetail: ErrorDetail
): CustomException(errorDetail)

class BException(
    private val errorDetail: ErrorDetail
): CustomException(errorDetail)

class CException(
    private val errorDetail: ErrorDetail
): CustomException(errorDetail)
```

#### 에러클래스 정의
* 클라에게 건네줄 에러클래스를 작성한다. 경우에 따라서 @JsonIgnore 를 붙여 내보내지 않아도 될 필드를 정의한다.
```kotlin
data class ErrorDetail(
    val errorCode: Long,
    val message: String,
    @JsonIgnore
    val httpStatus: HttpStatus,
    val cause: Map<String, Any> = emptyMap()
) {

    var requestUri: String? = null
    var requestMethod: String? = null
}
```

### 2. 전역으로 에러핸들링 정의
* RestControllerAdvice 를 이용한다. (ControlllerAdvice + ResponseBody)
* 해당 애노테이션을 이용하여 exception 이 throw 될 때, 애플리케이션 레벨에서 `일관성` 있는 에러 json 이 나가도록 할 수 있다.
```kotlin
@RestControllerAdvice
class GlobalControllerAdvice {

    @ExceptionHandler(value = [AException::class])
    fun handleAException(
        aException: AException, request: WebRequest
    ): ResponseEntity<ErrorDetail> {
        val errorDetail = aException.errorDetail.apply {
            this.requestUri = request.toRequestUri()
            this.requestMethod = request.toRequestMethod()
        }


        return ResponseEntity
            .status(aException.errorDetail.httpStatus)
            .body(errorDetail)
    }

    // 클라가 어느 method + uri 로 보내서 익셉션이 났는지 여부도 체크할 수 있도록 에러클래스에 첨부한다.
    private fun WebRequest.toRequestUri(): String = (this as ServletWebRequest).request.requestURI.toString()
    private fun WebRequest.toRequestMethod(): String = (this as ServletWebRequest).request.method
}
```

### 3. 결과확인
* 에러핸들링 정의가 잘 되었다면 http status 는 에러에 맞게 나가고, 더불어서 json 형태로 응답이 나가는 것을 확인할 수 있다.
```json
// 아래 내용은 Http status 400 으로 나간다.
{
    "errorCode": 30000,
    "message": "A 익셉션을 강제로 에러를 발생시킵니다.",
    "cause": {
        "sample-key1": "sample-value1",
        "sample-key2": "sample-value2"
    },
    "requestUri": "/force-error/a",
    "requestMethod": "GET"
}
```
