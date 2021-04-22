# 디플로이먼트 (deployment)
디플로이먼트는 파드와 레플리카셋에 대한 선언적 업데이트를 제공한다.   
   
* 디플로이먼트에서 의도하는 상태를 설명
* 디플로이먼트 컨트롤러는 현재 상태에서 의도하는 상태로 비율을 조정하며 변경
* 새 레플리카셋을 생성하는 디플로이먼트를 정의하거나 기존 디플로이먼트를 제거하고, 모든 리소스를 새 디플로이먼트에 적용할 수 있다.

## 유스케이스 : 레플리카셋을 롤아웃할 디플로이먼트
레플리카셋은 백그라운드에서 파드를 생성한다. 롤아웃 상태를 체크해서 성공여부를 확인한다.

```yaml
apiVersion: apps/v1
kind: Deployment
metadata: // 리소스에 대한 부가적인 정보들을 입력한다.
  name: nginx-deployment  // 디플로이먼트 이름을 설정한다.
  labels:
    app: nginx
spec:
  replicas: 3 // spec.replicas 필드에 따라 디플로이먼트는 3개의 레플리카 파드를 생성한다.
  selector: // spec.selector 디플로이먼틀가 관리할 파드를 찾는 방법을 정의한다. 여기선 파드 템플릿에 정의된 app: nginx 를 선택한다.
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
