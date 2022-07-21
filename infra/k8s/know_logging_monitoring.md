## logging & monitoring

## logging 메커니즘 쿠버네티스
```
$ kubectl apply -f {pod-definition}.yml

$ kubectl logs -f {pod-name}
```

## pod 내에 container 가 멀티로 존재하는 경우, 특정 컨테이너의 로그 조회
```
$ kubectl logs -f {pod-name} {container-name}
```
* 위의 내용에서 만약 `kubectl logs -f {pod-name}` 만 친다면 경고문이 나온다.

## 레퍼런스
* [실행중인 파드와 상호작용](https://kubernetes.io/ko/docs/reference/kubectl/cheatsheet/#%EC%8B%A4%ED%96%89-%EC%A4%91%EC%9D%B8-%ED%8C%8C%EB%93%9C%EC%99%80-%EC%83%81%ED%98%B8-%EC%9E%91%EC%9A%A9)