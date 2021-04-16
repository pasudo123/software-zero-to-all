# k8s resources
* 단순 일반 PC 의 메모리와 CPU 를 생각하면 된다.
* k8s 는 cpu 와 memory, 이 두 종류의 리소스를 관리할 수 있다.

# 리소스 구성을 구성해본다.
* [참고](https://kubernetes.io/ko/docs/concepts/cluster-administration/manage-deployment/)   

많은 애플리케이션은 디플로이먼트 및 서비스와 같은 여러 리소스를 필요로 한다.    
여러 리소스는 동일판 파일에 그룹화하여 단순화할 수 있다. `yaml 에서 --- 로 구분이 가능하다.`
```yaml
apiVersion: v1
kind: Service
metadata:
  name: my-nginx-svc
  labels:
    app: nginx
spec:
  type: LoadBalancer
  ports:
  - port: 80
  selector:
    app: nginx
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-nginx
  labels:
    app: nginx
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
      - name: nginx
        image: nginx:1.14.2
        ports:
        - containerPort: 80

```
  

# reference
