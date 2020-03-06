## 이미지 빌드
`$ docker build -t {image-build-name} .` 
* __dot__ 표기를 할 것!

## 컨테이너 생성 및 실행
`$ docker run -p {sour-port}:{dest-port} {image-name}:{tag-name}`

## 현재 떠있는 컨테이너 목록 확인
`$ docker ps`

## 특정 컨테이너 접속
`$ docker exec -it {container-id} /bin/bash`

## 이미지 조회
`$ docker images`

## 특정 이미지 삭제
`$ docker rmi {image-id}`

## 이름이 없는 이미지 전체 삭제
`$ docker image prune`
