# Virtual Thread vs platform Thread 성능 비교 예제

## 📋 프로젝트 소개

Java 21에서 도입된 Virtual Thread(가상 스레드)와 기존 platform Thread(플랫폼 스레드)의 성능 차이를 비교하는 예제 프로젝트입니다. 소수 찾기 알고리즘을 통해 세 가지 스레드 모델의 성능을 측정하고 비교합니다.

## 🎯 목적

- **단일 스레드 vs 멀티 스레드 vs 가상 스레드**의 성능 차이 이해
- Java 21의 Virtual Thread 기능 학습
- CPU 집약적 작업에서의 스레드 모델별 특성 파악

## 🚀 기능

### 세 가지 실행 방식

1. **단일 스레드 (Single Thread)**
   - 전통적인 순차 처리 방식
   - 하나의 스레드로 전체 범위 처리

2. **가상 스레드 (Virtual Thread)**
   - Java 21의 경량 스레드
   - 수천 개의 가상 스레드를 생성하여 병렬 처리
   - 1,000개 단위로 작업 분할

3. **플랫폼 스레드 (platform Thread)**
   - 기존 Java의 OS 스레드
   - CPU 코어 수만큼 스레드 생성
   - 균등하게 작업 분할

## 📁 프로젝트 구조

```
VirtualThread_Example/
├── src/
│   ├── Main.java                          # 메인 실행 클래스
│   └── findPrimes/
│       ├── PrimeSingleThread.java         # 단일 스레드 구현
│       ├── PrimeVirtualThread.java        # 가상 스레드 구현
│       └── PrimePlatformThread.java       # 플랫폼 스레드 구현
└── README.md
```

## 🔧 요구사항

- **Java 21 이상** (Virtual Thread 지원)
- IntelliJ IDEA 또는 Java 지원 IDE

## 💻 실행 방법

1. 프로젝트 클론 또는 다운로드
```bash
git clone [repository-url]
cd VirtualThread_Example
```

2. Java 21 이상 버전 확인
```bash
java --version
```

3. 컴파일 및 실행
```bash
javac src/**/*.java -d out
java -cp out Main
```

또는 IDE에서 직접 `Main.java` 실행

## 📊 알고리즘 설명

### 소수 판별 알고리즘
- **6k ± 1 최적화 기법** 사용
- 2와 3의 배수를 제외하고, 6k ± 1 형태의 수만 검사
- 제곱근까지만 확인하여 효율성 향상

### 작업 분할 전략
- **가상 스레드**: 1,000개 단위로 작업 분할
- **플랫폼 스레드**: CPU 코어 수에 맞춰 균등 분할

## 📈 예상 결과

```
== 소수 찾기 성능 비교 ==
범위: 1~1,000,000,000
CPU 코어 수: [시스템 코어 수]
==================================================

1. 단일 쓰레드 방식
단일 쓰레드 실행 시간: XXXXms
찾은 소수 개수: XXX개

2. 가상 쓰레드 방식
가상 쓰레드 실행 시간: XXXXms
사용한 가상 쓰레드 수: XXXX개
찾은 소수 개수: XXX개

3. 멀티 쓰레드 방식
플랫폼 쓰레드 실행 시간: XXXXms
사용한 플랫폼 쓰레드 수: X
찾은 소수 개수: XXX개

==================================================
📊 성능 비교 결과
==================================================
실행 시간:
  • 단일 쓰레드: XXXXms
  • 가상 쓰레드: XXXXms
  • 플랫폼 쓰레드: XXXXms

단일 쓰레드 대비 성능 향상:
  • 가상 쓰레드: X.XXx 빠름
  • 플랫폼 쓰레드: X.XXx 빠름

🏆 가장 빠른 방식: [결과]
==================================================
```

## 💡 주요 발견 사항

### CPU 집약적 작업의 특성
- **플랫폼 스레드**가 일반적으로 가장 좋은 성능
- CPU 코어 수에 최적화된 스레드 수 사용
- 컨텍스트 스위칭 오버헤드 최소화

### Virtual Thread의 특징
- I/O 집약적 작업에 최적화
- CPU 집약적 작업에서는 오버헤드 발생 가능
- 많은 수의 경량 스레드 생성 가능

### 성능 영향 요소
- CPU 코어 수
- 작업 범위 크기
- 메모리 사용량
- JVM 최적화 수준

## 🔍 추가 탐구 주제

- [ ] I/O 집약적 작업에서의 Virtual Thread 성능 비교
- [ ] 메모리 사용량 비교 분석
- [ ] 다양한 작업 분할 크기에 따른 성능 변화
- [ ] 소켓 통신에서의 Virtual Thread 활용
- [ ] Virtual Thread의 내부 구조 및 동작 원리

## 📚 참고 자료

- [JEP 444: Virtual Threads](https://openjdk.org/jeps/444)
- [Java 21 Virtual Thread Documentation](https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html)
- [Virtual Threads Performance Guide](https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html#GUID-DC4306FC-D6C1-4BCC-AECE-48C32C1A8DAA)

## 👨‍💻 작성자

- **Chan**

## 📄 라이선스

이 프로젝트는 학습 목적으로 작성되었습니다.