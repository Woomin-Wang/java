
<img width="632" alt="image" src="https://github.com/user-attachments/assets/79f20d6c-0ff6-4dc8-ad27-8303f1dc27f1" />  

- https://docs.oracle.com/javase/tutorial/datetime/iso/overview.html

<br>

## LocalDate
- 날짜만 표현할 때 사용

```java
import java.time.LocalDate;

public class LocalDateMain {
    public static void main(String[] args) {
        LocalDate nowDate = LocalDate.now();
        LocalDate ofDate = LocalDate.of(2025,11,11);
        System.out.println("오늘 날짜 = " + nowDate);
        System.out.println("지정 날짜 = " + ofDate);

        //계산(불변)
        ofDate = ofDate.plusDays(10);
        System.out.println("지정 날짜 +10 = " + ofDate);
    }
}
```
- `now()`: 현재 시간을 기준으로 생성
- `of(...)`: 특정 날짜를 기준으로 생성(년, 월, 일 기입 가능)
- `plusDays()`: 특정 일을 더함(다양한 `plusXxx()` 메서드가 존재)

<br>

## LocalTime
- 시간만을 표현할 때 사용
  
```java
import java.time.LocalTime;

public class LocalTimeMain {
    public static void main(String[] args) {
        LocalTime nowTime = LocalTime.now();
        LocalTime ofTime = LocalTime.of(9, 10, 30);

        System.out.println("현재 시간 = " + nowTime);
        System.out.println("지정 시간 = " + ofTime);

        //계산(불변)
        LocalTime ofTimePlus = ofTime.plusSeconds(30);
        System.out.println("지정 시간 + 30s = " + ofTimePlus);
    }
}
```
- `of(...)`: 시, 분, 초, 나노초 입력 가능

<br>

## LocalDateTime
- `LocalDate`와 `LocalTime`을 합한 개념
```java
public class LocalDateTime {
  private final LocalDate date;
  private final LocalTime time;
  ...
}
```

```java
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class LocalDateTimeMain {
    public static void main(String[] args) {
        LocalDateTime nowDt = LocalDateTime.now();
        LocalDateTime ofDt = LocalDateTime.of(2016,8,16,8,10,1);
        System.out.println("현재 날짜시간 = " + nowDt);
        System.out.println("지정 날짜시간 = " + ofDt);

        //날짜와 시간 분리
        LocalDate localDate = ofDt.toLocalDate();
        LocalTime localtime = ofDt.toLocalTime();
        System.out.println("localtime = " + localtime);
        System.out.println("localDate = " + localDate);

        //날짜와 시간 합체
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localtime);
        System.out.println("localDateTime = " + localDateTime);

        //계산(불변)
        LocalDateTime ofDtPlus = ofDt.plusDays(1000);
        System.out.println("지정 날짜시간 + 1000d = " + ofDtPlus);
        LocalDateTime ofDtPlus1Year = ofDt.plusYears(1);
        System.out.println("지정 날짜시간 + 1y = " + ofDtPlus1Year);

        //비교
        System.out.println("현재 날짜시간이 지정 날짜시간보다 이전인가? " + nowDt.isBefore(ofDt));
        System.out.println("현재 날짜시간이 지정 날짜시간보다 이후인가? " + nowDt.isAfter(ofDt));
        System.out.println("현재 날짜와 지정 날짜시간이 같은가? " + nowDt.isEqual(ofDt));
    }
}
```
- `LocalDate`와 `LocalTime`을 `toXxx()` 메서드로 분리 가능

✅ **isEqual() vs equals()**
- **`isEquals()`**: 객체, 타임존이 달라도 시간적으로 같은면 `true` 반환
- **`equals()`**: 객체의 타입, 타임존 등등 내부 데이터의 모든 구성요소가 같아야 `true` 반환

<br> 

## ZoneId
- 자바는 **타임존**을 `ZoneId` 클래스로 제공
- `ZoneId` 내부에는 **일광 절약 시간 관련 정보**, UTC와의 **오프셋 정보** 포함

