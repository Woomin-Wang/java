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

### 기본 스트림과 보조 스트림

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
> 어떤 경우든 실제 데이터 입출력은 바이트 단위로 이루어지기 때문에, **기본 스트림인 바이트 스트림이 반드시 필요**하다.

<br>

> 💡 인코딩(Encoding)이란?
>
> **인코딩**은 **문자열**을 컴퓨터가 이해하고 처리할 수 있는 **바이트(Byte) 형태로 변환하는 과정**을 말한다.  
> 즉, 인코딩을 하려면 반드시 **문자열 데이터가 필요**하다.

<br>
<br>
<br>

## InputStream, OutputStream (최상위 추상 클래스)

`InputStream`과 `OutputStream`은 **자바의 모든 바이트 기반 입출력 스트림의 최상위 추상 클래스**이다. 

데이터를 읽거나 쓰는 구체적인 방법보다는, **스트림이 데이터를 다루는 기본적인 규칙과 메서드(예: `read()`, `write()`)를 정의**한다.

또한, 이들을 직접 상속하는 클래스들은 **데이터의 소스**(예: 파일, 메모리 등)나 **목적지**에 따라 구분된다.


![image](https://github.com/user-attachments/assets/88e8b2ea-312d-4217-b181-976b4a5bf2db)


**ByteArrayInputStream / ByteArrayOutputStream**
- 대상: **JVM 메모리 내부**의 `byte` 배열
- 특징:
  - `ByteArrayInputStream`: `byte` 배열에서 데이터를 **읽는다.**
  - `ByteArrayOutputStream`: `byte` 배열에 데이터를 **쓴다.**
- 주요 용도: 데이터를 파일이나 네트워크로 보내지 않고, 순전히 **메모리 안에서만** 데이터를 처리하거나 임시로 저장할 때 사용 

<br>

**FileInputStream / FileOutputStream**
- 대상: 컴퓨터의 **파일 시스템**에 있는 파일
- 특징:
  - `FileInputStream`: 파일에서 데이터를 **읽는다.**
  - `FileOutputStream`: 파일에 데이터를 **쓴다.**
- 주요 용도: 로컬 컴퓨터의 파일에 데이터를 영구적으로 저장하거나, 파일에서 데이터를 읽어와 프로그램에서 처리할 때 사용

<br>

**SocketInputStream / SocketOutputStream**
- 대상: **네트워크 연결**(소켓)의 다른 쪽 끝
- 특징:
  - `SocketInputStream`: 네트워크를 통해 수신된 데이터를 **읽는다.**
  - `SocketOutputStream`: 네트워크를 통해 전송할 데이터를 **쓴다.**
- 주요 용도: 클라이언트와 서버 간의 통신처럼, 네트워크를 통해 데이터를 주고받을 때 사용

<br>

## Buffer I/O Stream (버퍼 입출력 스트림)

버퍼 입출력 스트림은 **내부에 임시 저장 공간인 버퍼(Buffer)를 사용하여 I/O 성능을 최적화하는 보조 스트림**이다.

직접적인 I/O 연산의 빈도를 줄여 효율성을 높인다.

<br>

**보조 스트림 생성**
```java
FileOutputStream fos = new FileOutputStream(FILE_NAME); // 파일 출력 스트림 생성 (기반 스트림)

BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER_SIZE); // 보조 스트림인 BufferedOutputStream 생성
```

> ✅ 이처럼 보조 스트림은 생성시 `FileOutputStream`과 같은 **기본 스트림을 인자로 전달**받아, 기본 스트림 위에 버퍼링 기능을 덧씌우는 형태로 작동한다.

<br>

### BufferedOutputStream

데이터를 **내부 버퍼에 모아 한 번에 출력**함으로써 입출력 성능을 크게 향상시키는 보조 스트림이다.

- 생성:
  - `FileOutputStream`과 같은 **기본 스트림을 감싸서 사용**하며, 생성 시 **버퍼 크기를 명시적으로 지정**할 수 있다.
  - 미지정 시 JVM 기본 버퍼 크기 사용, 보통 8KB

- 동작 원리:
  - `write()` 호출 시, 데이터는 **즉시 파일로 전송되지 않고** `BufferedOutputStream`의 **내부 버퍼에 임시로 저장**된다.
  - 버퍼에 저장된 데이터는 다음 조건 중 하나가 충족될 때 **한 번에(batch) 실제 파일로 기록**된다.
    - **버퍼가 가득 찼을 때** (자동 `flush()`)
    - `flush()` 메서드가 **명시적으로 호출**되었을 때
    - `BufferedOutputStream`이 **닫힐 때** (`close()`)
  - 이러한 버퍼링 메커니즘을 통해 잦은 파일 I/O 작업(시스템 콜)을 줄여 성능을 최적화한다.   
 
- 자원 관리:
  - `close()` 메서드를 호출하면:
    - **자동 플러시**: 내부적으로 `flush()`가 **자동으로 호출**되어 버퍼에 남아있던 모든 데이터가 누락 없이 파일에 기록된다.
    - **연쇄적인 자원 정리**: 이후 연결된 `FileOutputStream`의 `close()` 메서드도 **연쇄적으로 호출**되어 관련 파일 자원이 정리된다.

<br>

> `FileOutputStream`만 닫고 `BufferedOutputStream`을 닫지 않으면, **버퍼에 남아 있던 데이터가 파일에 기록되지 않아 데이터 손실이 발생할 수 있다.**

<br>

### BufferedInputStream

내부 버퍼에 **미리 데이터를 읽어두어**, `read()` 호출 시 버퍼에서 데이터를 제공함으로써 읽기 성능을 최적화하는 보조 스트림이다.

- 동작 원리:
  - `read()` 호출 시, 먼저 `BufferedInputStream`의 **내부 버퍼를 확인**한다.
  - 요청한 데이터가 버퍼에 있다면, 실제 파일 접근 없이 **버퍼에서 즉시 데이터를 반환**한다.
  - 버퍼에 데이터가 없거나 부족한 경우, `BufferedInputStream`은 연결된 `FileInputStream`을 통해 **버퍼 크기만큼의 데이터를 한 번에 미리 읽어와 버퍼를 채운다.**
- 이 과정을 통해 사용자가 1바이트씩 `read()`를 여러 번 호출하더라도, 실제 시스템 콜은 버퍼가 비워질 때 한 번만 발생한다.

<br>

> 💡 **BufferedXxx 성능 저하 이유 (vs. 직접 버퍼링)**
>
> BufferedXxx 클래스는 내부에 **synchronized로 동기화**되어 있어 멀티 스레드 환경에 안전하지만, 이로 인한 락 오버헤드 때문에 싱글 스레드에서는 직접 버퍼를 다루는 것보다 느릴 수 있다. 그럼에도 불구하고 버퍼링으로 I/O 횟수를 줄이는 이점이 더 크기 때문에 일반적으로 사용하는 것이 권장된다.


<br>
<br>


## Writer, Reader (문자 스트림)

자바는 **텍스트 데이터**를 효율적으로 처리하기 위해 **바이트 스트림**과 별개로 **문자 스트림**(`Writer`, `Reader`)을 제공한다.  

`Reader`와 `Writer`는 문자 단위(`char`)로 데이터를 읽고 쓰는 기본적인 추상 메서드들을 정의하고 있는 **추상 클래스**이며,    

이를 상속하는 모든 클래스 또한 문자 스트림으로서, 특정 목적에 맞는 문자 데이터 처리 기능을 제공한다.

<br>

### OutputStreamWriter, InputStreamReader: 바이트 ↔ 문자 스트림 변환

`OutputStream`와 `InputStreamReader`는 **문자 스트림**으로 단순히 문자 데이터를 처리하는 것을 넘어,  

**바이트 스트림과 문자 스트림 사이에서 인코딩/디코딩을 수행하는 변환 스트림 역할을 한다.**

<br>

**OutputStreamWriter**
- 프로그램에서 작성한 문자를 `OutputStream`(바이트 스트림)으로 변환하여 출력
- 내부적으로 **지정한 문자 인코딩**(예:UTF-8)에 따라 문자를 바이트로 변환 

<br>

**OutputStreamWriter 사용 코드**
```java
try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
    OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
    osw.write(content);
}
```

> `OutputStreamWriter`는 문제 데이터를 바이트로 변환해 출력하는 **변환 스트림**이며, `FileOutputStream`같은 **기반 바이트 스트림을 생성자 매개변수로 받아 내부에서 사용한다.**
>
> 이 구조를 통해 메모리상의 문자열 데이터를 파일에 저장할 수 있으며, 예를 들어 `osw.write(content);`를 호출하면 `content` 문자열이 **UTF-8 방식으로 바이트 인코딩**되어 `FileOutputStream`을 통해 실제 파일에 기록된다.


<br>
<br>


**InputStreamWriter**
- `InputStream`(바이트 스트림)에서 읽은 데이터를 **문자 단위로 처리할 수 있게 변환**
- 바이트를 읽고, **지정된 문자 인코딩**에 따라 적절한 문자로 디코딩

<br>

**InputStreamWriter 사용 코드**
```java
try (FileInputStream fis = new FileInputStream(FILE_NAME);
    InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
    StringBuilder readContent = new StringBuilder();
    int ch;
    while((ch = isr.read()) != -1) { // 문자 단위로 읽기
        readContent.append((char)ch);
    }
    System.out.println("파일에서 읽은 텍스트 (UTF-8 디코딩): " + readContent);
}
```

> `InputStreamReader`는 바이트 데이터를 문자로 변환하는 변환 스트림으로, `FileInputStream`과 같은 기본 바이트 스트림에 연결되어 동작한다.
>
> `isr.read()`는 `FileInputStream`을 통해 읽어들인 바이트 데이터를 지정된 UTF-8 인코딩 방식에 따라 문자로 디코딩하며,  
>
> 이 디코딩된 문자들을 StringBuilder에 추가하여 최종 문자열을 구성한다.


<br>
<br>


### FileWriter, FileReader: 파일 기반 문자 입출력의 간편함

`FileWriter`와 `FileReader`는 `OutputStreamWriter`와 `InputStreamReader`를 상속하며, **인코딩/디코딩 기능을 내장한 문자 스트림**으로서 파일 입출력을 간편하게 처리한다.

<br>

- **FileWriter**
  - `OutputStreamWriter`를 상속하며, **내부적으로 `FileOutputStream`을 생성해 바이트 스트림 처리를 자동으로 수행한다.**
  - 인코딩을 명시하지 않으면 운영체제의 **기본 문자 인코딩**이 사용된다.
  - 파일에 데이터를 추가할지(append) 여부를 지정할 수 있다. 기본 값은 `false`(파일 내용을 덮어씀)

```java
// 파일 내용을 덮어쓰는 경우 (기본 동작)
FileWriter fwOverwrite = new FileWriter(FILE_NAME);
FileWriter fwOverwriteWithEncoding = new FileWriter(FILE_NAME, StandardCharsets.UTF_8);

// 파일 끝에 데이터를 추가하는 경우 (append 모드)
FileWriter fwAppend = new FileWriter(FILE_NAME, true); // true: append 모드 활성화
FileWriter fwAppendWithEncoding = new FileWriter(FILE_NAME, true, StandardCharsets.UTF_8); // true: append 모드 활성화
```

> `FileWriter`를 사용하면 `FileOutputStream`을 직접 생성하고 `OutputStreamWriter`와 연결하는 과정을 생략할 수 있다.

<br>

**FileReader**
- `InputStreamReader`를 상속하며, **FileInputStream 생성을 내부적으로 처리**하여 파일에서 바이트 데이터를 읽어와 문자열로 쉽게 디코딩할 수 있다.

```java
// FileInputStream과 InputStreamReader를 직접 사용하는 경우
FileInputStream fis = new FileInputStream(FILE_NAME);
InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);

// FileReader를 사용하는 경우 (인코딩 지정 가능)
FileReader fr = new FileReader(FILE_NAME, StandardCharsets.UTF_8);
```

<br>
<br>

### BufferedWriter, BufferedReader: 성능 최적화

이 두 클래스는 `Writer`와 `Reader`에 **버퍼링 기능**을 추가하여 텍스트 데이터의 입출력 성능을 향상 시키는 **보조 스트림**이다.

- **BufferedWriter**: 문자를 버퍼에 모아 한 번에 출력

- **BufferedReader**: 문자를 버퍼에 미리 읽어와 효율적으로 입력

<br>

**`readLins()` 메서드**

```java
try (FileReader fr = new FileReader(FILE_NAME);
     BufferedReader br = new BufferedReader(fr)) {

    String line;
    while ((line = br.readLine()) != null) { // 핵심: readLine()으로 한 줄씩 읽고 null 여부 확인
        System.out.println(line);
    }
}
```

> BufferedReader의 가장 유용한 기능 중 하나는 `readLine()`메서드이다.  
>
> 이 메서드는 **텍스트 한 줄 전체를 String 타입으로 반환**해준다.
>
> 줄바꿈 문자는 결과 문자열에 포함되지 않으며, 더 이상 읽을 라인이 없으면 `null`을 반환하므로 파일 내용을 줄 단위로 처리할 때 유용하다.


<br>
<br>

### PrintStream (편의성 출력 스트림)

`PrintStream`은 `OutputStream`을 상속받는 클래스이다.

자바 프로그램을 실행하면 `PrintStream` 클래스의 인스턴스인 `System.out`이 **자동으로 생성되어 바로 사용할 수 있도록 준비**된다.

`PrintStream`은 부모 클래스의 `write()` 메서드뿐만 아니라, `print()`, `println()`, `printf()` 등 다양한 타입의 데이터를 편리하게 출력할 수 있는 메서드들도 제공한다.

<br>

**PrintStream 작동 흐름 요약**

**1. 다양한 타입 데이터 입력**: 개발자가 `print()`나 `println()`에 숫자, 문자열 등 다양한 데이터를 넘겨준다.

**2. 내부에서 문자열로 변환**: `PrintStream`은 입력받은 데이터를 `toString()` 메서드 등을 활용하여 문자열로 바꿔준다.

**3. 바이트 배열로 인코딩**: 변환된 문자열을 `UTF-8` 같은 인코딩 방식으로 바이트 배열로 변환한다.

**4. write()로 전송**: 최종적으로 변환된 바이트 데이터를 `write()` 메서드를 통해 실제 출력 스트림(예:콘솔)으로 보낸다.

<br>
<br>

## Java 데이터 처리의 진화

### DataStream (기본 타입 데이터 스트림)

`DataInputStream`, `DataOutputStream`은 **자바의 기본 자료형 데이터와 문자열을 바이트로 인코딩/디코딩하는 변환 스트림 형태의 보조 스트림**이다.

<br>

> 💡 **DataStream을 사용하는 이유**
>
> `DataOutputStream`과 `DataInputStream`은 **데이터 타입 정보를 그대로 유지하면서 읽고 쓸 수 있게 해준다.**  
>
> 일반적인 문자 스트림은 데이터를 문자열 형태로 저장하고 읽는다.
> 
> 예를 들어, 정수 `123`을 파일에 쓰면 `1`, `2`, `3` 이라는 개별 문자로 저장되고, 읽을 때 문자열로 반환된다.
>
> 때문에 개발자가 `Integer.parseInt()`와 같이 다시 숫자로 변환하는 과정이 필요하다.
> 
> 반면, `DataOutputStream`은 `writeInt(123)`처럼 **자바의 기본 타입을 그대로 저장하고,    
> `DataInputStream`은 `readInt()`로 같은 타입으로 바로 읽어올 수 있다.**  

<br>


### DataStream의 원리: 구분자 없이 데이터를 저장하고 조회하는 비밀

`DataStream`은 어떻게 구분자나 줄바꿈 없이 데이터를 저장하고 조회할 수 있을까, 그 이유는 각 데이터 타입의 특성을 활용하여 정해진 규칙에 따라 바이를 읽고 쓰는 데 있다.

<br>

**문자열 저장 및 조회 원리**

`DataOutputStream`의 `writeUTF()` 메서드를 사용하여 문자열을 저장하고, `DataInputStream`의 `readUTF()` 메서드로 문자열을 조회하는 경우이다.

```java
dos.writeUTF("id1"); // 저장
dis.readUTF();       // 조회 결과: "id1"
```

`readUTF()`가 `id1`처럼 정확히 3글자만 읽어올 수 있는 이유는 다음과 같다.    

`writeUTF()`는 UTF-8 형식으로 문자를 저장할 때, **실제 문자 데이터 앞에 2바이트를 추가로 사용하여 문자열 길이를 저장한다.**    

예를 들어, `id`을 저장할 때는 다음과 같은 형식으로 바이트가 구성된다.  

`[2바이트: 문자열 길이 (3)] + [3바이트: 실제 문자열 데이터 ("id1")`

`readUTF()`는 데이터를 읽을 때, 먼저 앞의 2바이트를 읽어 문자열의 길이를 확인한 후,
그 길이만큼 바이트를 연속해서 읽어 실제 문자열을 복원하는 방식으로 동작한다.

<br>
<br>

**기타 기본 타입의 저장 및 조회 원리**

```java
dos.writeInt(20);
dos.readInt();
```

자바의 `int` 타입은 항상 4바이트를 사용한다.   
`dos.writeInt(20)`은 20이라는 숫자를 4바이트 크기로 파일에 저장하고, `dis.readInt()`는 그 파일에서 정확히 4바이트를 읽어 다시 20으로 복원한다.

<br>
<br>

**DataStream의 이점과 한계**

`DataStream` 덕분에 자바의 기본 타입을 그대로 사용하면서도, 데이터를 저장할 때 별도의 구분자를 사용할 필요가 없어진다.
또한, 모든 데이터를 문자로만 저장할 때보다 저장 용량을 더 효율적으로 최적화할 수 있다.  

예를 들어, 숫자 1,000,000,000 (10억)을 문자열로 저장하면 각 숫자가 문자로 변환되어 총 10바이트가 사용됩니다 (ASCII 인코딩 기준).  
하지만 이것을 자바의 `int` 타입(`writeInt()`)으로 저장하면 고정된 4바이트만 사용됩니다.

하지만, 이렇게 바이트 단위로 직접 데이터를 저장하면, 해당 파일을 텍스트 편집기 등으로 열어서 내용을 확인하거나 수정하는 것이 어렵다는 단점이 있다.  

<br>

DataStream 덕분에 회원 데이터를 편리하게 저장할 수 있는 것은 맞지만,  
회원 객체를 직접 저장하는 것이 아니라 회원의 필드(`id`, `name`, `age`)를 하나하나 꺼내서 각 타입에 맞도록 따로 저장해야한다.

이것은 객체 지향적인 관점에서 보면, 회원 객체 자체를 저장한다기보다는 회원 데이터를 구성하는 각 필드를 분류해서 저장하는 방식이다.

<br>
<br>

### Object Stream: 객체 직렬화와 역직렬화

객체 직렬화(Object Serialization)는 메모리에 있는 **객체 인스턴스를 바이트 스트림으로 변환**하여 파일에 저장하거나 네트워크를 통해 전송할 수 있는 기능이다.

이 과정을 통해 객체의 현재 상태가 보존되며, 나중에 역직렬화(Deserialization)를 통해 원래의 객체 상태를 정확히 복원할 수 있다.

<br>

> 💡 **객체 직렬화를 위한 조건: Serializable 인터페이스**  
> 
> 어떤 클래스의 객체를 직렬화하려면, 해당 클래스는 반드시 `java.io.Serializable` 인터페이스를 구현해야 한다.  
>
> `Serializable` 인터페이스는 메서드를 제공하지 않으며, 이 클래스는 객체의 직렬화가 가능하다는 표시(Marker Interface)역할을 할 뿐이다.  
>
> 만약 `Serializable` 인터페이스를 구현하지 않은 객체를 직렬화하려고 시도하면, `java.io.NotSerializableException`이 발생하게 된다.  

<br>

**직렬화 과정(ObjectOutputStream)**  

- `ObjectOutputStream`은 객체 인스턴스를 받아서 이를 바이트 스트림으로 변환하며,
- 직렬화된 바이트 데이터는 연결된 기본 스트림(예: `FileOutputStream`)을 통해 파일 등 외부로 출력된다. 
- `List<Member>`를 `writeObject()`로 저장하면 리스트 객체 자체는 물론, 그 안에 들어 있는 모든 `Member` 객체들도 함께 직렬화된다.
- 자바의 직렬화는 객체 내부의 구성 요소까지 **재귀적으로 따라가며** 바이트 형태로 저장하기 때문이다.
  
<br>

**역직렬화 과정(ObjectInputStream)**  

- `ObjectInputStream`의 `readObject()` 메서드는 바이트 스트림을 읽고 원래의 객체 인스턴스로 복원한다.
- 반환타입이 `Object`이므로, 실제 사용하려는 타입으로 **명시적 캐스팅**이 필요하다.

<br>

### 객체 직렬화의 한계와 대안, 그리고 데이터베이스의 필요성

자바 객체 직렬화는 편리하지만 여러 문제점 때문에 실제 개발에서는 거의 사용되지 않습니다.  

**1. 객체 직렬화의 한계**  

- **버전 관리 어려움:** 클래스 구조 변경 시 호환성 문제 발생
- **플랫폼 종속성:** 자바 외 다른 시스템과 데이터 교환 어려움
- **성능/크기 문제:** 처리 속도가 느리고 데이터 크기가 큼
- **유연성 부족:** 데이터 형식 제어가 어려움

<br>

**2. 객체 직렬화의 대안들**  

이러한 한계를 극복하기 위해 다양한 데이터 교환 포맷이 등장했다.

- **XML:** 초기 웹 서비스에서 사용되었으나, **복잡하고 무겁다는 단점**
- **JSON:** 가볍고 간결하며, 웹 개발에서 빠르게 확산되어 **현재 웹 환경의 사실상 표준 데이터 교환 포맷**. 사람이 읽기 쉬워 디버깅 용이
- **Protobuf, Avro:** 최고의 성능과 용량 효율이 필요할 때 사용되는 바이너리 기반 포맷. 호환성은 낮고 사람이 읽기는 어려움

> 자바 객체 직렬화는 대부분 사용하지 않으며, **JSON이 표준이다.** 아주 특별한 성능 요구사항이 있다면 Protobuf, Avro를 고려할 수 있다.  

<br>

**3. 파일 기반 저장의 한계와 데이터베이스의 필요성**

JSON 같은 형식으로 데이터를 주고받는 것은 좋지만, 이 데이터를 **파일에 직접 저장하는 방식**은 다음과 같은 큰 문제점이 있다.

- 데이터 무결성/일관성 유지 어려움: 동시 접근 시 충돌 및 손상 위험
- 검색 및 관리 비효율성: 데이터 양이 많아질수록 검색 속도 저하
- 보안 취약성: 접근 제어 및 암호화 구현의 어려움.
- 대규모 백업/복구 어려움

이러한 문제점을 해결하고 대량의 데이터를 효율적으로 저장, 관리, 검색할 수 있도록 발전한 것이 바로 **데이터베이스**이다.

- 데이터베이스는 '인덱스' 등을 통해 데이터를 빠르게 찾고 관리한다.
- 실무에서는 이미지, 영상 같은 대용량 파일을 제외한 **대부분의 구조화된 데이터(회원 정보, 주문 내역 등)는 데이터베이스에 저장**한다.


<br>
<br>


### Java 파일 시스템 API: File, Path, Files 

Java에서 파일과 디렉토리를 다루는 세 가지 주요 API는 `File`, `Path`, `Files`이다.

- **File**: Java 초기부터 존재한 **레거시 API**이다. 파일이나 디렉토리 경로를 추상화하고 기본적인 생성, 삭제, 이름 변경 등을 수행하지만, 기능이 제한적이고 예외 처리가 명확하지 않다.

- **Path**: Java 7에 도입된 **NIO.2.API의 핵심 인터페이스**이다. 파일 시스템 **경로 자체를 더욱 유연하고 강력하게 다루도록 설계**되었으며, 주로 `Files` 클래스와 함께 사용된다.

- **Files**: Java 7에 도입된 **NIO.2.API의 유틸리티 클래스**이다. `Path` 객체를 인자로 받아 파일 및 디렉토리 생성, 복사, 삭제, 읽기, 쓰기 등 **실제 파일 시스템 작업을 수행하는 정적 메서드들을 제공**한다.














