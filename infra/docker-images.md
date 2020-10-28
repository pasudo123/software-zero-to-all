# docker-images
* 도커 이미지 정리

## centos 7.x

* [docker hub link](https://hub.docker.com/_/centos)


__Dockerfile__
```bash
FROM docker.io/centos:7.4.1708
 
# 사용자 지정
USER root
 
# 언어셋 설치
RUN yum clean all \
 && yum repolist \
 && yum -y update \
 && sed -i "s/en_US/all/" /etc/yum.conf  \
 && yum -y reinstall glibc-common
  
# 기본적으로 필요한 OS 패키지를 설치한다.
RUN  yum -y install tar unzip vi vim telnet net-tools curl openssl \
 && yum -y install apr apr-util apr-devel apr-util-devel \
 && yum -y install elinks locate python-setuptools \
 && yum clean all
  
ENV LANG=ko_KR.utf8 TZ=Asia/Seoul
 
# 컨테이너 실행시 실행될 명령
CMD ["/bin/bash"]
```

__java 설치__
```bash
yum -y install java-1.8.0-openjdk
```