# Thread

Thread를 학습하기 전에 다음과 같은 운영체제 개념들을 먼저 익히는 것을 권장한다. 이번 글에서는 기본 개념에 대한 설명은 생략하고 바로 본론으로 들어간다.

- [Process, Thread](https://github.com/Woomin-Wang/TIL/blob/main/OS/process-thread.md)
- [Sync, Async, Blocking, Nonblocking](https://github.com/Woomin-Wang/TIL/blob/main/OS/sync-async-blocking-nonblocking.md)

<br/>

> 1절. 동시성, 병렬성
> - 동시성과 병렬성의 차이와 기본 개념 이해
>
> 2절. 스레드 생성
> - 스레드 생성 방법(Thread 클래스, Runnable, Callable)
>   
> 3절. 스레드 생명 주기와 제어
> - 스레드 상태와 전이
> - 스레드 제어 메서드(sleep, join, interrupt 등)
>   
> 4절. 메모리 가시성 문제와 해결
> - 메모리 모델과 가시성 문제
> - volatile 키워드와 메모리 장벽
>
> 5절. 동기화 기본
> - synchronized 키워드 사용법과 특징
> - java.util.concurrent.Lock 인터페이스
> 
> 6절. CAS와 원자적 연산
> - CAS(Compare-And-Swap)의 원리
> - 동기화와 비교한 장단점
> 
> 7절. 동시성 컬렉션
> - java.util.concurrent 패키지 내 동시성 컬렉션 종류와 특징
> 
> 8절. 생산자-소비자 문제와 스레드 간 협력
> - 생산자-소비자 패턴 개념
> - wait, notify, notifyAll 메서드 활용
>
> 9절. 스레드풀과 작업 관리
> - ExecutorService와 스레드풀 구현
> - Future 비동기 작업 결과 처리
> - CompletableFuture의 장점과 활용

<br>
<br>

## 1. 동시성(Concurrency), 병렬성(Parallelism)

멀티 스레드 환경에서는 동시성 또는 병렬성으로 실행됩니다.

**병렬성(Parallelism)**
- 여러 CPU 코어가 각각 독립적인 작업을 동시에 수행하는 방식

```java
CPU 1: A ------------------------->

CPU 2: B ------------------------->
```

<br>

**동시성(Concurrency)**
- 단일 CPU 코어가 여러 작업을 번갈아가며 수행하는 방식

```java
CPU 1: A -----------> B ----------> A -----------> B ---------->
```

<br>

> 즉, 동시성은 '겹쳐서' 실행하는 개념이고, 병렬성은 '완전히 동시에' 실행한느 개념입니다.

<br>
<br>

## 2. 스레드 생성

Jav에서 스레드 생성 방법은 크게 세 가지입니다:

<br>

**1. Thread 클래스 상속**

```java
public class ThreadByExtends extends Thread {
    @Override
    public void run() {
        // 실행할 코드 작성
    }
}
```

<br>

**2. Runnable 인터페이스 구현**

```java
public class ThreadByRunnable implements Runnable {
    @Override
    public void run() {
        // 실행할 코드 작성
    }
}
```
Runnable 인터페이스 구현 후 Thread 생성자에 전달합니다.

<br>

**3. Callable 인터페이스 구현**

```java
import java.util.concurrent.Callable;

public class ThreadByCallable implements Callable<V> { // V는 반환 타입 (예: String, Integer 등)
    @Override
    public V call() throws Exception {
        // 실행할 코드 작성 및 결과 반환
        return null; // 또는 실제 결과
    }
}
```

Callable은 Runnable의 단점을 보완하기 위해 만들어졌다.

작업 수행 후 결과를 반환할 수 있으며, 체크 예외를 던질 수 있어 예외 처리와 결과값이 필요한 비동기 작업에 적합하다.

<br>
<br>

## 3. 스레드 생명 주기와 제어






<br>
<br>


## 4. 메모리 가시성 문제와 해결

<br>
<br>

## 5. 동기화 기본

<br>
<br>

## 6. CAS와 원자적 연산

<br>
<br>

## 7. 동시성 컬렉션

<br>
<br>

## 8. 생산자-소비자 문제와 스레드 간 협력

<br>
<br>

## 9. 스레드풀과 작업 관리

### Future

Future는 비동기 작업의 결과를 나중에 받아올 수 있도록 하는 인터페이스이다.

Future를 사용하려면, 시간이 오래 걸리는 작업을 Callable 객체로 구현한 후, 이를 ExecutorService에 제출해야 한다.
ExecutorService는 Callable 작업을 실행하고, Future 객체를 반환한다. 이 Future 객첼르 통해 작업이 완료됐는지 확인하거나, 완료된 결과를 가져올 수 있다.

즉, Future는 작업 완료 전까지 대기하거나, 작업 진행 중에도 다른 작업을 수행할 수 있게 해준다.

<br>

### ExecutorService

ExecutorService는 자바에서 비동기 작업 실행과 스레드 관리를 위한 인터페이스로, 작업 실행을 스레드 풀에 위임하여 효율적인 스레드 재사용과 관리를 지원한다.

직접 Thread를 생성하고 관리하는 경우, 스레드 생성 비용이 크고 종료 후 재사용이 어려워 성능 저하 및 자원 낭비가 발생할 수 있다. 
ExecutorService는 내부에 스레드 풀을 관리하여, 스레드를 미리 생성하거나 필요에 따라 생성하고 재활용함으로써 이러한 문제를 해결한다.

주요 ExecutorService 생성 메서드와 특징은 다음과 같다:

- **Executors.newFixedThreadPool(int nThreads)**
  - 고정된 개수의 스레드 풀을 생성한다. 스레드 개수가 고정되어 있어, 과도한 스레드 생성 방지와 예측 가능한 자원 사용이 가능하다.
- **Executors.newCachedThreadPool()**
  - 작업량에 따라 필요 시 스레드를 생성하고, 사용이 끝난 스레드를 일정 시간 동안 재활용한다. 단기적이고 많은 수의 작업을 처리할 때 적합하다.   
- **Executors.newScheduledThreadPool(int corePoolSize)**
  - 일정 지연 후 실행하거나 주기적으로 실행하는 작업을 위한 스케줄링 가능한 스레드 풀을 생성한다.
- **Executors.newSingleThreadExecutor()**
  - 단일 스레드로 작업을 순차적으로 처리하는 스레드 풀을 생성한다. 작업 순서 보장이 필요할 때 사용한다.

<br>

**Java 8 이전의 코드**

```java
// ThreadPool에 작업을 제출하기 위해 ExecutorService 생성
ExecutorService executor = Executors.newCachedThreadPool();

// Callable 객체를 submit하여 Future 반환받음
Future<Double> future = executor.submit(new Callable<Double>() {
    @Override
    public Double call() throws Exception {
        return doSomeLongComputation(); // 시간이 오래 걸리는 작업을 비동기 실행
    }
});

doSomethingElse(); // 비동기 작업이 실행되는 동안 다른 작업 수행

try {
    // 최대 1초 동안 비동기 작업 결과를 기다림
    Double result = future.get(1, TimeUnit.SECONDS);
} catch (ExecutionException e) {
    // 작업 수행 중 예외 발생 시 처리
} catch (InterruptedException e) {
    // 대기 중 스레드가 인터럽트된 경우 처리
} catch (TimeoutException e) {
    // 지정한 시간 내에 작업이 완료되지 않아 타임아웃 발생 시 처리
}

```

오래 걸리는 작업이 영원히 끝나지 않으면, 호출 스레드가 무한정 대기 상태에 빠질 수 있다.
이를 방지하기 위해 Future의 `get(long timeout, TimeUnit unit)` 메서드를 활용해 최대 대기 시간을 설정하는 것이 좋다.

<br>

![image](https://github.com/user-attachments/assets/836c0342-6e84-4770-bc45-61333af07ed6)


**Future의 단점**

Future 인터페이스 Java 5부터 `java.util.concurrent` 패키지에서 비동기 작업의 결과를 받기 위해 도입디었다.
하지만 다음과 같은 한계가 있다:

- 비동기 작업 결과를 쉽게 조합할 수 없다.
- 비동기 작업 도중 발생한 예외 처리가 불편하다.
- 비동기 작업 완료 후 후속 작업을 간편하게 연결하기 어렵다.

이러한 단점을 개선하고자 Java 8 에서 CompletableFuture가 추가되었다.

<br>

### CompletableFuture













