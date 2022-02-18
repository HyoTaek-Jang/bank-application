## 프로젝트 목표
**:  뱅킹 시스템 API를 연동해서 사용자간 계좌이체를 제공하는 API 서버를 구현**

## 시스템 구성도
Java, Spring Boot, MySQL

코드관리 : Git, Github

배포 : docker, docker-compose 사용, DB의 경우, local과 cloud DB를 분리하여 사용하고자 함.

사용자 데이터, 은행 데이터는 data.sql로 삽입 예정 (사용자 5개, 은행 3개, account 1개 초기 세팅)

## 기능정리

1. **뱅킹 시스템 이용에 있어서 응답 대기 시간 설정**
   1. 응답 지연의 경우 연결 중단
      1. 연결을 끊은 경우 중단 기록을 남김
         1. 스케줄러를 통해 중단된 기록에 대해 이체결과 조회 API(GET /transfer/{tx_id})를 호출함
            1. 결과가 나온 상태라면 (응답코드 200)
               1. 데이터 베이스 결과 업데이트
            2. 결과가 나오지 않은 상태라면 (응답코드 400, 500)
               1. 사용자는 결과가 나올 동안 계좌이체 불가
   2. 사용자는 최근 거래 기록을 통해 확인 가능
2. **사용자의 계좌 등록**
   1. 입력값 validation
      1. 등록된 은행코드인가
      2. 계좌번호는 10자리 숫자인가
   2. 기존 등록된 계좌인지 확인
   3. 뱅킹 시스템을 활용하여 계좌등록 (POST /register)
      1. 200 응답 시, DB에 사용자 계좌등록
         1. 등록된 계좌 ID는 유니크 8자리의 숫자
         2. 클라이언트 정상 값 반환
      2. 타 status code 응답 시, 에러 핸들링 및 클라이언트에 반환
3. **등록된 계좌를 통해 타 계좌번호로 이체**
   1. 입력값 validation
      1. 등록 계좌 ID가 8자리 숫자인지
      2. 은행코드가 유효한가
      3. 계좌번호가 10자리 숫자인가
   2. 등록된 계좌 ID가 유효한지 확인
   3. 지연 거래 내역이 존재하는지 확인
   4. tx_id 생성
      1. 가능한 tx_id가 없다면 에러처리 
   5. record 생성 - waiting
   6. 뱅킹 API 통해서 이체 API(POST /transfer) 요청
      1. 응답이 오래 걸림
         3. 클라이언트에 지연 응답 반환
      2. 응답 성공
         1. record 업데이트
         2. 에러 핸들링 혹은 성공코드 클라이언트 반환
4. **계좌 거래 내역 조회**
   1. 입력값 validation
   2. 등록 계좌 ID가 8자리 숫자인지
   3. 등록된 계좌 ID가 유효한지 확인
   4. DB를 통해 거래 내역 조회

---
1. 스케줄러

## 프로젝트 구조

## ERD

![ERD](./images/ERD.png)

TODO : 설명 달기, 기본 삽입 데이터도 표현!, erd 업데이트 

## 프로젝트 실행방법
1. 실제 뱅킹 시스템을 사용하면 HOST와 application.yml에서 isReal true로 바꾸기
2. 로컬 실행을 원하면 application.yml 에서 test, default db 연결 코드 수정해야함. 유저네임 비번, 디비
3. docker-compose up [-D]
4. docker-compose down or ctrl + c
5. 지금은 테스트용 서버이기에 필수적인 요소를 data.sql로 삽입함. 실 서비스 profile에는 제거해서 사용 요망

## API 명세
### [API 명세 확인하기](./src/main/java/com/daagng/test/api/controller/bank/README.md)

## 문제해결전략