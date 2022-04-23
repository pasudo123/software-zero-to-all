## tcpdump 👀
* 패킷 덤프 명령어

---
## 명령어
### 특정 인터페이스에 대한 패킷 출력
본인 PC 에 어떤 인터페이스에서 송수신 하는 패킷을 확인할 것인지 지정
```
$ tcpdump -i {{interface}}
```

### 특정 호스트에 대한 패킷 출력
```
$ tcpdump host {{ip}}
```

### 파일로 쓰기
```
$ tcpdump -w {{file-name}}.log
```
* 참고로 tcpdump -w 옵션을 통해서 쓴 파일은 `tcpdump -r` 을 통해서 읽어들일 수 있다. 그냥 읽으면 깨짐.

---
## 명령어 조합
```
// 특정 호스트에 대한 패킷데이터를 로깅파일로 남김
$ tcpdump host {{ip}} -w {{file-name}}.log

// pcap 파일로 남김
$ tcpdump host {{ip}} -w {{file-name}}.pcap
```

---
## pcap
* packet capture 의 약자
* 와이어샤크에서 사용하는 것도 pcap 라이브러리를 사용하고 있다.

---
## 참고
* https://www.solarwinds.com/resources/it-glossary/pcap