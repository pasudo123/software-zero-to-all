## configure : env variable

## 사용방법
```yaml
apiVersion: v1
kind: Pod
metadata:
 name: ~~
spec:
 containers:
 - name: 
   image:
   ports:
    - containerPort: 8080
   
   env:
    - name: APP_COLOR
      value: pink
```

docker run command 의 일부와 유사하게 사용할 수 있다.
```shell
$ docker run -e APP_COLOR=pink {container_name}
```

## ENV value types
plain key/value
```yaml
env:
 - name: APP_COLOR
   value: pink
```

configmap
```yaml
env:
 - name: APP_COLOR
   valueFrom:
     configMapKeyRef:
```
* configmap 으로 쓰게되면 key/value 쌍으로 데이터를 저장할 수 있다.
* pod 이 뜨게 되는 시점에 configmap 의 내용이 pod 의 컨테이너 내에 할당된다.

secrets
```yaml
env:
 - name: APP_COLOR
   valueFrom:
     secretKeyRef:
```

## configmap
명령형
```shell
kubectl create configmap \
    {app-config} --from-literal={APP_COLOR}={blue} \
                 --from-literal={APP_COLOR}={blue}
```

```shell
kubectl create configmap \
    {app-config} --from-file=app.properties
```

선언형
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
 name: app-config
data:
 APP_COLOR: blue
```
```shell
$ kubectl create -f configmap.yaml
```

조회하기
```shell
$ kubectl get configmaps
$ kubectl describe configmap {configmap-name}
```

pod 에 주입하기
```yaml
apiVersion: v1
kind: Pod
metadata:
 name: ~~
spec:
 containers:
 - name: 
   image:
   ports:
    - containerPort: 8080
   
   envFrom:
    - configMapRef: 
        name: app-config // configmap.yml 에 적힌 metadata.name 과 매칭되어야 한다.
```

하나의 프로퍼티만 pod 에 주입하기
```yaml
env:
 - name: APP_COLOR
   valueFrom:
     configMapKeyRef:
       name: app-config
       key: APP_COLOR
```

볼륨으로도 설정할 수 있다.
```yaml
volume: 
 - name: app-config-name
   configMap:
     name: app-config
```