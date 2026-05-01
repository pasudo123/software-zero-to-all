FROM    ubuntu:18.04
LABEL   maintainer="pasudo123@naver.com" \
        description="this is test for docker image and container"
USER    root
RUN     apt-get update \
        && apt-get install vim -y \
        && apt-get install htop -y \
        && apt-get clean
RUN     echo "alias ll='ls -al'" >> ~/.bashrc
ENV     LANG=ko_KR.utf8 
ENV     TZ=Asia/Seoul