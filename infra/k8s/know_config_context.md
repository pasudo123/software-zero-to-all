## config & context
config 파일은 kubectl 이 설치되고, ~/.kube/config 디렉토리에 위치하고 있다.
해당 파일을 `$ cat config` 명령어를 수행하면 현재 pc 에 설정된 클러스터 목록을 볼 수 있다.

## 데이터의 형식
```yml
clusters
 - cluster: {cluster-name}
    insecure-skip-tls-verify: true
    server: {http://example.com:6443}

contexts
 - context:
    cluster: {cluster-name}
    namespace: {ns-name}
    user: {user-name}

current-context:
kind: Config
perferences: {}

users:
 - name: {user-name}
   user:
     token: {token-value}

```
* clusters, 쿠버네티스 클러스터 정보
* users, 클러스터에 접근하는 유저들의 정보
* context, cluster 와 user 를 조합해서 생성된 값
    * clusters-name, user-name 이 필요하다.
* current-context, 현재 사용하는 context 를 나타낸다.

## 명령어 나열
```shell
$ kubectl config current-context 
$ kubectl config use-context {context-name}
$ kubectl config get-contexts {context-name}
$ kubectl config get-clusters
```