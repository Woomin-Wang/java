# Thread

Thread를 학습하기 전에 다음과 같은 운영체제 개념들을 먼저 익히는 것을 권장한다. 이번 글에서는 기본 개념에 대한 설명은 생략하고 바로 본론으로 들어간다.

- [Process, Thread](https://github.com/Woomin-Wang/TIL/blob/main/OS/process-thread.md)
- [Sync, Async, Blocking, Nonblocking](https://github.com/Woomin-Wang/TIL/blob/main/OS/sync-async-blocking-nonblocking.md)

<br/>

> 1절. 스레드 생성
> - 스레드 생성 방법(Thread 클래스, Runnable, Callable)
>   
> 2절. 스레드 생명 주기와 제어
> - 스레드 상태와 전이
> - 스레드 제어 메서드(sleep, join, interrupt 등)
>   
> 3절. 메모리 가시성 문제와 해결
> - 메모리 모델과 가시성 문제
> - volatile 키워드와 메모리 장벽
>
> 4절. 동기화 기본
> - synchronized 키워드 사용법과 특징
> - LockSupport
> - 공정성과 비공정성
> 
> 5절. CAS와 원자적 연산
> - CAS(Compare-And-Swap)의 원리
> - 동기화와 비교한 장단점
> 
> 6절. 동시성 컬렉션
> - java.util.concurrent 패키지 내 동시성 컬렉션 종류와 특징
> 
> 7절. 생산자-소비자 문제와 스레드 간 협력
> - 생산자-소비자 패턴 개념
> - wait, notify, notifyAll 메서드 활용
>
> 8절. 스레드풀과 작업 관리
> - ExecutorService와 스레드풀 구현
> - Future 비동기 작업 결과 처리
> - CompletableFuture의 장점과 활용

<br>
<br>

## 1. 스레드 생성

Java에서 스레드 생성 방법은 크게 세 가지이다.

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
Runnable 인터페이스 구현 후 Thread 생성자에 전달한다.

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
작업 수행 후 **결과를 반환**할 수 있으며, **체크 예외를 던질 수 있어** 예외 처리와 결과값이 필요한 비동기 작업에 적합하다.  

<br>
<br>

## 2. 스레드 생명 주기와 제어

자바 스레드의 생명 주기는 여러 상태로 나뉘어지며, 각 상태는 스레드가 실행되고 종료되기까지의 과정을 나타낸다.

<br>

![image](https://github.com/user-attachments/assets/25394abb-5e6b-427b-8e24-dedf748a3b6f)

**New (새로운 상태)**
- 스레드가 생성되었으나 **아직 실행되지 않은 상태**
      
<br>

**Runnable (실행 가능 상태)**
- 스레드가 **이미 실행 중이거나 실행 준비가 완료된 상태로**
    - 멀티스레드 환경에서는 각 스레드가 번갈아 가며 CPU를 사용하기 때문에, 실행 중이거나 대기 중인 모든 스레드는 Runnable 상태로 존재
    
<br>
  
**Bloked/Waiting (차단 상태/대기 상태)**
- 스레드가 **특정 조건이나 자원(락, I/O, 다른 스레드 작업 완료 등)을 기다리며 일시 정지된 상태**
    - **Blocked**: 락이 해제되기를 기다리는 상태 → 락이 풀리면 Runnable로 전환
    - **Waiting**: 무기한으로 다른 스레드 작업 완료를 기다리는 상태 (`wait()`, `join()` 호출 시 진입)

> 이 상태의 스레드는 **CPU 시간을 소비하지 않으며**, 스레드 스케줄러가 다시 실행 가능한 상태로 전환시킨다.
     
<br>
 
**Timed Waiting (시간 제한 대기 상태)**
- 스레드가 **일정 시간 동안 다른 스레드의 작업을 기다리는 상태**
    - `sleep()`, `wait(int timeout)`, `join(long millis)` 등 시간 제한이 있는 대기 메서드 호출 시 진입
    - 타임아웃 또는 `notify()` 알림을 받으면 Runnable 상태로 전환
      
<br>

**Terminated (종료 상태)**
- 스레드가 정상적으로 실행을 마쳤거나, 처리되지 않은 예외 등 비정상적 종료로 실행이 끝난 상태

<br>

### 스레드 제어 메서드

- sleep(long millis)
- 

- join

- interrupt






<br>
<br>

## 4. 메모리 가시성 문제와 해결

멀티스레드 환경에서는 **한 스레드가 변경한 변수 값이 다른 스레드에게 보이지 않는 문제** 즉, **메모리 가시성 문제**가 발생할 수 있다.

자바는 성능 최적화를 위해 **CPU 캐시**와 **메인 메모리 간의 불일치**가 허용되며, 이로 인해 다음과 같은 문제가 발생할 수 있다.

### 메모리 가시성(visibillity)
- 한 스레드가 변수 값을 변경해도, 다른 스레드는 **CPU 캐시와 메인 메모리 간의 동기화 지연**으로 이전 값을 읽는 현상

![image](https://github.com/user-attachments/assets/273d3493-c594-415d-843f-9ea255730fb2)

> 🔎 왜 발생하는가?
> - 각 스레드는 **자신만의 CPU 캐시**를 사용할 수 있다.
> - 변수의 최신 값이 **메인 메모리에 즉시 반영되지 않거나, 다른 스레드가 반영된 값을 읽지 않아서 발생한다.**

<br>

### volatile 키워드 사용

- 변수에 `volatile`을 선언하면, **모든 스레드가 해당 변수에 대해 항상 메인 메모리에서 직접 값을 읽고 쓰도록 보장**한다.
- 하지만, **캐시를 사용하지 않고 메인 메모리에 직접 접근하기 때문에 속도가 느려질 수 있다.**

```java
public class VolatileExample {

    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {

        Thread worker = new Thread(() -> {
            System.out.println("스레드 시작");

            while (running) {
                // busy-wait
            }

            System.out.println("스레드 종료");
        });

        worker.start();

        // 1초 후 종료 신호
        Thread.sleep(1000);
        running = false;

        worker.join(); // 스레드가 끝날 때까지 대기
        System.out.println("메인 종료");
    }
}
```

이 예제에서 volatile 키워드 덕분에, `runnung = false`로의 변경이 **즉시 다른 스레드에게 반영**되어 루프가 정상적으로 종료된다.

<br>

> volatile은 **메모리 가시성 문제는 해결**하지만, **원자성 문제는 해결하지 못한다.**
>
> 가시성과 원자성 모두 해결하려면 **synchronized, Lock, CAS(Compare-And-Swap)** 등의 동기화 기법을 사용해야 한다.
>
> 이러한 원자성 문제의 해결책은 5,6절에서 다룬다.

<br>
<br>

## 5. 동기화 기본

앞서 volatile 키워드로 **메모리 가시성 문제**는 해결할 수 있지만, **원자성 문제**는 여전히 남아 있다.  
여러 스레드가 동시에 공유 자원에 접근하는 경우, **데이터 일관성을 보장하기 위해 동기화**(synchronization)가 필요하다.

자바는 이를 위해 대표적으로 두 가지 방식의 동기화를 제공한다.

### synchronized 키워드

- synchronized는 **임계 구역에 한 번에 하나의 스레드만 접근**할 수 있도록 보장하여, 경쟁 상태(Race Condition)를 방지한다.

![image](https://github.com/user-attachments/assets/81a0b48c-6ce4-4a75-95dd-9c3c47a4c6b8)

**동기화 메서드**

```java
public class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++; // 임계 구역
    }

    public synchronized int getCount() {
        return count; // 임계 구역
    }
}
```

<br>

**동기화 블록**

```java
public void increment() {
    synchronized (this) {
        count++; // 임계 구역
    }
}
```

블록 단위로 지정하면 **보다 정밀하게 동기화 범위를 제어**할 수 있어 성능 최적화에 유리하다.

<br>

**synchronized의 한계**

- **무한대기**
    - synchronized를 사용하면 **락을 가진 스레드가 작업을 완료할 때까지 다른 스레드는 무조건 대기**해야 한다.
    - 락을 기다리는 동안 **타임아웃 설정 불가, 인터럽트로 중단 불가**하여 유연한 제어가 어렵다.
      
- **공정성 문제**
    - 락이 해제된 뒤 **어떤 스레드가 락을 얻을지 보장하지 않는다.** 
    - 스레드 스케줄링에 따라 **특정 스레드가 계속 락을 얻지 못하는 기아 상태 현상이 발생**할 수 있다.

<br>

> Java 1.5부터 더 정교한 동기화 제어를 위해 java.util.concurrent 패키지가 도입됐다.

<br>
<br>

### LockSupport

synchronized를 사용할 경우, 임계 영역에서 대기 중인 스레드는 **BLOCKED 상태**가 되어 **인터럽트로 깨어나기 어렵고 무한 대기 가능성**이 있다.

반면, LockSupport는 스레드를 **WAITING 상태로 전환**시켜 **인터럽트에 반응할** 수 있도록 한다.

- **park()**
    - 현재 스레드를 WAITING 상태로 전환하여 **무기한 대기**
      
- **parkNanos(long nanos)** 
    - 현재 스레드를 TIMED_WAITING 상태로 전환하여 **지정된 시간만큼 대기**
    - 시간이 지나면 자동으로 RUNNABLE 상태로 전환
      
- **unpark(Thread thread)** 
    - 지정 스레드를 WAITING 또는 TIME_WAITING 상태에서 RUNNABLE 상태로 전환
    - **다른 스레드가 호출해야 하며**, 대기 중인 스레드 자신이 호출 불가

<br>
  
```java
public class LockSupportMainV1 {

    public static void main(String[] args) {

        Thread thread1 = new Thread(new ParkTask(), "Thread-1");
        thread1.start(); // 스레드 시작 -> park()에서 대기 상태로 진입

        sleep(100); // 잠시 대기하여 thread1이 park 상태에 들어가도록 함
        log("Thread-1 state: " + thread1.getState()); // 스레드 상태 출력 (WAITING 예상)

        log("main -> unpark(Thread-1)");
        LockSupport.unpark(thread1); // 대기 중인 스레드 깨우기
        // thread1.interrupt(); // 인터럽트로도 깨울 수 있음 (주석 처리됨)
    }

    static class ParkTask implements Runnable {

        @Override
        public void run() {
            log("park 시작");
            LockSupport.park(); // 스레드를 WAITING 상태로 대기시킴
            log("park 종료, state: " + Thread.currentThread().getState()); // 깨어난 후 상태 출력
            log("인터럽트 상태: " + Thread.currentThread().isInterrupted()); // 인터럽트 여부 출력
        }
    }
}
```

`park()`는 스레드를 대기 상태로 만들고, `unpark()`가 호출되면 해당 스레드는 깨어난다.  
또한 인터럽트가 발생해도 깨어나며, 이때 인터럽트 플래그가 ture로 설정된다.

<br>

> LockSupport는 저수준이라 직접 사용하기는 어렵지만, 자바의 Lock 인터페이스와 ReentrantLock이 이를 내부적으로 활용하여, synchronized의 단점을 보완하고 더 유연하고 강력한 동기화 기능을 제공한다.

<br>

### ReentrantLock

ReentrantLock은 Lock 인터페이스의 구현체 중 하나로, 멀티스레딩 환경에서 임계 구역을 보호하기 위해 사용된다.

synchronized가 사용하는 **객체 내부의 모니터 락과 달리, ReentrantLock은 자체적으로 관리하는 별도의 락을 제공한다.**

<br>

**Lock 인터페이스**
```java
public interface Lock {
    void lock();
    void lockInterruptibly() throws InterruptedException;
    boolean tryLock();
    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
    void unlock();
    Condition newCondition();
}
```
> Lock 인터페이스는 동시성 프로그래밍에서 쓰이는 안전한 임계 영역을 위한 락을 구현하는데 사용된다.

<br>

- **lock()**
    - 임계 영역 진입하기 위해 락을 획득하는 메서드이다.
    - 다른 스레드가 이미 락을 보유 중이면, 현재 스레드는 락을 얻을 때까지 **WAITING 상태로 대기**한다.
    - 이 상태에서는 인터럽트에 응답하지 않는다.
        - 정확히는 인터럽트 발생 시 잠깐 RUNNABLE 상태로 전환되었다가, 즉시 WAITING 상태로 전환된다.

- **lockInterruptibly()**
    - `lock()`처럼 락을 획득하지만, 인터럽트가 발생하면 즉시 InterruptedException 예외를 발생시킨다.
    - 즉, **인터럽트에 응답 가능한 락 요청**이다.

- **tryLock()**
    - 락 즉시 획득하려 시도하며, 락 획득 성공 시 true, 실패 시 false를 반환한다.
    - 대기하지 않고 **즉시 반환**되는 것이 특징이다.

- **tryLock(long time, TimeUnit unit)**
    - 락 획득을 시도하되, 락이 이미 점유 중이라면 최대 time 만큼 TIMED_WAITING 상태로 대기한다.
    - 대기 중 인터럽트 발생 시 InterruptedException이 발생한다. 

- **unlock()**
    - 현재 스레드가 가지고 있는 락을 반환한다.
    - 반드시 `lock()` 또는 `lockInterruptibly()`로 락을 획득하고 사용 완료 후 반드시 `unlock()`을 호출해야 한다.
        - 호출하지 않으면 다른 스레드가 락을 획득하지 못해 영구 대기 상태에 빠질 수 있다.

<br>

```java
public class Counter {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count++; // 임계 구역
        } finally {
            lock.unlock(); // 반드시 해제해야 deadlock 방지 가능
        }
    }
}
```
 
<br>
 
> **데드락(Deadlock)**
> - 두 개 이상의 스레드가 **서로 상대방이 보유한 자원을 기다리며 무한 대기 상태에 빠지는 현상**이다.
> - 특히, Lock을 사용할 때 `unlock()`을 호출하지 않으면 데드락이 발생할 수 있으므로, 반드시 try-finally 블록을 사용해 락을 해제해야 한다.

<br>

### 공정성과 비공정성

락 해제 시 어떤 대기 중인 스레드가 락을 획득할지는 보장되지 않는다. 

이에 대한 해결책으로, ReentrantLock은 공정성 모드와 비공정성 모드를 지원한다.

- **공정성(Fairness)**: 대기 중인 스레드가 락을 공평하게 획득할 수 있도록 보장하는 것

- **비공정성(Unfairness)**: 락 획득 순서가 보장되지 않아 특정 스레드가 계속 락을 획득할 수도 있다.

```java
// 비공정 모드 락
private final Lock nonFairLock = new ReentrantLock();

// 공정 모드 락
private final Lock fairLock = new ReentrantLock(true);
```
> 비공정 모드는 성능은 우수하지만, 특정 스레드가 계속 락을 독점할 수 있다. 반면, 공정 모드는 기아 현상을 줄이지만 성능 저하가 발생할 수 있다.

<br>
<br>

## 6. CAS와 원자적 연산

![image](https://github.com/user-attachments/assets/1dddc5a6-50f7-4aac-a86d-9181103afd24)


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













