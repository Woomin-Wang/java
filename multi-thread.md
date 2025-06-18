# Multi Thread

Multi Thread를 학습하기 전에 다음과 같은 운영체제 개념들을 먼저 익히는 것을 권장한다.  

이번 글에서는 기본 개념에 대한 설명은 생략하고 바로 본론으로 들어간다.

- [Process, Thread](https://github.com/Woomin-Wang/TIL/blob/main/OS/process-thread.md)
- [Sync, Async, Blocking, Nonblocking](https://github.com/Woomin-Wang/TIL/blob/main/OS/sync-async-blocking-nonblocking.md)
- [Process synchronization](https://github.com/Woomin-Wang/TIL/blob/main/OS/process-synchronization.md)

<br/>

> 1절. 스레드 생성
> - Thread 클래스 상속
> - Runnable 인터페이스 구현
> - Callable 인터페이스 구현
>   
> 2절. 스레드 생명 주기와 제어
> - 스레드 상태와 전이
> - 스레드 제어 메서드: `sleep()`, `join()`, `interrupt()`, `yield()`
>   
> 3절. 메모리 가시성 문제와 해결
> - 메모리 가시성 개념 
> - volatile 키워드의 역할과 한계
>
> 4절. 동기화 기본
> - synchronized 키워드 
> - LockSupport와 ReentrantLock
> - 공정성과 비공정성
>   
> 5절. 생산자-소비자 문제와 스레드 간 협력
> - 생산자-소비자 패턴 
> - 문제 발생 시나리오 및 해결 방법
>
> 6절. CAS와 원자적 연산
> - CAS(Compare-And-Swap)의 원리
> - CAS vs 동기화: 장단점 비교
> 
> 7절. 동시성 컬렉션
> - java.util.concurrent 패키지 소개
> - 주요 동시성 컬렉션 특징
>
> 8절. 스레드풀과 작업 관리
> - ExecutorService를 통한 스레드풀 구성
> - Future 비동기 작업 결과 처리
> - CompletableFuture의 도입 배경과 활용법

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
<br>

### 스레드 제어 메서드

**1. sleep(long millis)**

`sleep()`은 현재 실행 중인 스레드를 **지정된 시간 동안 일시 정지**시킨다.    
이 시간 동안 해당 스레드는 CPU 자원을 사용하지 않고, 대기 상태로 전환되며, 지정된 시간이 지나면 다시 실행 가능한 상태가 된다.

이때, `sleep()`은 호출한 스레드만 멈추고, 다른 스레드에는 영향을 주지 않는다.

```java
try {
    Thread.sleep(1000); // 1초간 대기
} catch (InterruptedException e) {
    // 인터럽트 발생 시 처리
}
```

> `sleep()`은 대기 중에 다른 스레드로부터 **인터럽트**를 받을 수 있다. 이 경우 InterruptedException이 발생하므로, 예외 처리가 필수적이다. 

<br>
<br>

**2.  join()과 join(long millis)**

`join()`은 **다른 스레드가 종료될 때까지 현재 스레드를 기다리게 하는 메서드**이다.

- `join()`: 대상 스레드가 끝날 때까지 무한정 대기한다.
- `join(long millis)`: 최대 지정한 시간만큼 대기 후, 그 시간이 지나면 실행을 재개한다.

```java
Thread thread1 = new Thread(() -> {
    // 1~50까지 합 계산
});
Thread thread2 = new Thread(() -> {
    // 51~100까지 합 계산
});

thread1.start();
thread2.start();

try {
    thread1.join(); // Thread-1 종료까지 대기
    thread2.join(); // Thread-2 종료까지 대기
} catch (InterruptedException e) {
    // 인터럽트 발생 시 처리
}

// 두 스레드 모두 종료된 후 main 스레드는 결과 출력
```

> `thread1.join()`이 대기 중일 때 thread2가 먼저 종료되면, thread2.join()은 실행되자마자 바로 리턴된다.
>
> 이는 `join()`이 대상 스레드가 이미 종료되었으면 더 이상 기다리지 않고 즉시 반환된다는 것을 의미한다.

<br>
<br>

**3. interrupt()**

`interrupt()`는 스레드를 **즉시 종료시키지 않고, 해당 스레드에게 "작업을 멈춰달라"는 요청**을 보내는 메서드이다.

주로 스레드가 `sleep()`, `wait()`, `join()` 등 대기 상태(TIMED_WAITING, WAITING)에 있을 때, **InterruptedException을 발생시켜 흐름을 제어**한다.

즉, InterruptedException은 본래 **스레드가 블로킹 지점에 있을 때 발생하도록 설계**되어 있다. 

```java
Thread thread = new Thread(() -> {
    try {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("작업 중...");
            Thread.sleep(1000); // 대기 중 인터럽트 발생 가능
        }
    } catch (InterruptedException e) {
        System.out.println("인터럽트 발생 → 종료 준비");
        // 자원 정리
    }
    System.out.println("작업 종료");
});

thread.start();
Thread.sleep(3000); // 메인 스레드 3초 대기
thread.interrupt(); // 작업 중단 요청
```

InterruptedException이 발생하면 **스레드의 인터럽트 상태는 자동으로 false(정상)으로 초기화**된다.

<br>

**isInterrupted() vs interrupted()의 차이**

두 메서드 모두 스레드의 인터럽트 상태를 확인하지만, 중요한 차이가 있다.

- isInterrupted() : 해당 인스턴스 스레드의 **인터럽트 상태를 확인만 하고, 상태를 변경하지 않는다.**
   
- interrupted() : 현재 실행 중인 스레드의 **인터럽트 상태를 확인한 후 상태를 false로 초기화**한다.

<br>

> 💡 while (!Thread.currentThread().isInterrupted()) 조건문을 사용하는 이유
> 
> 이 조건문은 스레드가 `sleep()`과 같은 블로킹 작업 없이 계속해서 CPU를 사용하는 논블로킹(Non-blocking) 작업을 하고 있을 때 특히 중요하다.
> 이러한 경우, `interrupt()`가 호출되더라도 InterruptedException이 자동으로 발생하지 않는다.
> 따라서 `isInterrupted()`를 루프 조건으로 주기적으로 확인하여, 스레드가 인터럽트 신호를 감지하고 스스로 작업을 중단하도록 설계하는 것이 안전하고 견고한 스레드 종료 방식이다.

<br>
<br>

**4. yield()**

현재 실행 중인 스레드가 자신의 CPU 실행 권한을 **일시적으로 포기하고, 같은 우선순위 또는 더 높은 우선순위를 가진 다른 스레드에게 실행 기화를 주도록 요청**하는 메서드이다.

`yield()`는 스레드가 CPU를 오랫동안 독점하는 것을 막고 다른 스레드들도 CPU 시간을 공평하게 할당받도록 돕는 힌트 역할을 하다. 

하지만 이는 어디까지나 요청일 뿐, **실제로 다른 스레드에게 실행 권한이 넘어갈지는 보장하지 않는다.**

```java
Thread thread1 = new Thread(() -> {
    for (int i = 0; i < 3; i++) {
        System.out.println("Thread-1: " + i);
        Thread.yield(); // 다른 스레드에게 양보 요청
    }
});

Thread thread2 = new Thread(() -> {
    for (int i = 0; i < 3; i++) {
        System.out.println("Thread-2: " + i);
        Thread.yield(); // 다른 스레드에게 양보 요청
    }
});

thread1.start();
thread2.start();
```

> `yield()`를 호출해도 스레드는 **RUNNABLE 상태를 유지**하며, `sleep()`처럼 대기 상태로 전환되지 않습니다.  
> 이는 스레드가 언제든 다시 실행될 준비가 되어 있음을 의미하며, 스케줄러가 양보할 다른 스레드를 찾지 못하면 바로 본인 스레드가 다시 실행될 수 있음을 뜻합니다.

<br>
<br>

## 3. 메모리 가시성 문제와 해결

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

## 4. 동기화 기본

앞서 volatile 키워드로 **메모리 가시성 문제**는 해결할 수 있었지만, **원자성 문제**는 여전히 남아 있다. 

여러 스레드가 공유 자원에 동시에 접근할 경우, 데이터의 **일관성**을 보장하려면 **동기화**가 필요하다.

이제부터 배울 동기화 기법들은 volatile이 해결해주는 가시성 문제뿐만 아니라, 원자성 문제까지 함께 해결할 수 있는 방법이다.

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
> 비공정 모드는 성능은 우수하지만, 특정 스레드가 계속 락을 독점할 수 있다.
> 
> 반면, 공정 모드는 기아 현상을 줄이지만 성능 저하가 발생할 수 있다.

<br>
<br>

 
## 5. 생산자-소비자 문제(Producer-Consumer Problem)와 스레드 간 협력

생상자-소비자 문제는 멀티 스레드 환경에서 발생하는 대표적인 **동시성** 문제 중 하나이다.

서로 다른 역할을 하는 두 종류의 스레드, 즉 생산자와 소비자가 **공유된 자원**을 통해 협력하는 상황에서 발생한다.

<br>

### 생산자-소비자 문제

![image](https://github.com/user-attachments/assets/2612f6c5-171a-4f91-9181-db5f523244cf)

- **생산자(Producer)**
    - 데이터를 생성하여 공유 버퍼에 추가하는 역할
    
- **소비자(Consumer)**
    - 공유 버퍼에서 데이터를 꺼내 처리하는 역할

- **공유 버퍼(Shared Buffer/Queue)**
    - 생산자가 생성한 데이터를 소비자가 처리할 때까지 임시로 저장하는 공간

<br>

> 생산자와 소비자는 버퍼의 상태에 따라 작업을 조율해야 하며, **동시에 접근할 때는 동기화를 통해 데이터 무결성을 반드시 보장해야 한다.**

<br>

### 동기화가 없을 경우 발생할 수 있는 문제

멀티 스레드 환경에서 동기화가 제대로 이루어지지 않으면 발생하는 문제들은 다음과 같다.

- **버퍼 오버플로우(Buffer Overflow)**
    - 버퍼가 가득 찼음에도 생산자가 계속 데이터를 추가하려 할 때 발생

- **버퍼 언더플로우(Buffer Underflow)**
    - 버퍼가 비어있는데도 소비자가 데이터를 꺼내려 할 때 발생
   
- **경쟁 조건(Race Condition)**
    - 여러 생산자 또는 소비자가 동시에 버퍼에 접근하면서 데이터가 유실되거나 손상되는 상황

<br>

이런 문제들을 해결하려면 두 가지 측면에서 접근해야 한다.

**1. 경쟁 조건 문제(데이터 무결성 보장)**

- 여러 스레드가 동시에 공유 자원을 변경하려고 할 때 발생한
- 해결방법: 상호 배제를 보장하는 동기화 도구를 사용
    - synchronized 키워드 (가장 기본적인 자바 동기화 도구)
    - java.util.concurrent.locks.Lock 인터페이스의 구현체 (예: ReentrantLock)

<br>

**2. 버퍼 상태에 따른 흐름 제어 문제(스레드 협력 조율)**  

- 버퍼가 가득 찼거나 비어 있을 때처럼, 특정 조건이 만족되지 않은 상황에서 발생
- 단순 경쟁 조건 방지로는 부족하며, 스레드 간 작업 흐름 조율 필요
- 해결책: **조건 동기화(Condition Synchronization)**
  
    - 저수준 방식:
        - `Object.wait()`: 스레드를 대기 상태로 만들고, 락을 해제
        - `Object.notify()` / `Object.notifyAll()`: 대기 중인 스레드를 깨움
        - 반드시 synchronized 블록 내에서 사용


    - 고수준 방식:
        - `java.util.concurrent.locks.Condition` 객체의 `await()`, `signal()`, `signalAll()`
        - Lock 인터페이스(ReentrantLock 등)와 함께 사용
        - `wait()`/`notify()`보다 유연하고 정교한 조건 동기화 제공
 
    - 가장 강력한 해결책:
        - java.util.concurrent.BlockingQueue (하이레벨 동시성 컬렉션)
        - 경쟁 조건 방지와 버퍼 상태에 따른 흐름 제어를 내부에서 모두 처리
        - `put()`, `take()` 메서드 호출만으로 생산자-소비자의 대기 및 알림이 자동으로 처리

<br>
<br>

## 6. CAS와 원자적 연산

멀티스레드 환경에서 여러 스레드가 공유 자원에 동시에 접근하여 데이터를 변경할 때, 예상치 못한 문제가 발생할 수 있다.
이를 해결하기 위해 **동기화 기법**이 필요하며, 대표적으로 **synchronized 키워드**나 **Lock 인터페이스**를 사용한다.

하지만, 이러한 방법들은 스레드를 **블로킹** 시키는 방식으로 동작하여 성능 저하를 야기할 수 있다.

CAS는 이러한 블로킹 방식의 대안으로 등장한 **낙관적(Optimistic) 락킹 기법**이자 **하드웨어 수준에서 지원하는 원자적 연산**이다.

<br>

> **비관적 락 (예: synchronized, ReentrantLock):**
> - 공유 자원에 접근 전 미리 락을 걸어 다른 스레드의 접근을 원천 차단한다.
> - "충돌 가능성이 높으니 미리 잠그자"는 관점이다.
>   
> **낙관적 락 (예: CAS):**
> - 락 없이 일단 접근하여 작업을 수행하고, 변경 시점에 충돌 여부를 확인한다.
> - "충돌은 드물게 일어나니 일단 해보고, 충돌 나면 다시 시도하자"는 관점이다.

<br>

### CAS (Compare-And-Swap)

CAS는 이름 그래도 비교하고(Compare) 교체하는(Swap) 연산을 원자적으로 수행하는 기술이다.  
즉, 이 연산은 중단될 수 없는 단일 작업으로 처리된다.

CAS 연산은 다음 세 가지 피연산자를 사용한다:

- **V(Value)**: 현재 메모리에 있는 값 (읽어온 값)
- **A(Expected Value / Anticipated Value):** 현재 스레드가 예상하는 값 (내가 읽어왔던 값)
- **B(New Value):** 새로 갱신하려는 값

<br>

**CAS의 동작 방식:**

1. 스레드는 메모리 위치 V의 값을 읽어온다. (이것이 A가 된다)
2. 스레드는 이 값을 기반으로 어떤 계산을 수행하여 새로운 값 B를 준비한다.
3. 스레드는 메모리의 위치 V의 현재 값(V)이 자신이 읽어왔떤 값(A)과 **동일한지 비교**한다.
4. 만약 동일하다면 (**비교 성공**), 메모리 위치 V의 값을 새로운 값(B)으로 **원자적으로 교체**한다.
5. 만약 동일하지 않다면 (**비교 실패**), 다른 스레드가 그 사이에 값을 변경했다는 의미이므로, 교체하지 않고 연산에 실패했음을 알린다.
   - 이 경우, 일반적으로 스레드는 연산을 다시 시도한다. (다시 값을 읽고, 비교하고, 교체하는 과정을 반복).

<br>

### 원자적 연산 (Atomic Operations)

원자적 연산이란 더 이상 쪼갤 수 없는, 즉 중간에 다른 스레드에 의해 방해받지 않고 **완전히 수행되거나 전혀 수행되지 않는 작업 단위**를 의미한다.

- **CAS는 대표적인 원자적 연산 중 하나이다.** 비교와 교체라는 두 단계가 **하드웨어 수준에서 단일 명령어로 실행**되도록 설계되었다.
    - 이 덕분에 여러 스레드가 동시에 CAS 연산을 시도해도 문제가 발생하지 않는다.
- 자바에서는 `java.util.concurrent.atomic` 패키지를 통해 이러한 원자적 연산을 지원한다.
    - 이 패키지의 클래스들(예: AtomicInteger, AtomicLong)은 내부적으로 CAS 연산을 활용하여 락 없이도 Thread-safe 한 작업을 제공한다.

<br>

**CAS의 활용 예시 (Java Atomic 클래스)**
```java
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    private AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        // count.incrementAndGet()은 내부적으로 CAS를 사용하여 원자적으로 증가
        // 이는 count++ (원자적이지 않음)와 다르다.
        count.incrementAndGet(); 
    }

    public int getCount() {
        return count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicCounter counter = new AtomicCounter();
        
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        // 락을 사용하지 않았음에도 불구하고, 정확히 2000이 출력
        System.out.println("Final count: " + counter.getCount()); 
    }
}
```

> 동기화 설정(예: synchronized 키워드나 ReentrantLock 인스턴스 사용)을 하지 않았음에도, 멀티스레드 환경에서 동시성 문제가 해결됐다.
>
> AtomicInteger 클래스와 그 내부의 `incrementAndGet()` 메서드가 **CAS 연산을 사용하여 원자적으로 동작**하기 때문이다.

 
<br>

**CAS의 장점과 단점**

CAS는 락 없이 동기화를 시도하는 만큼 여러 이점과 함께 한계점도 지닌다.

**장점:**

- **논블로킹(Non-Blocking):** 스레드가 락을 기다리며 대기하지 않으므로 문맥 교환 오버헤드가 적다.
- **고성능:** 스레드 경쟁이 낮을 때 락 기반 방식보다 뛰어난 성능을 보인다.
- **데드락 회피:** 락을 사용하지 않아 데드락 발생 위험이 없다.

**단점:**

- **ABA 문제:** 값이 A에서 B로 바뀌었다가 다시 A로 돌아온 경우를 감지하지 못할 수 있다.
- **스핀 락 오버헤드:** 스레드 경쟁이 심할 때 CAS 연산이 계속 실패하여 CPU 자원을 불필요하게 소모할 수 있다.
- **복잡성:** 여러 변수를 동시에 원자적으로 다루는 등 복잡한 상황에서는 구현이 까다롭다.

<br>
<br>

## 7. 동시성 컬렉션

자바 기본 컬렉션은 스레드 안전하지 않아, 동시에 접근 시 예외나 데이터 불일치가 발생할 수 있다.

이를 해결하기 위해 자바는 `java.util.concurrent` 패키지를 통해 **동시성 컬렉션**을 제공한다.  

이 컬렉션들은 내부적으로 효율적인 동기화 메커니즘(예: 락 분할, CAS)을 사용하여 **동시성 문제를 효과적으로 예방한다.**

<br>

### 동시성 컬렉션이 필요한 이유

```java
public void add(Object e) {
    elementData[size] = e; // 스레드1, 스레드2가 동시에 접근하여 같은 index에 다른 값을 덮어쓸 수 있음
    sleep(100);             // 동시성 문제를 쉽게 재현하기 위한 지연
    size++;                 // 'size++' 자체도 원자적이지 않아 데이터 손실 가능
}
```

두 스레드가 동시에 `elementData[size] = e;`를 실행하면, **같은 인덱스에 서로 다른 값을 저장하게 되어 한 스레드의 데이터가 덮어써질 수 있다.**  

또한 `size++`도 **원자적 연산이 아니기 때문에**, 동시에 연산이 일어나면 최종 size 값이 누락되어 **실제 추가된 요소 수와 일치하지 않을 수 있다.**

<br>

### 동기화된 컬렉션 (Collections.synchronizedXxx)

이러한 문제를 해결하기 위한 첫 번째 접근법은 **synchronized 키워드를 사용하여 컬렉션 메서드를 동기화**하는 것이다.  

자바는 `Collections.synchronizedList()`, `Collections.synchronizedMap()` 등과 같이 기존 컬렉션에 **동기화 프록시**를 씌우는 방식을 제공한다.  
이 프록시는 원본 컬렉션의 모든 공개 메서드에 synchronized 락을 적용하여 스레드 안정성을 보장한다.

<br>

> 💡 **프록시**(Proxy)
>
> 실제 컬렉션 객체를 감싸는 래퍼(Wrapper) 객체이다.  
> 클라이언트가 프록시 객체의 메서드를 호출하면, 프록시는 내부적으로 동기화 처리를 수행한 후, 실제 컬렉션 객체의 해당 메서드를 호출한다.

<br>

```java
List<String> syncList = Collections.synchronizedList(new ArrayList<>());
Map<String, String> syncMap = Collections.synchronizedMap(new HashMap<>());
```

하지만, `Collections.synchronizedXxx()`를 통한 동기화 방식은 다음과 같은 단점들을 가진다.

- **단일 락으로 인한 동기화 오버헤드**

- **높은 락 경합**

- **단일 스레드 환경에서의 불필요한 비용**

이러한 단점들 때문에, 더 정교하고 효율적인 동기화 메커니즘을 동시에 제공하는 **동시성 컬렉션**의 필요성이 대두되었다.

<br>

### 동시성 컬렉션(java.util.concurrent 패키지)

내부적으로 락 분할, CAS, 논블로킹 알고리즘 등 다양한 동기화 기법을 사용해 성능과 안정성을 모두 고려한다.

- **ConcurrentHashMap**
    - HashMap의 스레드 안전 버전
    - 락 분할 등을 통해 일부 영역만 락을 걸어 병렬성을 향상

- **CopyOnWriteArrayList / CopyOnWriteArraySet**
    - 읽기 작업이 많은 경우에 적합
    - 쓰기 시 기존 배열을 복사하여 수정하고, 참조를 교체 (Copy-On-Write) 
    - 읽기는 락 없이 처리되어 매우 빠르다.
    - 쓰기에는 락을 사용하므로 완전한 논블로킹은 아니다.

- **ConcurrentLinkedQueue / ConcurrentLinkedDeque**
    - 락-프리(Non-blocking) 방식으로 구현된 큐/덱
    - 내부적으로 CAS 연산을 사용하여 경합 없이 동시성 보장

- **ConcurrentSkipListMap / ConcurrentSkipListSet**
    - 정렬된 Map/Set의 스레드 안전 버전
    - 내부적으로 스킵 리스트 구조를 사용
    - TreeMap, TreeSet과 유사하게 요소 순서를 유지


- **BlockingQueue**
    - **ArrayBlockingQueue**
        - **고정된 크기**를 가진 배열 기반의 블로킹 큐
        - 생성 시 정해진 용량 지정, 변경 불가

    - **LinkedBlockingQueue**
        - 연결 리스트 기반 큐
        - 용량 지정 가능(미지정 시 거희 무한: `Integer.MAX_VALUE`)

    - **PriorityBlockingQueue**
        - 우선순위 기반 큐(정렬 기준 필요)
        - 가장 높은 우선순위 요소부터 꺼냄 

    - **SynchronousQueue**
        - 저장 공간 없음
        - 생산자와 소비자가 동시에 만나야 데이터 교환(1:1 핸드오프)     

    - **DelayQueue**
        - 요소 마다 지연 시간 설정
        - 시간이 지난 요소만 꺼낼 수 있음
        - 예약 작업, 지연 실행 등에 사용

<br>

> BlockingQueue는 생상자-소비자 패턴과 같이 **스레드 간의 협업이 필요한 상황**에 주로 사용되는 큐 인터페이스이다.

<br>
<br>

## 8. 스레드풀과 작업 관리

### Future

Future는 **비동기 작업의 결과를 나중에 받아올 수 있도록 하는 인터페이스**이다.

**1. 작업 제출:** 시간이 오래 걸리는 작업을 Callable 객체로 구현한다.  

**2. ExecutorService 사용:** Callable 객체를 ExecutorService에 제출한다.  

**3. Future 객체 반환:** ExecutorService는 Callable 작업을 실행하면서 즉시 Future 객체를 반환한다.  

**4. 결과 확인 및 가져오기:** 개발자는 이 Future 객체를 통해 작업이 완료되었는지 확인하거나, 완료된 결과를 가져온다.  

<br>

> 이러한 메커니즘을 통해, Future는 작업이 완료될 때까지 메인 스레드가 블로킹되는 것을 방지하고, 비동기 작업이 진행되는 동안 다른 작업을 수행할 수 있게 하여 프로그램의 효율성을 높여준다.

<br>

### ExecutorService

ExecutorService는 자바에서 비동기 작업을 실행하고 스레드를 효율적으로 관리하기 위한 인터페이스이다. 

스레드를 직접 생산하고 관리하는 대신, 작업을 **스레드 풀**에 위임하여 다음과 같은 이점을 가진다:

- **성능 향상**: 스레드 생성 및 종료에 드는 높은 비용 감소

- **자원 효율성**: 이미 생성된 스레드를 재사용하여 자원 낭비 막음

- **복잡도 감소**: 개발자가 스레드의 생명주기를 직접 관리할 필요 없이, 작업 자체에만 집중 가능

<br>

> 💡 **Future와 ExecutorService의 관계**
>
> ExecutorService는 비동기 작업을 실행하고 스레드를 관리하는 '작업 관리자'이고, Future는 그 작업 결과를 나중에 받을 수 있는 '결과표' 역할을 한다.  
>
> 즉, ExecutorService가 작업을 실행하면 Future가 결과를 추적해 비동기 처리를 가능하게 한다.
  
<br>

**주요 ExecutorService 생성 메서드**

Executors 유틸리티 클래스를 통해 다양한 용도에 맞는 ExecutorService를 생성할 수 있다.

- `Executors.newFixedThreadPool(int nThreads)`
  - **지정된 수의 고정된 스레드**를 가진 풀을 생성
  - 스레드 수가 예측 가능하여 과도한 자원 사용을 방지할 때 유용
- `Executors.newCachedThreadPool()`
    - **필요에 따라 스레드를 생성**하고, 사용되지 않는 스레드는 일정 시간(기본 60초) 후 **자동으로 종료** 
    - 단기적이고 많은 작업 처리에 적합 
- `Executors.newScheduledThreadPool(int corePoolSize)`
  - 정해진 시간에 맞춰 작업을 실행하거나, 주기적으로 반복되는 작업을 처리
- `Executors.newSingleThreadExecutor()`
  - **단 하나의 스레드**만을 사용하여 작업을 순차적으로 처리
  - 작업의 순서 보장이 중요할 때 사용

<br>

**Future 사용 예시(Java 8 이전)**

```java
// 스레드 풀을 생성
ExecutorService executor = Executors.newCachedThreadPool();

// 오래 걸리는 작업을 제출하고, 결과를 받을 Future 객체를 반환 받음
Future<Double> future = executor.submit(new Callable<Double>() {
    @Override
    public Double call() throws Exception {
        return doSomeLongComputation(); // 오래 걸리는 비동기 작업
    }
});

doSomethingElse(); // 비동기 작업이 실행되는 동안 다른 작업을 수행

try {
    // 최대 1초 동안 비동기 작업 결과를 기다림
    Double result = future.get(1, TimeUnit.SECONDS); // 핵심: 타임아웃 설정
} catch (ExecutionException e) {
    // 작업 중 예외가 발생하면 처리
} catch (InterruptedException e) {
    // 결과를 기다리는 스레드가 중단되면 처리
} catch (TimeoutException e) {
    // 지정된 시간 안에 작업이 완료되지 않으면 처리
}
```

위 코드에서 핵심은 `future.get(1, TimeUnit.SECONDS);`이다.

만약 비동기 작업이 영원히 끝나지 않는 경우, `get()`을 호출한 스레드가 무한히 대기하게 되므로, 이런 상황을 방지하기 위해 **타임 아웃을 설정하는 것이 중요하다.**

<br>

![image](https://github.com/user-attachments/assets/836c0342-6e84-4770-bc45-61333af07ed6)


### Future의 한계점

Java 5에서 도입된 Future 인터페이스는 비동기 프로그래밍의 기반을 마련했지만, 복잡한 비동기 로직 처리에는 한계가 존재했다.

- **비동기 작업 간 조합/연결 어려움**
    - 작업 `A → B` 순으로 처리하려면, A 작업의 결과를 `future.get`으로 꺼낸 후 B 작업을 **직접 호출**해야 한다.
    - `get()`은 **블로킹 호출**이므로, 비동기임에도 불구하고 **스레드가 멈추게 되어 비효율적**이다.
    - 여러 비동기 작업의 결과를 모아 처리할 때도, 각각 `get()`을 호출해야 하므로 코드가 복잡하고 관리가 어렵다.

- **예외 처리 불편함**
    - 비동기 작업 도중 예외가 발생해도 **즉시 알 수 없고**, `get()` 호출 시점에 ExecutionException으로 **래핑되서 던져진다.**
    - 예외 발생 여부를 사전에 감지하기 어려우며, 원인 예외를 **직접 추출**해야 한다.
    - 예외 발생 시 **자동 복구 로직**(fallback 등)을 연결할 수 없고, **직접 try-catch로 처리**해야 한다.

- **후속 작업 연결 어려움**
    - 작업 완료 후 **자동으로 후속 작업을 실행하는 콜백/체이닝 기능이 없다.**
    - 따라서, 흐름 제어를 개발자가 명시적으로 구성해야 하며, 이는 코드의 가독성과 유지보수성을 떨어뜨린다.

<br>

> 위 한계점들은 결과를 얻기 위해 반드시 사용해야 하는 `get()` 메서드가 스레드를 멈춰 세우는 **'블로킹 동시 호출'** 방식으로 동작하기 때문이다.

<br>

### CompletableFuture

CompletableFuture는 Java 8에서 도입된 클래스로, 기존 Future의 한계를 극복하고 보다 유연하고 강력한 비동기 프로그래밍을 지원한다.  

Future와 CompletionStage를 함께 구현하여, **비동기 작업의 흐름 제어, 결과 조합, 예외 처리, 후속 작업 연결 등을 손쉽게 구성할 수 있는 강력한 도구**이다.

<br>






