# Spring-boot-pinpoint

## 서버 환경

- 애플리케이션
    - 스프링 부트 3.x
    - jdk 21
- 모니터링
    - ubuntu 22.04
    - jdk 8, jdk 11

---

## 설치 과정

### JDK 8, JDK 11, HBASE 1.2.7 설치

```shell
sudo apt update

# HBASE 구동을 위한 JDK 8
sudo apt install openjdk-8-jdk -y
sudo apt install openjdk-8-jre -y

# WEB, Collector를 위한 JDK 11
sudo apt install openjdk-11-jdk

# 서버의 기본 JDK는 11
sudo update-alternatives --set java /usr/lib/jvm/java-11-openjdk-amd64/bin/java

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