```java
import java.time.ZoneId;

public class test {
    public static void main(String[] args) {

        for (String availableZoneId : ZoneId.getAvailableZoneIds()) {
            ZoneId zoneId = ZoneId.of(availableZoneId);
            System.out.println(zoneId + " | " + zoneId.getRules());
        }
        
        ZoneId zoneId =ZoneId.systemDefault();
        System.out.println("zoneId.systemDefault() = " + zoneId);
        
        ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
        System.out.println("seoulZoneId = " + seoulZoneId);
    }
}
```
- `ZoneId.systemDefault()`: 시스템이 사용하는 기본 `ZoneId`를 반환 
- `ZoneId.of()`: **타임존**을 직접 제공해서 `ZoneId`를 반환

<br>

### ✅ java.time 패키지의 of() 메서드 정리
- `of()`는 **정적 팩토리 메서드**로, **주어진 파라미터를 기반으로 새로운 불변 객체를 생성하여 반환**

**정적 팩토리 메서드**: `new` 없이 클래스 이름으로 직접 호출해서 원하는 객체를 생성해주는 메서드

 왜 생성자 대신 of()를 쓰는가?
- **객체 재사용**: 같은 값이면 새로 만들지 않고 캐싱된 객체 반환
- **하위 타입 반환 가능**: 다양한 구현제를 유연하게 반환 가능
- **생성 로직 은닉**: 객체 생성 과정을 숨기고 결과만 제공하므로, 내부 구현 변경에 유연하고 유지보수 용이

✔️ 결론: `of()`는 생성자보다 더 유연하고 효율적으로 객체 생성을 제어할 수 있어 유지보수와 확장에 유리




<br>

## ZonedDateTime
- `ZonedDateTime`은 LocalDateTime에 시간대 정보인 **ZoneID**(타임존)가 합쳐진 상태
- 시간대(타임존) 정보를 포함한 날짜와 시간을 표현

```java
public class ZonedDateTime {
    private final LocalDateTime dateTime;
    private final ZoneOffset offset;
    private final ZoneId zone;
}
```

✅ **타임존(Time Zone)**  
- 정의: 지역마다 다른 시간을 정리하기 위한 기준
- 형태: `Asia/Seoul`, `UTC`, `Europe/London` 등
- 특징: 타임존 이름만으로 **해당 지역의 시간 정보**, **오프셋**, **서머타임** 여부 확인 가능

✅ **오프셋(Offset)**  
- 정의: UTC를 기준으로 얼마나 빠르거나 느린지를 나타내는 시간 차이  
- 형태: `+09:00`, `-5:00` 등
- 특징: 
    - 타임존을 통해 오프셋 유추 가능
    - 일광 절약 시간제(DST)가 적용되면 오프셋 변동
    - 오프셋만으로 DST를 알 수 없음
  
<br>

```java
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeMain {
    public static void main(String[] args) {

        ZonedDateTime nowZdt = ZonedDateTime.now();
        System.out.println("nowZdt = " + nowZdt);

        LocalDateTime ldt = LocalDateTime.of(2030,1,1,13,30,50);
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.of("Asia/Seoul"));
        System.out.println("zdt = " + zdt);

        ZonedDateTime zdt2 = ZonedDateTime.of(2030,1,1,13,30, 50,0, ZoneId.of("Asia/Seoul"));
        System.out.println("zdt2 = " + zdt2);

        ZonedDateTime utcZdt = zdt2.withZoneSameInstant(ZoneId.of("UTC"));
        System.out.println("utcZdt = " + utcZdt);
    }
}
```
- `ZonedDateTime.of(LocalDateTime, ZoneId)`: LocalDateTime에 ZoneId를 추가하여 생성 가능
    - 나노초 생략하여 생성하면 자동으로 `0` 설정
    
- `withZoneSameInstant(ZoneID)`: 기존 ZonedDateTime의 **타임존을 변경**
    - 해당 타임존에 맞게 시간도 자동 조정   

<br>

