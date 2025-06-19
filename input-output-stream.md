# 입출력(Input-Ouput)

### Stream

<br>

> 💡 왜 Byte로 처리될까요?
> 
> 컴퓨터의 기본적인 데이터 단위가 바이트이기 때문이다. 하드웨어 수준에서 CPU, 메모리 등 모든 장치는 데이터를 바이트 단위로 주고받는다.  
> 어떤 종류의 데이터든 결국 컴퓨터 내부에서는 `0`과 `1`의 조합인 바이트 형태로 표현되어야만 저장하거나 전송할 수 있다.

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
