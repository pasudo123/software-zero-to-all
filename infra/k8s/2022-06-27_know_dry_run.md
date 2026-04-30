## --dry-run=client 옵션
실제 실행을 시키지 않고 쿠버네티스 클러스터에 전달한 오브젝트 객체를 보여준다.

## 커맨드
created 문만 출력되는 형태, 실제 수행은 하지 않는다.
```shell
❯ kubectl create -f deployment01.yaml --dry-run=client
deployment.apps/nginx-deployment created (dry run)
```

yaml 내용만 출력되는 형태
```
❯ kubectl create -f deployment01.yaml --dry-run=client -o yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: nginx
  name: nginx-deployment
  namespace: default
spec:
  replicas: 3
  selector:
    matchLabels:
      app: nginx
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - image: nginx:1.14.2
        name: nginx
        ports:
        - containerPort: 80
```
