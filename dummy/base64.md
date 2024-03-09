## base64 (with base32)
- 바이너리 데이터를 텍스트로 변환하는 인코딩 기법이다. 
- 이 때 인코딩에 이용되는 문자열은 정해진 64개의 문자열을 가지고 인코딩을 수행한다. 
- source 의 바이너리 데이터를 6비트씩 잘라서 64개의 문자열에 매핑시켜서 인코딩을 수행한다. 
- 하지만 바이너리 데이터가 큰 경우에 위키피셜, 33~37% 정도의 데이터 오버헤드가 발생한다고 한다.

## base64 데이터 오버헤드가 발생하는 두가지 이유
1. base64 인코딩은 6비트씩 잘라서 처리가 된다. 하지만 바이너리 데이터는 8비트씩 잘라서 처리되기 때문에 base64 인코딩 시에는 기존 바이너리 데이터 대비 더 많은 문자가 사용되기 때문이다.
2. base64 인코딩 데이터는 각 라인 끝에 줄바꿈 문자가 추가될 수 있다. 6bit 를 명확하게 끝내기 위해서 '=' 문자가 추가된다.

## base64 코드작성 (with kotlin)
```kotlin
import java.util.Base64

fun main() {
	// 원본 문자열
	val origin = "Many hands make light work."
	
	// 원본 문자열을 바이트배열로 변환
	val originByteArray = origin.toByteArray()
	
	// 바이트배열을 아스키 코드 매핑 번호로 변환
	val asciiNumbers = originByteArray.map { byte -> byte.toUInt()}
	
	// 아스키 코드를 바이너리 데이터로 변환
	val binaryArray = asciiNumbers.map { it.toString(2) }
	
	// 바이너리 데이터를 6비트씩 끊어서 처리
	// 6비트씩 끊어진 데이터를 base64 테이블과 매핑처리
	
	// 인코딩된 결과값
	// encode=TWFueSBoYW5kcyBtYWtlIGxpZ2h0IHdvcmsu
	val encodeString = Base64.getEncoder().encodeToString(originByteArray)
	
	// 디코딩된 결과값 : 인코딩 반대로 수행
	// decode=Many hands make light work.
	val decodeString = Base64.getDecoder().decode(encodeString).decodeToString()
}
```

그리고 base64 인코딩 수행 시 `=`, 패딩을 붙이는 이유도 있다.
패딩을 붙이는 이유는 원본문자열의 8bit 와 base64 6bit 의 최소공배수를 맞추기 위해서다.
그래서 무의미한 값이 인코딩시에 “=” 로 들어간다. 따라서 24비트를 한 묶음으로 처리하는데, 그 값에 공백은 패딩이 들어간다고 보면 된다.

> base32 인코딩은 base64 인코딩 기법과 동일하다.
> base64가 6비트 그룹이라면, base32 는 5비트씩 잘라서 32개 문자열에 매핑한 걸 인코딩한 값이다

## 참고
- https://ko.wikipedia.org/wiki/ASCII
- https://datatracker.ietf.org/doc/html/rfc4648#autoid-9
- https://base64decode.tech/playground