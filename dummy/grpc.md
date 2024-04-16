## gRPC
- 구글이 개발한 google Remote Procedure Call 시스템
- 클라이언트와 서버간의 통신을 위해 사용되며, MSA 아키텍처에서 유용하게 사용
- gRPC 는 Protobuf 를 사용하여 메시지를 정의 및 직렬화하는 방법을 제공
  - 즉 gRPC 인터페이스와 메시지 페이로드는 Protobuf 를 사용하여 정의
- gPRC 는 Protobuf 를 이용한 통신방법 중에 하나.

## Protobuf
- 구글에서 개발한 데이터를 직렬화한 형식
- 구조화된 데이터를 바이너리 형식으로 컴팩트하게 저장하여 네트워크 연결을 빠르게 처리

## 참고자료
* [gRPC 소개](https://grpc.io/docs/what-is-grpc/introduction/)
* [Protobuf 란](https://appmaster.io/ko/blog/peurotobeopeuran-mueosibnigga)
* [ms : gRPC vs HTTP API](https://learn.microsoft.com/ko-kr/aspnet/core/grpc/comparison?view=aspnetcore-8.0)
* [ms : gRPC 관련 성능 모범 사례](https://learn.microsoft.com/ko-kr/aspnet/core/grpc/performance?view=aspnetcore-8.0)
* [제대로 이해하는 gRPC](https://youtu.be/VBtwIkE-W14?si=7EiAvjTMKWU888FO)
* [당근마켓 gRPC 서비스 운영 노하우](https://youtu.be/igHrQPzLVRw?si=0hhF_tx66B5kZI9J)
* [[네이버클라우드 기술&경험] 시대의 흐름, gRPC 깊게 파고들기 #1](https://medium.com/naver-cloud-platform/nbp-%EA%B8%B0%EC%88%A0-%EA%B2%BD%ED%97%98-%EC%8B%9C%EB%8C%80%EC%9D%98-%ED%9D%90%EB%A6%84-grpc-%EA%B9%8A%EA%B2%8C-%ED%8C%8C%EA%B3%A0%EB%93%A4%EA%B8%B0-1-39e97cb3460)
* [[네이버클라우드 기술&경험] 시대의 흐름, gRPC 깊게 파고들기 #2](https://medium.com/naver-cloud-platform/nbp-%EA%B8%B0%EC%88%A0-%EA%B2%BD%ED%97%98-%EC%8B%9C%EB%8C%80%EC%9D%98-%ED%9D%90%EB%A6%84-grpc-%EA%B9%8A%EA%B2%8C-%ED%8C%8C%EA%B3%A0%EB%93%A4%EA%B8%B0-2-b01d390a7190)
* [chatGPT](https://chat.openai.com/)