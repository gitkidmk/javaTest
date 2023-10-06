# javaTest

> java에서 사용하는 개념 가운데 궁금한 내용을 테스트합니다.

1. foreach vs stream
- stream의 경우 병렬처리가 가능해 멀티 스레드 환경에서 강점을 보인다
- 다만 memory 사용량은 foreach에 비해 안좋다

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
      <td valign="center">동시접속자 수 변화</td>
      <td valign="center">
          <img width="200px" src="./chartImage/리스트 길이-chart-time.png"/>
      </td>
      <td>
          <img width="200px" src="./chartImage/리스트 길이-chart-memory.png"/>
      </td>
  </tr>
  <tr>
      <td valign="center">리스트 길이 변화</td>
      <td valign="center">
          <img width="200px" src="./chartImage/동접자 수-chart-time.png"/>
      </td>
      <td>
          <img width="200px" src="./chartImage/동접자 수-chart-memory.png"/>
      </td>
  </tr>
</table>
