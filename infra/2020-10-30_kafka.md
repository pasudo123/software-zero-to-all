# kafka
* 카프카 설치

## 카프카 설치

### 주키퍼 설치
1. centos7 이미지 준비, 해당 컨테이너 내에 주키퍼를 설치수행
2. `cd /usr/local` 이동
3. `yum install wget` 
4. `wget https://downloads.apache.org/zookeeper/zookeeper-3.5.8/apache-zookeeper-3.5.8-bin.tar.gz` 수행
5. `tar zxf apache-zookeeper-3.5.8-bin.tar.gz` 압축해제
6. `zookeepr` 심볼릭링크 생성
7. 앙상블 내에 주키퍼 노드를 구분하기 위한 ID 생성
    * `mkdir -p /data`
    * `echo 1 > /data/myid`
8. 주키퍼 환경설정 파일 생성
    * 


###
