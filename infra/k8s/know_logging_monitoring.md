## logging & monitoring

## logging 메커니즘 쿠버네티스
```
$ kubectl apply -f {pod-definition}.yml

$ kubectl logs -f {pod-name}
```

## pod 내에 container 가 n 개 있는 경우, 특정 컨테이너의 로그 조회
```
$ kubectl logs -f {pod-name} {container-name}
```
* 위의 내용에서 만약 `kubectl logs -f {pod-name}` 만 친다면 경고문이 나온다.