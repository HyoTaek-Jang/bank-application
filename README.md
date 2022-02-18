## 프로젝트 목표
**:  뱅킹 시스템 API를 연동해서 사용자간 계좌이체를 제공하는 API 서버를 구현**

## 프로젝트 실행방법
1. 실제 뱅킹 시스템을 사용하면 HOST와 application.yml에서 isReal true로 바꾸기
2. 로컬 실행을 원하면 application.yml 에서 test, default db 연결 코드 수정해야함. 유저네임 비번, 디비
3. docker-compose up [-D]
4. docker-compose down or ctrl + c
5. 지금은 테스트용 서버이기에 필수적인 요소를 data.sql로 삽입함. 실 서비스 profile에는 제거해서 사용 요망

## ERD

![ERD](./images/ERD.png)

**[자세한 설명 및 기본 삽입 데이터 보기](https://low-cook-e1a.notion.site/726e801e60fd4e2bacea254bea580a72)**

## API 명세
### [API 명세 확인하기](./src/main/java/com/daagng/test/api/controller/bank/README.md)

## 문제해결전략

## 기능정리 및 플로우 설명

1. **뱅킹 시스템 API의 응답 지연을 위한 스케줄러**
   1. 응답 지연의 경우 지연 기록과 함께 해당 요청 클라이언트에 반환
      1. 응답 지연 기록이 존재하는 Account는 송금 불가
      2. 스케줄러를 통해 중단된 기록에 대해 이체결과 조회 API(GET /transfer/{tx_id})를 호출함
         1. 조회 결과를 토대로 송금 기록 업데이트
         2. 이후, 사용자는 송금 가능
2. **사용자의 계좌 등록**
   1. 입력값 유효성 검사
      1. 등록된 은행코드인가
      2. 계좌번호는 10자리 숫자인가
   2. 기존 등록된 계좌인지 확인
   3. 뱅킹 시스템을 활용하여 계좌등록 (POST /register)
      1. 200 응답 시, DB에 사용자 계좌등록
         1. 등록된 계좌 ID는 유니크 8자리의 숫자
         2. 클라이언트 정상 값 반환
      2. 타 status code 응답 시, 에러 핸들링 및 클라이언트에 반환
3. **등록된 계좌를 통해 타 계좌번호로 이체**
   1. 입력값 유효성 검사
      1. 등록 계좌 ID가 8자리 숫자인지
      2. 은행코드가 유효한가
      3. 계좌번호가 10자리 숫자인가
   2. 등록된 계좌 ID가 유효한지 확인
   3. 지연 거래 내역이 존재하는지 확인
   4. tx_id 생성
      1. 가능한 tx_id가 없다면 에러처리
   5. 뱅킹 요청에 앞서 기본 값으로 응답 지연 값을 생성
   6. 뱅킹 API 통해서 이체 API(POST /transfer) 요청
      1. 응답이 오래 걸림
         3. 클라이언트에 지연 응답 반환
      2. 응답 성공
         1. 조회 결과를 토대로 송금 기록 업데이트
         2. 에러 핸들링 혹은 성공코드 클라이언트 반환
4. **송금 내역 조회**
   1. 사용자의 Auth 확인
   2. 사용자의 모든 Account 조회
   3. 각 Account의 송금 내역 조회 및 병합

## 시스템 구성도
Java, Spring Boot, MySQL

코드관리 : Git, Github

배포 : docker, docker-compose