## OffsetDateTime
- `OffsetDateTime`은 `LocalDateTime`에 UTC 오프셋 정보인 ZoneOffset이 합쳐진 상태
- 타임존은 존재하지 않고, UTC로 부터의 시간대 차이인 고정된 **오프셋만 포함**
- ZoneId가 없으므로 **DST 적용 X**

```java
public class OffsetDateTime {
    private final LocalDateTime dateTime;
    private infal ZoneOffset offset;
}
```

<br>

```java
import java.time.*;

public class OffsetDateTimeMain {
    public static void main(String[] args) {
        OffsetDateTime nowOdt = OffsetDateTime.now();
        System.out.println("nowOdt = " + nowOdt);

        LocalDateTime ldt = LocalDateTime.of(2030,1,1,13,30,50);
        System.out.println("ldt = " + ldt);
        OffsetDateTime odt = OffsetDateTime.of(ldt, ZoneOffset.of("+09:00"));
        System.out.println("odt = " + odt);
    }
}
```
<br>

✅ **ZonedDateTime vs OffsetDateTime**
- ZonedDateTime은 지역 기반 시간대를 포함해 복잡한 시간 계산에 적합
- OffsetDateTime은 UTC 기준 오프셋만 고려해 단순 기록 및 저장에 적합

<br>

## Instant

**Epoch 시간**
- 1970-01-01 00:00:00 **UTC 기준**으로부터 경과된 초를 나타내는 시간 표현 방식
- **절대적인** 시간 표현

**Instant 클래스**
- Epoch 시간을 다루기 위한 자바의 대표 클래스
- 내부적으로 **나노초 단위까지 표현 가능**
  

```java
public class Instant {
    private final long seconds;
    private final int nanos;
    ...
}
```

```java
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

public class InstantMain {
    public static void main(String[] args) {
        Instant now = Instant.now();
        System.out.println("now = " + now);
        
        ZonedDateTime zdt = ZonedDateTime.now();
        Instant from1 = Instant.from((zdt));
        System.out.println("from1 = " + from1);

        OffsetDateTime odt = OffsetDateTime.now();
        Instant from2 = Instant.from((odt));
        System.out.println("from2 = " + from2);

        Instant epochStart = Instant.ofEpochSecond(0);
        System.out.println("epochStart = " + epochStart);

        //계산
        Instant later = epochStart.plusSeconds(3600);
        System.out.println("later = " + later);

        //조회
        long laterEpochSecond = later.getEpochSecond();
        System.out.println("laterEpochSecond = " + laterEpochSecond);
    }
}
```
- **`from()`**: 다른 타입의 날짜와 시간을 기준으로 Instant를 생성
    - Instant는 UTC를 기준으로 하기 때문에 시간대 정보가 필요(LocalDateTime은 사용 불가)  

- **`ofEpochSecond()`**: 에포크 시간을 기준으로 Instant 생성
- **`getEpochSecond()`**: 에포크 시간을 기준으로 흐른 초를 반환  

<br>

## Period
- 두 날짜 사이의 간격을 년, 월, 일 단위로 나타냄
```java
public class Period {
    private final int years;
    private final int months;
    private final int days;
```

```java
import java.time.LocalDate;
import java.time.Period;

public class PeriodMain {
    public static void main(String[] args) {
        //생성
        Period period = Period.ofDays(10);
        System.out.println("period = " + period);

        //계산에 사용
        LocalDate currentDate = LocalDate.of(2030,1,1);
        LocalDate plusDate = currentDate.plus(period);
        System.out.println("현재날짜 = " + currentDate);
        System.out.println("더한날짜 = " + plusDate);

        //기간 차이
        LocalDate startDate = LocalDate.of(2023,1,1);
        LocalDate endDate = LocalDate.of(2023,4,2);
        Period between = Period.between(startDate, endDate);
        System.out.println("기간 = " + between.getMonths() + "개월 " + between.getDays() + "일");
    }
}
```
- `of()`: 특정 기간을 지정해서 **Period**를 생성
  - `of(년, 월, 일)`
  - `ofDays()`
  - `ofMonths()`
  - `ofYears()` 

- `plus()`: Period 타입의 매개변수
- `plusDays()`: long 타입의 매개변수

