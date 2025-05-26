# 컬렉션 프레임워크
본 내용은 [이것이 자바다 - 신용권], [김영한 중급 2편]을 참고하여 정리하였습니다.

<br/>

> 1절. 컬렉션 프레임워크 소개 
> 
> 2절. List 컬렉션
>
> 3절. Hash 알고리즘
>
> 4절. Set 컬렉션
> 
> 5절. Map 컬렉션
>
> 6절. 검색 기능을 강화시킨 컬렉션
>
> 7절. LIFO와 FIFO
> 
> 8절. 동기화된 컬렉션
> 
> 9절. 병렬 처리를 위한 컬렉션
>
> 10절. 수정할 수 없는 컬렉션

<br/>
<br>

## 1. 컬렉션 프레임워크 소개

![image](https://github.com/user-attachments/assets/e7c8e8e8-08d3-4b16-a1c7-65bb926137c7)

### 개요
- 프로그래밍에서 데이터를 효율적으로 저장하고 처리하는 것은 매우 중요하다.
- 이를 위해 배열이나 직접 구현한 자료구조를 사용할 수도 있지만,
- **복잡한 로직, 자료구조 간의 일관성 부족, 성능 최적화의 어려움** 등의 문제가 발생한다.

<br>

### 사용 이유  
- **공통 인터페이스 제공**
	- 여러 자료구조가 동일한 인터페이스를 구현하기 때문에, 일관된 방법으로 데이터를 추가, 삭제, 탐색할 수 있어 코드 작성과 이해가 쉽다.  
  
- **검증된 다양한 구현체 재사용**
	- 이미 잘 테스트되고 최적화된 `ArrayList`, `HashSet`, `TreeMap` 등의 구현체를 바로 사용할 수 있어, 직접 구현하는 시간과 오류 발생 가능성을 줄인다.  
  
- **구현체 교체 용이성**
	- 인터페이스 기반 설계 덕분에 요구사항에 따라 `ArrayList`를 `LinkedList`로, `HashMap`을 `TreeMap`으로 손쉽게 교체할 수 있어 코드의 유연성과 유지보수성을 높인다.  
  
- **성능과 안정성 최적화**
	- 자바 표준 라이브러리 내부에서 지속적으로 성능 개선과 버그 수정이 이루어져, 신뢰할 수 있는 안정적인 성능을 보장한다.  
  
- **풍부한 유틸리티 메서드 제공**
	- 정렬, 검색, 동기화, 변환 등 자주 필요한 작업을 지원하는 유틸리티 메서드를 제공해 개발 생산성을 높인다.

<br>
<br>

> **자바 컬렉션 프레임워크는 다양한 자료구조를 표준화된 방식으로 제공하여, 데이터를 더 쉽고 일관성 있게 처리할 수 있도록 돕는다.**

<br>

### 컬렉션 프레임워크의 구성 요소

- 인터페이스
	- 컬렉션이 가져야 할 기본 동작을 정의하는 틀
	- 예: List, Set, Queue, Map

- 구현 클래스
	- 인터페이스를 실제 동작하게 만든 클래스와 그 메서드
	- 예: ArrayList의 add(), remove(), HashSet 등

- 알고리즘
	- 데이터 정렬, 검색, 변환 등 특별한 처리 방법
	- Collections와 Arrays 클래스의 정적 메서드 및 일부 구현체의 특수 기능 포함
	- 예: Collections.sort(), Arrays.binarySearch(), TreeSet.subSet()

<br>

> 아래는 해당 인터페이스를 구현한 클래스들이다.

|           **인터페이스 분류**          | **특징**                                                                                   | **대표 구현 클래스**                                                    |
| :-----------------------------: | :--------------------------------------------------------------------------------------- | :--------------------------------------------------------------- |
|     **Collection - List 계열**    | - 요소의 순서 유지<br/>- 중복 저장 가능                                                       | `ArrayList`, `LinkedList`, `Vector`                              |
|     **Collection - Set 계열**     | - 요소의 순서 없음 <br/>-LinkedHashSet은 순서 유지<br/>- 중복 저장 불가                           | `HashSet`, `LinkedHashSet`, `TreeSet`                            |
| **Collection - Queue/Deque 계열** | - Queue: FIFO (선입선출)<br/>- Stack: LIFO (후입선출) <br/>- Deque: 양방향 삽입/삭제           | `LinkedList`, `ArrayDeque`, `PriorityQueue`                      |
|            **Map 계열**           | - Key-Value 쌍으로 저장<br/>- Key는 중복 불가, Value는 중복 가능<br/>- `keySet()`은 Set처럼 작동 | `HashMap`, `LinkedHashMap`, `TreeMap`, `Hashtable`, `Properties` |

<br/>
<br>

## 2. List 컬렉션
### List 특징
- 객체를 순서대로 저장하며, 인덱스로 객체를 관리하는 자료구조이다.
- 객체를 저장하면 자동으로 인덱스가 부여되며, 저장되는 것은 객체 그 자체가 아닌 객체의 참조이다.

<br/>

### ArrayList
- 배열 기반의 List 구현체로, 인덱스를 이용해 객체를 관리한다.
- 일반 배열과 달리, 배열의 크기는 저장 용량(capacity)를 초과하면 자동으로 50% 증가한다.
- 중간 또는 앞쪽에 삽입/삭제 시 뒤쪽 요소를 한 칸씩 이동해야 하므로 성능이 저하된다.
	- `System.arraycopy()`를 활용한 고속 복소라 성능을 개선하였다.
 - 요소들이 메모리상에서 연속적으로 위치하고 있어 CPU 캐시 효율이 높다.

<br/>

### LinkedList
- 이중 연결 리스트 구조로 각 요소가 노드 형태이며, 앞뒤 노드의 참조를 가진다.
- 중간 삽입/삭제 시에는 단순히 링크만 변경하면 되므로 이론적으로 효율적이다.
- 하지만, 삽입/삭제 위치를 찾기 위해 노드를 순차적으로 탐색해야 하므로 접근 속도가 느리다.
	- 인덱스 방식이지만, 실제로는 포인터를 따라 노드를 순차 탐색한다.
- 처음과 끝에서의 삽입/삭제는 포인터로 직접 접근 가능하므로 O(1)의 성능을 보인다.

<br/>

### ArrayList와 LinkedList 성능 비교

| 기능             | 배열 리스트 (ArrayList)     | 연결 리스트 (LinkedList)    |
|------------------|-----------------------------|------------------------------|
| 앞에 추가/삭제   | O(n) - 약 106ms             | O(1) - 약 2ms                |
| 평균 추가/삭제   | O(n) - 약 49ms              | O(n) - 약 1116ms            |
| 뒤에 추가/삭제   | O(1) - 약 1ms               | O(1) - 약 2ms                |
| 인덱스 조회 (`get`) | O(1) - 약 1ms               | O(n) - 평균 약 439ms        |
| 검색 (`indexOf`) | O(n) - 평균 약 104ms       | O(n) - 평균 약 473ms        |


<br>

> ArrayList는 메모리상에 요소들이 연속적으로 저장되기 때문에 CPU 캐시 효율이 높고, 객체 접근 속도도 빨라 대부분의 상황에서 더 우수한 성능을 보인다. 반면, LinkedList는 앞부분에서 자주 삽입/삭제하는 경우 포인터만 조작하기 때문에 효율적이다.

<br>

### Vector
- 내부 구조는 ArrayList와 동일하지만, Vector는 동기화된(synchronized) 메서드로 구성되어 있다.
- 멀티 스레드가 동시에 이 메서드를 실행할 수 없다.
	- 이를 스레드가 안전(Thread Safe)하다라고 말한다.


<br/>
<br>

## 3. Hash 알고리즘
### Hash 특징
- 데이터를 빠르게 저장하고 검색할 수 있다.
- 해시 함수는 입력값을 정수형 해시 코드를 변환하고, 이를 기반으로 해시 인덱스를 계산해 배열의 위치를 결정한다.
- 평균적으로 시간 복잡도 O(1)의 성능을 제공하지만, 해시 충돌이 발생하면 최악의 경우 O(n)까지 성능이 저하될 수 있다.
- **해시 충돌이란, 서로 다른 데이터가 같은 해시 인덱스를 가지는 경우를 말한다.**

<br>

> 같은 해시코드를 가지더라도, 배열의 크기(capacity)에 따라 해시 인덱스는 달라질 수 있다. 보통 해시 인덱스는 `hashCode % capacity`로 계산되기 때문에, capacity가 변경되면 같은 해시코드로도 다른 인덱스를 가질 수 있다.

<br>

### Hash Table 구성 요소

| 구성 요소             | 설명                                            |
| ----------------- | --------------------------------------------- |
| **Hash Function** | 입력 데이터를 정수값(해시 코드)으로 변환하는 함수 (`hashCode()` 등) |
| **Hash Code**     | 해시 함수의 출력값으로, 객체를 대표하는 정수값                    |
| **Hash Index**    | 해시 코드를 기반으로 계산된 실제 저장 위치 (배열 인덱스)             |
| **Buckets**       | 해시 인덱스를 기준으로 데이터를 저장하는 전체 배열                  |
| **Bucket**        | Buckets 배열의 각 요소로, 같은 해시 인덱스를 공유하는 데이터 그룹     |

<br>

### 충돌 해결
- 해시 충돌이 발생하면 같은 해시 인덱스의 Bucket에 선형 자료구조(주로 LinkedList)를 연결해서 데이터를 저장한다. 
- LinkedList는 포인터 조작만으로 삽입과 삭제가 가능해 구조 변경 비용이 낮고, 배열 기반보다 충돌 처리에 더 효율적이다.

<br>
<br>

## 4. Set 컬렉션
### Set 특징
- 중복된 데이터를 저장하지 않으며, 저장 순서를 유지하지 않는다.
- `null` 값은 하나만 허용된다.
- 인덱스로 접근할 수 없고, 대신 Iterator를 통해 요소를 순차 접근한다.

<br>

**Iterator 사용 예시**
```java
Set<String> set = ...;
Iterator<String> iterator = set.iterator();

while(iterator.hasNext()) { // 가져올 객체가 있는지 확인
	String str = iterator.next(); // 객체 반환
	if(str.equals("홍길동")) {
		iterator.remove(); 
        //remove는 Iterator의 메소드이지만, 실제 Set 컬렉션에서 객체가 제거
	}
}
```

<br>

### Set의 성능 문제
- `add()`나 `contains()` 수행 시, **내부적으로 중복 여부 확인을 위해 전체를 순회해야 한다.**
- 이로 인해, 시간 복잡도는 O(n)으로, 데이터가 많아질 수록 성능이 저하된다.

<br>

### HashSet
- Hash 기반으로 구현된 Set 인터페이스의 대표적인 구현체이다.
- 객체를 저장할 때 순서를 고려하지 않고, 동일한 객체는 한 번만 저장된다.
- 저장된 데이터가 전체 배열 크기의 75%를 초과하면 배열 크기를 2배로 늘리고, 모든 요소를 다시 해시 인덱스에 재배치한다.
  
<br>

> 여기서 "동일한 객체"란 단순히 같은 인스턴스를 의미하는 것이 아니라, 객체의 `hashCode()`와 `equals()` 메서드의 결과를 기준으로 논리적 동등성을 판단한 것이다.

<br>

### equals()
- Object 클래스의 `equals()` 기본 구현은 `==`과 동일하게 참조 비교한다.
- String, Integer, List 등 자바 주요 클래스들은 객체의 값을 비교하도록 `equals()`를 오버라이딩한다.
- 직접 만든 클래스도 객체의 논리적 동등성을 판단하려면 `equals()`를 재정의해야 한다.


<br>

> 동일성(Identity): `==` 연산자를 사용해서 두 객체의 참조가 동일한 객체를 가리키고 있는지 확인한다.  
> 동등성(Equality): `equals()` 메서드를 사용해서 두 객체가 논리적으로 같은 값을 가지는지 확인한다.

<br>
<br>

**equals()가 사용되는 시점**
```java
public boolean contains(Object searchValue) {
	int hashIndex = hashIndex(searchValue);
	LinkedList<Object> bucket = buckets[hashIndex];
	for(Object object: bucket) {
		if(object.equals(searchValue) {
		return true;
		}
	}
	return false;
	// return bucket.contains(searchValue);
}
```
- LinkedList의 `contains()` 메소드 내부적으로 `equals()`를 사용해 요소를 비교한다.
- 따라서 요소 타입의 `equals()` 구현 여부가 검색 정확도를 결정한다.

<br>

### hashCode()
- Object 클래스의 기본 `hashCode()`는 객체 참조값을 기반으로 해시코드를 생성한다.
- String, Integer, List 등 주요 클래스들은 값에 따라 같은 해시코드를 반환하도록 `hashCode()`를 오버라이딩한다.
- 직접 만든 클래스도 원하는 기준에 따라 일관된 해시코드를 생성하려면 `hashCode()`를 재정의해야 한다.

<br>

### equals, hashCode의 중요성

**1. equals, HashCode 둘 다 미구현**
- Object 클래스의 기본 구현은 객체의 참조값을 기준으로 `equals()`와 `hashCode()`를 처리한다.
- 이로 인해 객체마다 해시코드가 달라 서로 다른 버킷에 저장된다.
- 간혹 해시 충돌로 같은 버킷에 저장될 수는 있지만, `equals()`가 참조값을 비교하므로 내용이 같아도 중복 저장된다.
- 검색 시, 동일한 내용을 가진 새 객체는 다른 해시코드를 가지므로 다른 버킷을 탐색하게 된다.
- 설령 우연히 같은 버킷을 탐색하더라도 `equals()`가 참조값을 기준으로 비교하므로, 내용이 같아도 검색에 실패한다.

<br>

**2. hashCode만 구현, equals 미구현**
- `hashCode()`가 값 기반으로 구현되어, 같은 내용을 가진 객체는 동일한 버킷을 찾아간다.
- 하지만 `equals()`는 기본 구현 그대로 참조값을 비교하므로, 내용이 같아도 서로 다른 객체로 인식된다.
- 이로 인해 동일한 버킷 안에서도 중복 저장이 발생하고, 검색 시에도 `equals()`에서 참조값이 다르므로 일치하지 않아 검색에 실패한다.

<br>

> 직접 만든 클래스는 `equals()`와 `hashCode()`는 함께 오버라이딩해야 중복 저장을 방지하고 정확한 검색이 가능하다.

<br>

### LinkedHashSet
- HashSet에 이중 연결 리스트를 더해 요소의 삽입 순서를 보장한다.
- 요소는 노드의 형태로 버킷에 저장되고, 이 노드들이 연결 리스트로 순서를 유지한다.
- 정렬 기능은 없으며, 삽입한 순서대로만 요소를 순회할 수 있다.

<br>
<br>

## 5. Map 컬렉션
### Map 특징
- **Map 컬렉션은 키(Key)와 값(Value)의 쌍으로 구성된 Entry 객체를 저장하는 자료구조이다.**
- 키는 중복 저장이 불가능하며, 중복된 키로 값을 저장할 경우 기존 값이 새 값으로 대체된다.
- 값은 중복 저장이 가능하다.
    
<br>

### Map과 Set의 구조적 유사성
- Map의 Key 구조는 Set과 동일하여, HashMap과 HashSet, TreeMap과 TreeSet은 내부 구현이 매우 유사하다. 
- **실제로 HashSet은 내부적으로 HashMap을 이용해 Key만 저장하며, Value는 더미(dummy) 값으로 설정된다.**

<br>

### HashMap
-  HashMap은 Map 인터페이스를 구현한 대표적인 Map 컬렉션이다.
-  Key로 사용할 객체는 `hashCode()`와 `equasl()` 메소드를 반드시 오버라이딩해야 한다.
-  **두 메서드는 Key의 중복 여부를 판단하는 기준이 되며, 검색/저장/삭제 등 모든 연산은 Key를 기준으로 처리된다.**
	-  즉, `hashCode()`는 Value가 아닌 Key에만 사용된다.

<br>

### LinkedHashMap
- HashMap에 이중 연결 리스트를 추가해 키-값 쌍의 삽입 순서를 유지한다.
- 기본 HashMap의 빠른 조회 성능에 순서 보장 기능을 더한 컬렉션이다.

<br>

### Hashtable
- Hashtable은 HashMap과 동일한 내부 구조를 가지고 있다.
- 차이점은 Hashtable은 동기화된(synchronized) 메소드로 구성되어 있다.

<br>

### Properties
- Properties는 Hashtable의 하위 클래스이기 때문에 Hashtable의 모든 특징을 그대로 가진다.
- 차이점은 Hashtable은 키와 값을 다양한 타입으로 지정이 가능한데 비해, Properties는 키와 값을 String 타입으로 제한한 컬렉션이다
- 애플리케이션이나 데이터베이스 등의 정보가 저장된 프로퍼티(*.properties) 파일을 읽을 때 주로 사용된다. 
	- 프로퍼티 파일은 키와 값이 `=` 기호로 연결되어 있는 텍스트 파일이다.

<br>
<br>

## 6. 검색과 순회 기능을 강화시킨 컬렉션

### TreeSet, TreeMap
- TreeSet과 TreeMap은 자동 정렬과 빠른 탐색을 지원하는 컬렉션이다.
- 내부적으로 레드-블랙 트리(균형 이진 탐색 트리)를 사용하며, 주요 연산의 시간 복잡도는 O(log n)이다.
- 요소는 삽입하는 순간부터 정렬된 상태를 유지하며, 정렬 기준은 다음 중 하나를 반드시 제공해야 한다.
  	- **Comparable**: 요소 객체가 Comparable 인터페이스를 구현하여 **기본 정렬 기준**을 제공
 	- **Comparator**: 외부에서 Comparator 객체를 전달하여 **사용자 지정 정렬 기준** 적용
- 요소를 순회할 때는 중위 순회 방식으로 진행되며, 삽입한 순서와 무관하게 정렬된 순서로 탐색된다.
- TreeMap은 TreeSet과 달리, 키와 값을 쌍(Map.Entry<K, V>)으로 저장하며, 키 기준으로 정렬된다.


<br>

> `Arrays.sort()`나 `Collections.sort()`는 호출 시에 정렬을 수행하지만, Tree 계열 컬렉션은 요소를 추가할 때마다 자동으로 정렬 상태를 유지한다. 이때 Comparable 또는 Comparator 중 하나를 반드시 제공해야 하며, 제공하지 않으면 ClassCastException이 발생한다.

<br>

### Compareable
- **객체 자체에서 기본 정렬 기준을 정의할 때 사용한다.**
- `java.lang.Comparable<T>` 인터페이스를 구현하고, `compareTo(T o)` 메서드를 오버라이딩해서 두 객체 간의 비교 기준을 정한다.
- 대표적으로 String, Integer, Double 등 자바 기본 클래스들은 이미 Comparable를 구현하고 있다.
- `Collections.sort(list)`, `list.sort(null)`, `Arrays.sort(array)`, `new TreeSet<>()` 등이 내부적으로 `compareTo()`를 사용한다.

<br>

### Comparator
- **객체 외부에서 정렬 기준을 정의할 때 사용한다.**
- `java.util.Comparator<T>` 인터페이스를 구현하거나 람다식으로 전달한다.
- `compare(T o1, T o2)` 메서드를 오버라이딩하여 두 객체를 비교 기준을 정한다.
- `Collections.sort(list, comparator)`, `list.sort(comparator)`, `new TreeSet<>(comparator)` 등이 내부적으로 `compare()`를 사용한다.

<br>

### Iterable
- `java.lang.Iterable<T>` 인터페이스는 **반복 가능한 객체**임을 나타낸다.
- **이 인터페이스를 구현한 클래스는 `iterator()` 메서드를 제공하며, 이를 통해 요소를 순차적으로 접근할 수 있는 Iterator 객체를 반환한다.**
- 향상된 for문을 사용할 수 있는 기본 조건이 된다.
- 대부분의 자바 컬렉션 클래스(List, Set, Queue 등)는 이 인터페이스를 구현한다.

<br>

**향상된 for문**
```java
for (Element e : myObject) {
    System.out.println(e);
}
```
- 위 코드는 컴파일 시 아래와 같이 변환된다.

<br>
  
```java
Iterator<Element> it = myObject.iterator();  // 우리가 정의한 iterator() 호출됨
while (it.hasNext()) {
    Element e = it.next();
    System.out.println(e);
}

```
- 향상된 for문은 Iterable 인터페이스의 `iterator()` 메서드를 컴파일러가 자동 호출하여, `hasNext()`와 `next()`를 이용한 반복문으로 변환된다.
- 이를 통해 코드가 간결해지며, 다양한 컬렉션을 일관된 방식으로 쉽게 순회할 수 있다.

<br>

### Iterator
- `java.util.Iterator<T>` 인터페이스는 요소를 직접 순회하는 데 사용된다.
- 내부 구조에 상관없이, 안전하고 일관되게 요소를 꺼내고 순회할 수 있다.
- 순회 상태를 내부적으로 관리하며, 순차적으로 요소에 접근한다.

<br>
  
| 메서드                 | 설명                           |
| ------------------- | ---------------------------- |
| `boolean hasNext()` | 아직 순회하지 않은 요소가 있으면 `true` 반환 |
| `T next()`          | 다음 요소를 반환하고 순회 위치를 한 칸 이동    |
| `void remove()`     | 현재 위치의 요소를 제거 (선택적)  |

<br>

>Iterable은 반복을 지원하는 객체임을 명시하며, Iterator를 통해 실제 요소에 순차적으로 접근할 수 있도록 한다. 이를 통해 **다양한 자료구조를 내부 구현과 관계없이 일관되고 통일된 방식으로 순회할 수 있다.**

<br>
<br>

## 7. LIFO와 FIFO 컬렉션

### Stack
- 스택은 LIFO(Last In First Out, 후입선출) 방식의 자료구조이다.
- 자바의 Stack 클래스는 내부적으로 Vector를 기반으로 구현되어 있으나, 현재는 Deque를 사용을 권장한다.

```java
Stack<E> stack = new Stack<E>(); // 비권장

Deque<E> stack = new ArrayDeque<>(); // 권장 
```

<br>

### Queue
- 큐는 FIFO(First In First Out, 선입선출) 방식의 자료구조이다.
- Queue 인터페이스를 구현한 대표적인 클래스는 ArrayDeque, LinkedList이다.
	- 이 중 ArrayDeque가 성능 면에서 더 우수하여 일반적으로 더 권장된다.
   
<br>

### Deque
- 덱은 양방향으로 삽입과 삭제가 가능한 자료구조이다.
- 즉, 스택 또는 큐처럼 사용할 수 있으며, Deque는 Stack과 Queue의 기능을 모두 포함한다.
- 대표적인 구현체는 Queue 인터페이스와 마찬가지로 ArrayDeque, LinkedList이다.

```java
Deque<E> deque = new ArrayDeque<>();

// 스택처럼 사용 (LIFO)
deque.push(element);
deque.pop();

// 큐처럼 사용 (FIFO)
deque.offer(element);
deque.poll();
```

<br>
<br>

## 8. 동기화된 컬렉션
### 개요
- 컬렉션 프레임워크의 대부분의 클래스들은 싱글 스레드 환경에서 사용할 수 있도록 설계됐다.
- 따라서, 여러 스레드가 동시에 컬렉션에 접근한다면 의도하지 않게 요소가 변경될 수 있는 불안전한 상태가 된다.
- Vector와 Hashtable은 동기화되어 있어 스레드 안전하지만, ArrayList·HashSet·HashMap은 동기화되지 않아 멀티스레드 환경에서 안전하지 않다.

<br>

> 따라서 컬렉션 프레임워크는 비동기화된 메소드를 동기화된 메소드로 래핑하는 Collections의 sychronizedXXX()의 메소드를 제공하고 있다.

<br>

**동기화된 컬렉션으로 리턴하기**

|리턴 타입|  메소드(매개 변수) | 설명|
|:-------:|:------|:-------|
|List<T> | synchronizedList(List<T> list) | List를 동기화된 List로 리턴|
|Map<K, V> | synchronizedMap(Map<K, V> m) | Map을 동기화된 Map으로 리턴|
|Set<T> | synchronizedSet(Set<T> s) | Set을 동기화된 Set으로 리턴|

<br>
<br>

## 9. 병렬 처리를 위한 컬렉션
### 개요
- 동기화된 컬렉션은 멀티 스레드 환경에서 스레드 안전성은 보장하지만, 전체 잠금으로 인해 처리 속도가 느릴 수 있다.
- 이를 보완하기 위해 Java는 병렬 처리를 지원하는 컬렉션을 제공한다.
  
<br>

 ### ConcurrentHashMap
- 멀티 스레드 환경에서 안전하며너 빠른 Map 처리를 지원한다.
- 내부적으로 부분 잠금을 사용하여 여러 스레드가 동시에 다른 영역을 수정할 수 있다.
	- 전체 잠금: 1개를 처리할 동안 전체의 10개 요소를 다른 스레드가 처리하지 못하도록 하는 것
 	- 부분 잠금: 처리하는 요소가 포함된 부분만 잠금하고, 나머지 부분은 다른 스레드가 변경할 수 있는 것 

```java
Map<K, V> map = new ConcurrentHashMap<K, V>();
```
  
<br>

 ### ConcurrentLinkedQueue
 - 락-프리(lock-free) 알고리즘 기반의 비동기 큐이다.
 - 여러 스레드가 동시에 접근해도 잠금 없이 안전한 작업 수행이 이루어진다.

```java
Queue<E> queue = new ConcurrentLinkedQueue<E>();
```
 
<br>
<br>

## 10. 수정할 수 없는 컬렉션
### 개요
- 컬렉션을 생성한 후, 요소의 추가/수정/삭제를 막는다.
- 데이터 무결성을 보장하거나, 외부 변경을 방지할 때 유용하다.

<br>

### List.of(), Set.of(), Map.of()
- 불변 컬렉션을 생성하는 정적 메서드이다.
- `null` 요소를 허용하지 않고, 수정 시 UnsupportedOperationException 예외가 발생한다.

```java
List<String> list = List.of("A", "B", "C");
Set<Integer> set = Set.of(1, 2, 3);
Map<String, Integer> map = Map.of("one", 1, "two", 2);
```

<br>

### copyOf() 
- 기존 컬렉션을 기반으로 불변 복사본을 생성한다.
- 원본이 변경되어도 복사본은 영향을 받지 않는다.

```java
List<String> unmodifiableList = List.copyOf(modifiableList);
```

<br>

### Arrays.asList()
- 배열을 List로 변환해준다.
- 요소 수정은 가능하지만, 추가/삭제는 불가능하다.

```java
List<String> list = Arrays.asList("A", "B", "C");
list.set(0, "X");         // O (가능)
list.add("D");            // X (UnsupportedOperationException)
```

<br>

> Arrays.asList()는 완전한 불변 컬렉션은 아니며, 배열과 연결되어 있고 크기 변경이 불가능한 고정 리스트이다.

<br>
<br>

## 마무리
글을 간략하게 작성하다보니 생략된 코드들이 있으니, 자세히 보려면 [이것이 자바다](https://github.com/Woomin-Wang/java/tree/main/src/this_is_java/ch15), [김영한 중급 2편](https://github.com/Woomin-Wang/java/tree/main/src/inflearn_java_middle/collection)를 클릭해주세요.

