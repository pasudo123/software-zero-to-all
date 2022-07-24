## configure : application arguments

## COMMAND : 도커 실행 시 커맨드 실행
```shell
$ docker run ubuntu {command}
$ docker run ubuntu sleep 5
```

위 내용을 도커파일에서 정의한다면?   
* `CMD ["command", "param1", "param2"]`   
* `CMD ["sleep", "5"]`

## ENTRY POINT : 도커 실행 시 인자 넘기기
```shell
$ docker run ubuntu-sleeper 10
```

위 내용을 도커파일에서 아래와 같이 정의할 수 있다.
* `ENTRYPOINT ["sleep"]`
* ENTRYPOINT 와 command 인자로 넘기는 10이 합쳐진다.

## COMMAND vs ENTRYPOINT
* COMMAND 는 모든 값이 대체된다. (오버라이딩)
    * ENTRYPOINT 와 같이 쓰이는 경우 ENTRYPOINT 인자로 쓰인다. (디폴트 값)
* ENTRYPOINT 는 기존 내용에 값을 추가할 수 있다.


## COMMAND 와 ENTRYPOINT 를 섞어쓰기
아래와 같이 도커파일이 명세되어있다고 생각한다.
```dockerfile
FROM ubuntu
ENTRYPOINT ["sleep"]
CMD ["5"]
```

그러면 명령어 작성을 좀 더 유연하게 할 수 있다.
``` 
// sleep 5 으로 수행
$ docker run ubuntu-sleeper

// sleep 10 으로 수행
$ docker run ubuntu-sleeper 10

// 새로운 entrypoint 를 입력
// sleep2.0 20 으로 수행
$ docker run --entrypoint sleep2.0 ubuntu-sleeper 20
```

## pod definition file 에서 응용해서 쓰기
기존에 ubuntu-sleeper 도커 이미지가 아래와 같이 만들어졌음
```dockerfile
FROM ubuntu
ENTRYPOINT ["sleep"]
CMD ["5"]
```

pod-definition.yml
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: ubuntu-sleeper-pod
spec:
    containers:
        - name: ubuntu-sleeper
          image: ubuntu-sleeper
          args: ["10"]
```

kubectl apply -f pod-definition.yml 는 docker run ubuntu-sleeper 10 과 동일하다.   
> 추가적으로 yml 파일 내에서 entrypoint 도 오버라이딩이 가능하다.

pod-definition.yml
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: ubuntu-sleeper-pod
spec:
    containers:
        - name: ubuntu-sleeper
          image: ubuntu-sleeper
          command: ["sleep2.0"]
          args: ["10"]
```

kubectl apply -f pod-definition.yml 은 docker run --entrypoint sleep2.0 ubuntu-sleeper 10 과 동일하다.   

## k8s command & args
* Dockerfile, ENTRYPOINT -> spec.containers.command 와 동일 (오버라이딩)
* Dockerfile, CMD -> spec.containers.args 와 동일 (오버라이딩)