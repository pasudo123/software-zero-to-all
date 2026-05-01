## 아파치 스파크 입문 1강 - Spark 의 개념과 활용
* https://youtu.be/rjJ54qtOjW4 를 듣고 정리

## HDFS
* 분산파일 시스템 지원
* 큰 파일 저장 & 처리
* 병렬처리를 위해, 재연산을 위해 HDFS 는 카피본을 저장
* 디스크 IO 를 일으킴 : latency 가 존재
* Master - Slave 구조
    * Master Node : Single Name Node
    * Slave Node : Data Node (multi 로 구성)
* block 단위로 데이터를 잘라서 저장
    * 저장된 단위는 read/write 를 수행하는 단위
    * large 파일인 경우 좋음
    * small 파일인 경우 좋지 않음

## Map Reduce
* 하둡 에코시스템 패러다임
* Map 하고 Reduce 를 한다. : 코드를 병렬적으로 간단한게 할 수 있는 프로그래밍 모델

## Apache Spark
* 기존 맵리듀스 컴퓨팅보다 더 빠르게 할 수 있다.
* 반복적으로 데이터를 연산, 기존 하둡구조는 매번 요청이 있을때 diskIO 가 발생하는데 스파크는 메모리에 올려서 처리할 수 있다.
* 아파치 스파크는 데이터 분석, 머신러닝, 그래프, 스트리밍 데이터 등을 지원한다.
* 스파크는 분산 병렬 프로그래밍을 하는 솔루션.
* 스파크만으로 할 수 있는 것보단, 하둡을 붙이든 RDB 를 붙이든 프로세싱 할 수 있는 능력을 극대화 시킨다.

## MapReduce vs Spark
* 맵리듀스는 데이터를 읽고 쓰고 x n, disk io 가 발생한다. (shuttle + sort 등)
* 스파크는 메모리에 데이터를 올려놓고, 거기서 연산을 수행한다. disk io 있는것을 메모리에서 처리한다.
* https://docs.microsoft.com/ko-kr/azure/hdinsight/spark/apache-spark-overview 참고

## spark basic architecture
* 스파크 클러스터(멀티노드) 가 있다.
* driver process 내에 spark session + user code 가 있고 이를 cluster manager 에 올려두면 cluster manager 에 여러 executor 를 띄운다.
    * 다수의 executor 가 실제로 일하고 있다.
    * user code 는 executor 에서 동작한다.
* spark session 은 JVM 내부에서 구현
* user code 는 python 이 되거나 R 이 되거나 그러하다.

```shell
|--------- driver process ---------|   |-----------------------------|
|       spark session              |   |                executor     |
|           +                      |   |    executor                 |
|       user code                  |   |                executor     |
|----------------------------------|   |-----------------------------|
                  |                                   |
                  |                                   |
                  |                                   |
|--------------------------------------------------------------------|
|                                                                    |
|                           cluster manager                          |
|                                                                    |
|--------------------------------------------------------------------|
```

## spark's use cases
* streaming data : 실시간 처리
* machine learning : 인공지능
* interactive analysis
* data warehousing
* batch processing
    * hive 로 많이 한다. 
    * 집계를 위해 한번 처리하는 것등.
* exploratory data analysis
* grapha data analysis
* GIS 

## Hadoop vs Spark
* 하둡 (hdfs, map reduce)
    * 빅데이터에 대한 쉬운 솔루션 제공
    * 분산 프로그래밍 시스템에 대한 패러다임 제공
    * 하둡은 에코시스템에 대해 포괄적이다.
* 스파크
    * 맵리듀스의 확장판
    * 하둡의 기존 hdfs 는 그대로 사용할 수 있다.
    * 분석할 경우 유용하다. 인터랙티브하다.