- `Period.between(startDate, endDate)`: 특정 날짜 차이의 값이 Period로 반환


<br>

## Duration
- 두 시간 사이의 간격을 시, 분,초(나노초) 단위로 나타냄
- 내부에서 초를 기반으로 시, 분, 초를 계산해서 사용
```java
public class Duration {
    private final long seconds;
    private final int nanos;
```

```java
import java.time.Duration;
import java.time.LocalTime;

public class test {
    public static void main(String[] args) {
        Duration duration = Duration.ofMinutes(30);
        System.out.println("duration = " + duration);

        LocalTime lt = LocalTime.of(1,0);
        System.out.println("기준 시간 = " + lt);

        //계산에 사용
        LocalTime plusTime = lt.plus(duration);
        System.out.println("더한 시간 = " + plusTime);

        //시간 차이
        LocalTime start = LocalTime.of(9,0);
        LocalTime end = LocalTime.of(10,0);
        Duration between = Duration.between(start, end);
        System.out.println("차이 = " + between.getSeconds() + "초");
        System.out.println("근무 시간 = " + between.toHours() + "시간" + between.toMinutesPart() + "분");
        System.out.println("근무 시간 = " + between.toHours() + "시간" + between.toMinutes() + "분");
    }
}
```
- `of()`: 특정 시간을 지정해서 **Duration** 생성
    - `of(지정)`
    - `ofSeconds()`
    - `ofMinuter()`
    - `ofHours()`
- `toMinutes()`: 전체 Duration을 분 단위로 변환
- `toMinutesPart()`: 분 단위 중에서 **초과된 분**만 추출(125분이면 5분)


<br>

