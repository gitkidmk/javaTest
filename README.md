# javaTest

> java에서 사용하는 개념 가운데 궁금한 내용을 테스트합니다.

1. foreach vs stream
- stream의 경우 병렬처리가 가능해 멀티 스레드 환경에서 강점을 보인다
- 다만 memory 사용량은 foreach에 비해 안좋다
<div>
    <figure>
        <img width="300px" src="./chartImage/리스트 길이chart-time-1000-to-10000-with-50.0.png"/>
        <figcaption>동시접속자 수 변화시킬 때 시간/메모리 차트</figcaption>
    </figure>
    <figure>
        <img width="300px" src="./chartImage/리스트 길이chart-memory-1000-to-10000-with-50.0.png"/>
        <figcaption>동시접속자 수 변화시킬 때 시간/메모리 차트</figcaption>
    </figure>
    <figure>
        <img width="300px" src="./chartImage/동접자 수chart-time-1-to-100-with-1.0.png"/>
        <figcaption>리스트 길이 변화시킬 때 시간/메모리 차트</figcaption>
    </figure>
    <figure>
        <img width="300px" src="./chartImage/동접자 수chart-memory-1-to-100-with-1.0.png"/>
        <figcaption>리스트 길이 변화시킬 때 시간/메모리 차트</figcaption>
    </figure>
</div>

