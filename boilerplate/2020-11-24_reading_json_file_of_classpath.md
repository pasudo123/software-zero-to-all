## classpath 의 json 파일 읽기
* 테스트코드 작성 중에 dto 클래스를 인자로 가지는 객체를 테스트 하기위해서 애로사항이 있다.
* 바로 dto 클래스에는 일반적으로 생성자나 빌더패턴이 존재하지 않기 때문이다.
* 세터, 생성자, 빌더패턴 이런 것들이 없는데, `어떻게 하는 것이 좋은가?`
* 위의 경우를 해결하고자 json 파일을 읽어들어 ObjectMapper 로 `readValue(String string, Class<T> valueType)` 을 통하여 dto 클래스로 변환하는게 코드의 일관성을 유지시킬 수 있다.
* 테스트코드 만을 위해서 불필요하게 생성될 불필요한 생성자 혹은 세터를 막기위함이다.

<BR>

### json 경로를 읽어들이는 코드 샘플
```java
// 이렇게 경로가 존재한다면...
|
|- resources
|   |- json
|   |   |
|   |   |- storage-api-success.json
|   |
|


public class CustomJsonReader {

    // path 는 json/storage-api-success.json 로 될 수 있다.
    public String readJsonByClasspathFile(final String path) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);

        try {
            return readFromInputStream(inputStream);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return Strings.EMPTY;
        }
    }

    private String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }

        return resultStringBuilder.toString();
    }
}
```