# javaTest

> java에서 사용하는 개념 가운데 궁금한 내용을 테스트합니다.

## foreach vs stream
   - single thread 환경에서는 리스트 길이 약 10만 기준으로 이하 값에서는 foreach가 우세하고 이상 값에서는 stream이 강세를 보인다
   - stream의 경우 ~~병렬처리가 가능해~~ 멀티 스레드 환경에서 시간 측면에서 foreach보다 강세를 보인다
   - 반대로 멀티 스레드 환경에서 ~~병렬처리로 인해~~ stream이 메모리에서는 foreach보다 약세를 보인다
   - stream이 멀티쓰레드에서 병렬처리가 가능하려면 stream.parallel()
    
    🟦: foreach, 🟨: stream
    
    <table>
      <tr>
          <td valign="center"></td>
          <td valign="center">시간 차트</td>
          <td>메모리 차트</td>
      </tr>
      <tr>
          <td valign="center">싱글 스레드, 리스트 길이 변화</td>
          <td valign="center">
              <img width="200px" src="./chartImage/single-thread-리스트 길이-chart-time.png"/>
          </td>
          <td>
              <img width="200px" src="./chartImage/single-thread-리스트 길이-chart-memory.png"/>
          </td>
      </tr>
      <tr>
          <td valign="center">멀티 스레드, 리스트 길이 변화<br/>(동접자 수 10명)</td>
          <td valign="center">
              <img width="200px" src="./chartImage/동접자 수-chart-time.png"/>
          </td>
          <td>
              <img width="200px" src="./chartImage/동접자 수-chart-memory.png"/>
          </td>
      </tr>
      <tr>
          <td valign="center">멀티 스레드, 동시접속자 수 변화<br/>(리스트 길이 1만)</td>
          <td valign="center">
              <img width="200px" src="./chartImage/리스트 길이-chart-time.png"/>
          </td>
          <td>
              <img width="200px" src="./chartImage/리스트 길이-chart-memory.png"/>
          </td>
      </tr>
    </table>

## Enum
- valueOf 사용 예시
- 상수값 ()안에 정의해 getCode로 불러오기 예시

## Copy
- 1차원 배열, 2차원 배열, List에 대해 primitive, Generic에서의 깊은 복사 알아보기
- 1차원 primitive 배열에서는 clone 사용해 깊은 복사 가능함
- 2차원에서는 for-loop를 통해 1차원 수준에서 clone으로 깊은 복사 가능함
- Generic의 경우에는 for-loop를 통해 생성자로 새롭게 메모리 생성해 깊은 복사하거나, Object clone Override로 사용
- List의 경우 생성자로 깊은 복사 가능