![image](https://github.com/user-attachments/assets/fe76befb-6ece-42dd-9019-675b640789e4)

### TemporalAccessor 인터페이스
- 날짜와 시간을 **읽기** 위한 기본 인터페이스


### Temporal 인터페이스
- `TemporalAccessor`의 하위 인터페이스로, 날짜와 시간을 **조작**(추가, 빼기 등)하기 위한 기능 제공

### TemporalAmount 인터페이스
- 시간의 간격을 나타내며, 날짜와 시간 객체에 적용하여 조정 가능

<br>

  ![image](https://github.com/user-attachments/assets/71b93789-f848-452a-ae4e-7f41cba99b5f)

## 시간의 단위 - TemporalUnit, ChronoUnit
- `TemporalUnit` 인터페이스 날짜와 시간을 측정하는 단위를 나타내며, 주로 사용되는 구현체는 java.time.temporal.ChronoUnit 열거형으로 구현
  
**ChrnoUnit의 주요 메서드**
- between(Temporal, Temporal): 두 Temporal 객체 사이의 시간을 현재 ChronoUnit 단위로 측정하여 반환
- isDateBased(): 현재 ChronoUnit이 날짜 기반 단위인지 여부 반환
- isTimeBased(): ChronoUnit이 시간 기반 단위인지 여부 반환
- isSupportedBy(Temporal): 주어진 Temporal 객체가 현재 ChronoUnit 단위 지원 여부 반환
- getDuration(): 현재 ChronoUnit의 기간을 Duration 객체로 반환

```java
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class ChronoUnitMain {
    public static void main(String[] args) {

        ChronoUnit[] values = ChronoUnit.values();
        for (ChronoUnit value : values) {
            System.out.println("value = " + value);
        }

        System.out.println("ChronoUnit.HOURS = " + ChronoUnit.HOURS);
        System.out.println("HOURS.duration = " + ChronoUnit.HOURS.getDuration().getSeconds());

        System.out.println("ChronoUnit.DAYS = " + ChronoUnit.DAYS);
        System.out.println("DAYS.duration = " + ChronoUnit.DAYS.getDuration().getSeconds());

        //차이 구하기
        LocalTime lt1 = LocalTime.of(1,10,0);
        LocalTime lt2 = LocalTime.of(1, 20, 0);

        long secondsBetween = ChronoUnit.SECONDS.between(lt1, lt2);
        System.out.println("secondsBetween = " + secondsBetween);

        long minutesBetween = ChronoUnit.MINUTES.between(lt1, lt2);
        System.out.println("minutesBetween = " + minutesBetween);
    }
}
```


## 시간 필드 - ChronoField
- ChronoField는 날짜 및 시간을 나타내는 데 사용되는 열거형이다. 이 열거형은 다양한 필드를 통해 **날짜와 시간의 특정 부분**을 나타냄
- TemporalField 인터페이스는 날짜와 시간을 나타내는데 사용. 주로 사용되는 구현체는 java.time.temporal.ChronoField 열거형으로 구현

### 주요 메서드
- getBaseUnit():
    - 반환 타입: TemporalUnit
    - 설명: 필드의 기본 단위를 반환
- getRangeUnit():
    - 반환 타입: TemporalUnit
    - 설명: 필드의 범위 단위를 반환 
- isDateBased():
  - 반환 타입: boolean
  - 설명: 필드가 주로 날짜를 기반으로 하는지 여부
- isTimeBased():
    - 반환 타입: boolean
    - 설명: 필드가 주로 시간을 기반으로 하는지 여부 
- range():
    - 반환 타입: ValueRange
    - 설명: 필드가 가질 수 있는 값의 유효 범위를 ValueRange 객체로 반환 

```java
import java.time.temporal.ChronoField;

public class ChronoFieldMain {
    public static void main(String[] args) {

        for (ChronoField value : ChronoField.values()) {
            System.out.println("value.range() = " + value.range());
        }

        System.out.println("ChronoField.MONTH_OF_YEAR.range() = " + ChronoField.MONTH_OF_YEAR.range());
        System.out.println("ChronoField.DAY_OF_MONTH.range() = " + ChronoField.DAY_OF_MONTH.range());
    }
}
```

## 날짜와 시간 조회하기
```java
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class GetTimeMain {

    public static void main(String[] args) {
        LocalDateTime dt = LocalDateTime.of(2030,1,1,13,30,59);
        System.out.println("YEAR = " + dt.get(ChronoField.MONTH_OF_YEAR));
        System.out.println("MONTH_OF_YEAR = " + dt.get(ChronoField.MONTH_OF_YEAR));
        System.out.println("HOUR_OF_DAY = " + dt.get(ChronoField.HOUR_OF_DAY));
        System.out.println("MINUTE_OF_HOUR = " + dt.get(ChronoField.MINUTE_OF_HOUR));
        System.out.println("SECOND_OF_MINUTE = " + dt.get(ChronoField.SECOND_OF_MINUTE));

        System.out.println("편의 메서드 제공");
        System.out.println("dt.getYear() = " + dt.getYear());
        System.out.println("dt.getMonth() = " + dt.getMonth());
        System.out.println("dt.getMonthValue() = " + dt.getMonthValue());
        System.out.println("dt.getDayOfMonth() = " + dt.getDayOfMonth());
        System.out.println("dt.getMinute() = " + dt.getMinute());
        System.out.println("dt.getSecond() = " + dt.getSecond());
        System.out.println("dt = " + dt);

        System.out.println("편의 메서드에 존재 X");
        System.out.println("ChronoField.MINUTE_OF_DAY = " + dt.get(ChronoField.MINUTE_OF_DAY));
        System.out.println("ChronoField.SECOND_OF_DAY = " + ChronoField.SECOND_OF_DAY);
    }
}
```

```java
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class ChangeTimePlusMain {
    public static void main(String[] args) {
        LocalDateTime dt = LocalDateTime.of(2018,1,1,13,30,59);
        System.out.println("dt = " + dt);

        LocalDateTime plusDt1 = dt.plus(10, ChronoUnit.YEARS);
        System.out.println("plusDt1 = " + plusDt1);

        LocalDateTime plusDt2 = dt.plusYears(10);
        System.out.println("plusDt2 = " + plusDt2);

        Period period = Period.ofYears(10);
        LocalDateTime plusDt3 = dt.plus(period);
        System.out.println("plusDt3 = " + plusDt3);
    }
}
```





