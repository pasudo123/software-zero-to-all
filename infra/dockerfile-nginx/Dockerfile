# image 생성
FROM    nginx:stable
RUN     apt-get update && apt-get install -y vim 
RUN     mkdir -p /data/log/nginx
RUN     echo "alias ll='ls -al'" >> ~/.bashrc
COPY    conf.d/custom.conf /etc/nginx/conf.d/custom.conf
COPY    naruto.png  /data/static/images/naruto.png
COPY    sample.html /data/static/html/sample.html