# 입출력(Input-Ouput)


### Stream(스트림)

스트림은 **데이터의 흐름**을 추상화한 개념이다. 마치 물이 수도관을 통해 흐르듯, 데이터가 한쪽에서 다른 쪽으로 흘러가는 방식을 나타낸다.  

자바에서 스트림은 단방향으로만 흐른다. 즉, **데이터를 읽는 스트림(Input Stream)과 데이터를 쓰는 스트림(Ouput Stream)이 명확하게 분리**되어있다.  

이는 동시에 같은 파이프를 통해 읽고 쓰는 것을 방지하여 데이터의 일관성에 도움을 준다.

<br>

> 💡 왜 Byte로 처리될까?
> 
> 컴퓨터의 기본적인 데이터 단위가 **바이트**이기 때문이다. 하드웨어 수준에서 CPU, 메모리 등 모든 장치는 데이터를 바이트 단위로 주고받는다.  
> 어떤 종류의 데이터든 결국 컴퓨터 내부에서는 `0`과 `1`의 조합인 바이트 형태로 표현되어야만 저장하거나 전송할 수 있다.

<br>

### 기본 스트림 보조 스트림

자바 스트림은 그 역할에 따라 두 가지로 구분된다:

- **기본 스트림**
  - **단독으로 사용 가능하며**, 파일이나 네트워크 등 실제 데이터 소스나 목적지에 직접 연결되는 스트림이다.
  - 실제 데이터를 물리적으로 읽고 쓰는 역할을 한다.
  - 예: `FileInputStream`, `FileOutputStream`, `FileReader`, `FileWriter`, `ByteArrayInputStream`

- **보조 스트림**
  - **단독으로 사용할 수 없고**, 다른 기본 스트림에 **추가적인 기능**(예: 버퍼링, 인코딩/디코딩)을 제공하는 스트림이다.
  - 예: `BufferedInputStream`, `BufferOutputStream`, `InputStreamReader`, `OutputStreamWriter`, `DataOutputStream`, `DataInputStream`, `PrintStream`

<br>
 
> 💡 **기본 스트림 = 바이트 스트림**
>
> 어떤 경우든 실제 데이터 입출력은 바이트 단위로 이루어지기 때문에, **기본 스트림인 바이트 스트림이 반드시 필요**하며 문자 스트림은 이를 편리하게 처리하는 보조 스트림이다.

<br>

> 💡 인코딩(Encoding)이란?
>
> **인코딩**은 **문자열**을 컴퓨터가 이해하고 처리할 수 있는 **바이트(Byte) 형태로 변환하는 과정**을 말한다.  
> 즉, 인코딩을 하려면 반드시 문자열 데이터가 필요하다.

<br>

## InputStream, OutputStream (최상위 추상 클래스)

InputStream과 OutputStream은 **자바의 모든 바이트 기반 입출력 스트림의 최상위 추상 클래스**이다. 

데이터를 읽거나 쓰는 구체적인 방법보다는, **스트림이 데이터를 다루는 기본적인 규칙과 메서드(예: `read()`, `write()`)를 정의**한다.

또한, 이들을 직접 상속하는 클래스들은 **데이터의 소스**(예: 파일, 메모리 등)나 **목적지**에 따라 구분된다.


