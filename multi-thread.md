# Thread

Thread를 학습하기 전에 다음과 같은 운영체제 개념들을 먼저 익히는 것을 권장한다. 이번 글에서는 기본 개념에 대한 설명은 생략하고 바로 본론으로 들어간다.

- [Process, Thread](https://github.com/Woomin-Wang/TIL/blob/main/OS/process-thread.md)
- [Sync, Async, Blocking, Nonblocking](https://github.com/Woomin-Wang/TIL/blob/main/OS/sync-async-blocking-nonblocking.md)

<br/>

> 1절. 멀티 스레드 개념
>
> 2절. 작업 스레드 생성과 실행
>
> 3절. 스레드 우선순위
> 
> 4절. 동기화 메소드와 동기화 블록
>
> 5절. 스레드 상태
>
> 6절. 스레드 상태 제어
> 
> 7절. 데몬 스레드
> 
> 8절. 스레드 그룹
>
> 9절. 스레드풀

<br>
<br>

## 동시성(Concurrency), 병렬성(Parallelism)

멀티 스레드 환경에서는 동시성 또는 병렬성으로 실행된다.

**병렬성(Parallelism)**

```java
CPU 1: A ------------------------->

CPU 2: B ------------------------->
```

**동시성(Concurrency)**

```java
CPU 1: A -----------> B ----------> A -----------> B ---------->
```

동시성은 스레드 스케줄링에 따라 실행 순서가 결정된다. 스레드들은 매우 짧은 시간 단위로 번갈아가며 자신의 작업(`run()`)를 조금씩 실행한다.


<br>
<br>

## Runnable, Callable

Callable은 Runnable의 단점을 보완하기 위해 만들어졌다.

```java
public interface Runnable {
    public void run();
}
```

```java
public interface Callable<V> {
    V call() throws Exception
}
```

`run()` 메서드는 결과 값을 리턴하지 않기 때문에, 실행 결과를 얻으려변 공용 메모리나 파이프 같은 방식을 사용해야 했다.

<br>
<br>

## Future

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













