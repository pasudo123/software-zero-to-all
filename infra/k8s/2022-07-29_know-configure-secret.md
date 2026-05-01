## configure : secret
* 비밀스런 정보를 저장하기 위한 리소스
* configmap 과 유사하고, 인코딩되어 저장된다. (해시포맷 형태 : base64)

## create secret
```shell
$ kubectl create secret generic \
    app-secret --from-literal=DB_HOST=mysql \
               --from-literal=DB_USER=root \
               --from-literal=DB_PASSWORD=pwd

$ kubectl create secret generic \
    app-secret --from-file={path to file}
```

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: app-secret
data:
  DB_HOST: {value}
```
* yml 로 저장될때는 인코딩된 값으로 치환해서 저장되어야 한다.
    * echo -n 'mysql' | base64
    * echo -n 'root' | base64
    * echo -n 'pwd' | base64
    * 위의 내용을 확인해서 secret.yml 파일에 넣어주어야 한다.
* 인코딩된 값은 디코딩을 수행한다.
    * echo -n '{~~}' | base64 --decode

## inject pod
```yml
envFrom:
 - secretRef:
     name: app-secret
```

env 
```yaml
envFrom:
 - secretRef:
     name: app-secret
```

single env
```yaml
env:
  - name: DB_PASSWORD
    valueFrom:
      secretKeyRef:
        name: app-secret
        key: DB_PASSWORD
```

volume
```yml
volume:
  - name: app-secret-volume
    secret:
      secretName: app-secret
```
* container 내부에 파일로 각각 설정되어있다.

## 강의 들으면서 추가사항
* secret 은 그렇게 안전하지 않음 사실, base64 인코딩/디코딩이기 때문에 취약하다. -> 서로간의 관행으로 안전함을 추구하는게 좋다.
* secret 의 내용을 레파지토리 저장소에 저장하지 않도록 한다.
* `Enabling Encryption at Rest for Secrets so they are stored encrypted in ETCD.`
* HashiCorp vault 를 이용하는 것이 더 안전할 수 있다.