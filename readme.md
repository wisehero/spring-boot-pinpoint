# Spring-boot-pinpoint

## 서버 환경

- 서버는 2대로 구성
    - 애플리케이션
        - ubuntu 22.04
        - 스프링 부트 3.x
        - jdk 17
    - 모니터링
        - ubuntu 22.04
        - jdk 8, jdk 11

## 목차 
- [pinpoint 환경설정 (Only ServerMap)](#pinpoint-환경설정-only-servermap)
- [참고자료](#참고 자료)
---

## pinpoint 환경설정 (Only ServerMap)

### JDK 8, JDK 11, HBASE 1.2.7 설치

```shell
sudo apt update

# HBASE 구동을 위한 JDK 8
sudo apt install openjdk-8-jdk -y
sudo apt install openjdk-8-jre -y

# WEB, Collector를 위한 JDK 11
sudo apt install openjdk-11-jdk

# 서버의 기본 JDK는 11
sudo update-alternatives --set java /usr/lib/jvm/java-11-openjdk-amd64

# HBASE 1.2.7 설치 
wget https://archive.apache.org/dist/hbase/1.2.7/hbase-1.2.7-bin.tar.gz
tar xzvf hbase-1.2.7-bin.tar.gz
```

### HBASE 환경설정

```shell
vi ./hbase-1.2.7/conf/hbase-env.sh


export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
# 이 옵션을 주석처리 하지 않으면 hbase 실행시 warning 이 뜸
export HBASE_MASTER_OPTS="$HBASE_MASTER_OPTS -XX:PermSize=128m -XX:MaxPermSize=128m"
export HBASE_REGIONSERVER_OPTS="$HBASE_REGIONSERVER_OPTS -XX:PermSize=128m -XX:MaxPermSize=128m"

# 저장 후 Hbase 실행
./hbase-1.2.7/bin/start-hbase.sh
```

### HBASE 스크립트 실행

```shell
cd ./hbase-1.2.7/bin
wget https://raw.githubusercontent.com/pinpoint-apm/pinpoint/master/hbase/scripts/hbase-create.hbase

# 스크립트 실행
./hbase shell hbase-create.hbase
```

### Web, Collector 다운로드

```shell
# Web
wget https://github.com/pinpoint-apm/pinpoint/releases/download/v2.5.4/pinpoint-web-boot-2.5.4.jar

# Collector
wget https://github.com/pinpoint-apm/pinpoint/releases/download/v2.5.4/pinpoint-collector-boot-2.5.4.jar

nohup java -jar -Dpinpoint.zookeeper.address=localhost pinpoint-web-boot-2.5.4.jar >/dev/null 2>&1 &
nohup java -jar -Dpinpoint.zookeeper.address=localhost pinpoint-collector-boot-2.5.4.jar >/dev/null 2>&1 &
```

### Server Agent 설치

```shell
# 서버에는 현재 애플리케이션 구동을 위한 JDK가 설치되어 있다고 가정
# agent 설치
wget https://repo1.maven.org/maven2/com/navercorp/pinpoint/pinpoint-agent/3.0.1/pinpoint-agent-3.0.1.tar.gz
 
# 압축 해제
tar xvzf pinpoint-agent-3.0.1.tar.gz

cd pinpoint-agent-3.0.1
# config 파일 수정
sudo vi pinpoint-root.config

# 앞에서 구성한 monitoring EC2의 ip로 수정
profiler.transport.grpc.collector.ip=pinpoint ip로 변경

java -javaagent:/home/ubuntu/pinpoint-agent-3.0.1/pinpoint-bootstrap-3.0.1.jar \
     -Dpinpoint.agentId=wisehero-springboot-01 \
     -Dpinpoint.applicationName=springboot-wisehero \
     -Dpinpoint.config=/home/ubuntu/pinpoint-agent-3.0.1/pinpoint-root.config \
     -jar spring-boot-pinpoint-0.0.1-SNAPSHOT.jar
```

### 참고 자료

- https://incheol-jung.gitbook.io/docs/q-and-a/infra/feat

---

## 남은 과제

- 나머지 기능은 어떻게 작동 시킬까?
  - 현재는 ServerMap만 사용 가능한 상태다.
  - v3를 기준으로 New Inspector, System Metric, URI 
- 계속해서 HBase에 데이터가 적재된다면, 디스크에 문제가 생길 것이다. 이는 어떻게 해결할까?