![image](https://github.com/user-attachments/assets/88e8b2ea-312d-4217-b181-976b4a5bf2db)


**ByteArrayInputStream / ByteArrayOutputStream**
- 대상: **JVM 메모리 내부**의 `byte` 배열
- 특징:
  - ByteArrayInputStream: `byte` 배열에서 데이터를 **읽는다.**
  - ByteArrayOutputStream: `byte` 배열에 데이터를 **쓴다.**
- 주요 용도: 데이터를 파일이나 네트워크로 보내지 않고, 순전히 **메모리 안에서만** 데이터를 처리하거나 임시로 저장할 때 사용 

<br>

**FileInputStream / FileOutputStream**
- 대상: 컴퓨터의 **파일 시스템**에 있는 파일
- 특징:
  - FileInputStream: 파일에서 데이터를 **읽는다.**
  - FileOutputStream: 파일에 데이터를 **쓴다.**
- 주요 용도: 로컬 컴퓨터의 파일에 데이터를 영구적으로 저장하거나, 파일에서 데이터를 읽어와 프로그램에서 처리할 때 사용

<br>

**SocketInputStream / SocketOutputStream**
- 대상: **네트워크 연결**(소켓)의 다른 쪽 끝
- 특징:
  - SocketInputStream: 네트워크를 통해 수신된 데이터를 **읽는다.**
  - SocketOutputStream: 네트워크를 통해 전송할 데이터를 **쓴다.**
- 주요 용도: 클라이언트와 서버 간의 통신처럼, 네트워크를 통해 데이터를 주고받을 때 사용

<br>

## Buffer I/O Stream (버퍼 입출력 스트림)

버퍼 입출력 스트림은 **내부에 임시 저장 공간인 버퍼(Buffer)를 사용하여 I/O 성능을 최적화하는 보조 스트림**이다.

직접적인 I/O 연산의 빈도를 줄여 효율성을 높인다.

<br>

**보조 스트림 생성**
```java
BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER_SIZE);
```

> 이처럼 보조 스트림은 생성시 FileOutputStream과 같은 **기본 스트림을 인자로 전달**받아, 기본 스트림 위에 버퍼링 기능을 덧씌우는 형태로 작동한다.

<br>

### BufferedOutputStream

데이터를 **내부 버퍼에 모아 한 번에 출력**함으로써 입출력 성능을 크게 향상시키는 보조 스트림이다.

- 생성:
  - FileOutputStream과 같은 **기본 스트림을 감싸서 사용**하며, 생성 시 **버퍼 크기를 명시적으로 지정**할 수 있다.
  - 미지정 시 JVM 기본 버퍼 크기 사용, 보통 8KB

- 동작 원리:
  - `write()` 호출 시, 데이터는 **즉시 파일로 전송되지 않고** BufferedOutputStream의 **내부 버퍼에 임시로 저장**된다.
  - 버퍼에 저장된 데이터는 다음 조건 중 하나가 충족될 때 **한 번에(batch) 실제 파일로 기록**된다.
    - **버퍼가 가득 찼을 때** (자동 `flush()`)
    - `flush()` 메서드가 **명시적으로 호출**되었을 때
    - BufferedOutputStream이 **닫힐 때** (`close()`)
  - 이러한 버퍼링 메커니즘을 통해 잦은 파일 I/O 작업(시스템 콜)을 줄여 성능을 최적화한다.   
 
- 자원 관리:
  - `close()` 메서드를 호출하면:
    - **자동 플러시**: 내부적으로 `flush()`가 **자동으로 호출**되어 버퍼에 남아있던 모든 데이터가 누락 없이 파일에 기록된다.
    - **연쇄적인 자원 정리**: 이후 연결된 FileOutputStream의 `close()` 메서드도 **연쇄적으로 호출**되어 관련 파일 자원이 정리된다.

<br>

> `FileOutputStream`만 닫고 `BufferedOutputStream`을 닫지 않으면, **버퍼에 남아 있던 데이터가 파일에 기록되지 않아 데이터 손실이 발생할 수 있다.**

<br>

### BufferedInputStream

내부 버퍼에 **미리 데이터를 읽어두어**, `read()` 호출 시 버퍼에서 데이터를 제공함으로써 읽기 성능을 최적화하는 보조 스트림이다.

- 동작 원리:
  - `read()` 호출 시, 먼저 BufferedInputStream의 **내부 버퍼를 확인**한다.
  - 요청한 데이터가 버퍼에 있다면, 실제 파일 접근 없이 **버퍼에서 즉시 데이터를 반환**한다.
  - 버퍼에 데이터가 없거나 부족한 경우, BufferedInputStream은 연결된 FileInputStream을 통해 **버퍼 크기만큼의 데이터를 한 번에 미리 읽어와 버퍼를 채운다.**
- 이 과정을 통해 사용자가 1바이트씩 `read()`를 여러 번 호출하더라도, 실제 시스템 콜은 버퍼가 비워질 때 한 번만 발생한다.

<br>

> 💡 **BufferedXxx 성능 저하 이유 (vs. 직접 버퍼링)**
>
> BufferedXxx 클래스들은 **멀티 스레드 환경에서 안전하도록 `synchronized` 처리**되어 있다.  
> 락 처리 과정에서 발생하는 오버헤드 때문에, 싱글 스레드 환경에서는 개발자가 직접 버퍼를 다루는 것보다 성능이 떨어질 수 있다.  
> 하지만, 일반적으로 버퍼링으로 얻는 I/O 횟수 감소 효과가 이 오버헤드를 훨씬 상회하므로, 대부분의 경우 BufferdXxx를 사용하는 것이 권장된다.

<br>
<br>

## Writer, Reader (문자 스트림)

자바는 텍스트 데이터를 효율적으로 처리하기 위해 **바이트 스트림**과 별개로 **문자 스트림**(`Writer`, `Reader`)을 제공한다.  

`Reader`와 `Writer`는 문자 단위(`char`)로 데이터를 읽고 쓰는 기본적인 추상 메서드들을 정의하고 있는 **추상 클래스**이며,    

이를 상속하는 모든 클래스 또한 문자 스트림으로서, 특정 목적에 맞는 문자 데이터 처리 기능을 제공한다.

<br>

### 바이트 ↔ 문자 스트림 변환: OutputStreamWriter & InputStreamReader

OutputStream와 InputStreamReader는 자바 I/O 스트림에서 **문자 스트림**에 속하지만,  

단순히 문자 데이터를 처리하는 것을 넘어, **바이트 스트림과 문자 스트림 사이에서 인코딩/디코딩을 수행하는 변환 스트림 역할을 한다.**

<br>

**`OutputStreamWriter`: 문자를 바이트로 인코딩하여 출력**
- 프로그램에서 작성한 문자를 OutputStream(바이트 스트림)으로 변환하여 출력
- 내부적으로 **지정한 문자 인코딩**(예:UTF-8)에 따라 문자를 바이트로 변환 

```java
// 1. OutputStreamWriter로 파일에 쓰기 (문자 -> 바이트)
try (FileOutputStream fos = new FileOutputStream(fileName);
     OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
    osw.write(content);
    System.out.println("파일에 텍스트가 UTF-8로 기록됨: " + content);
}
```

위 코드는 OutputStreamWriter를 사용하여 메모리상의 **문자열** 데이터를 파일에 **바이트** 형태로 기록하는 과정이다.  

OutputStreamWriter는 **보조 스트림**으로, 생성자 매개변수로 전달된 FileOutputStream 같은 기본 바이트 스트림을 내부에서 사용한다.

`osw.wirte(content);`는 OutputStreamWriter의 `write()`에 String 타입의 `content` 변수 값을 전달하여 실제 파일 쓰기 작업을 수행하며,  

이때 `content`의 문자들은 지정된 UTF-8 인코딩 방식으로 바이트로 변환되어 FileOutputStream을 통해 파일에 저장된다.


<br>
<br>

**`InputStreamWriter`: 바이트를 문자로 디코딩하여 입력**
- InputStream(바이트 스트림)에서 읽은 데이터를 **문자 단위로 처리할 수 있게 변환**
- 바이트를 읽고, **지정된 문자 인코딩**에 따라 적절한 문자로 디코딩
  
```java
// 2. InputStreamReader로 파일 읽기 (바이트 -> 문자)
try (FileInputStream fis = new FileInputStream(fileName);
     InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
    StringBuilder readContent = new StringBuilder();
    int ch;
    while ((ch = isr.read()) != -1) { // 문자 단위로 읽기
        readContent.append((char) ch);
    }
    System.out.println("파일에서 읽은 텍스트 (UTF-8 디코딩): " + readContent);
}
```

이 코드는 InputStreamReader를 사용하여 파일에 저장된 **바이트** 데이터를 다시 **문자열**형태로 읽어오는 과정을 보여준다.  

InputStreamReader 또한 **보조 스트림**으로, FileInputStream과 같은 기본 바이트 스트림에 연결되어 작동한다.  

`isr.read()`는 FileInputStream을 통해 읽어들인 바이트 데이터를 지정된 UTF-8 인코딩 방식에 따라 문자로 디코딩하며,  

이 디코딩된 문자들을 StringBuilder에 추가하여 최종 문자열을 구성한다.


<br>
<br>

### FileWriter, FileReader










<br>
<br>

### 성능 최적화: BufferedWriter & BufferedReader

이 두 클래스는 Writer와 Reader에 **버퍼링 기능**을 추가하여 텍스트 데이터의 입출력 성능을 향상 시키는 보조 스트림이다.

**BufferedWriter: 문자를 버퍼에 모아 한 번에 출력**

**BufferedReader: 문자를 버퍼에 미리 읽어와 효율적으로 입력**

위에 Bufferd I/O Stream 설명이 이미 있음, readLine() 메서드 설명 정도?










### PrintStream (편의성 출력 스트림)

PrintStream은 OutputStream을 상속받는 클래스이다.

자바 프로그램을 실행하면 PrintStream 클래스의 인스턴스인 `System.out`이 **자동으로 생성되어 바로 사용할 수 있도록 준비**된다.

PrintStream은 부모 클래스의 `write()` 메서드뿐만 아니라, `print()`, `println()`, `printf()` 등 다양한 타입의 데이터를 편리하게 출력할 수 있는 메서드들도 제공한다.

<br>

**PrintStream 작동 흐름 요약**

**1. 다양한 타입 데이터 입력**: 개발자가 `print()`나 `println()`에 숫자, 문자열 등 다양한 데이터를 넘겨준다.

**2. 내부에서 문자열로 변환**: PrintStream은 입력받은 데이터를 문자열로 바꿔준다.

**3. 바이트 배열로 인코딩**: 문자열을 `UTF-8` 같은 방식으로 바이트 배열로 변환한다.

**4. write()로 전송**: 변환된 바이트 데이터를 `write()` 메서드를 통해 실제 출력 스트림(예:콘솔)으로 보낸다.

<br>


<br>
