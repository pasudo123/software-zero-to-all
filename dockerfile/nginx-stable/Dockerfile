FROM    nginx:stable
RUN     apt-get update && apt-get install -y vim
RUN     mkdir -p /data/log/nginx
RUN     echo "alias ll='ls -al'" >> ~/.bashrc
ENV     TZ Asia/Seoul
