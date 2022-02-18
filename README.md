## 프로젝트 목표
**:  뱅킹 시스템 API를 연동해서 사용자간 계좌이체를 제공하는 API 서버를 구현**

## 프로젝트 실행방법
**프로젝트 기본 안내사항**
   1. 현재 application.yml의 isRealBankingSystem 값이 false이기에 뱅킹 서비스를 사용하지 않습니다. 사용을 원하신다면 true로 변경해주세요.
   2. 현재 버전에 대한 jar 파일을 미리 만들어 docker-jar에 삽입했습니다. 해당 jar를 사용하시거나, 코드의 수정을 하시고 docker-compose를 사용하신다면 jar를 다시 만들어 해당 폴더에 넣어주세요.
   3. 현재 원활한 테스트를 위해 data.sql를 통해 기본 데이터를 삽입했습니다. 사용을 원하신다면 [**기본 삽입 데이터 보기**](https://low-cook-e1a.notion.site/726e801e60fd4e2bacea254bea580a72) 를 참고해주세요.
   4. 현재 data.sql에서 스케줄러 체킹을 위한 insert를 주석 처리했습니다. 스케줄러의 작동 확인을 원하신다면 주석을 해제해주세요. (하단 시연 영상에서 스케줄러 작동이 확인 가능합니다.)

**docker-compose로 구동하기**
   1. docker-jar에 jar가 존재하는지 확인합니다.
   2. docker-compose를 통해 사용하고 있는 포트가 없는지 확인합니다.
   3. docker-compose up [-D] 를 통해 이미지를 빌드하고 컨테이너로 올립니다.
   4. **MySQL이 실행되는 동안 스프링부트에서 오류가 발생할 수 있습니다. MySQL 컨테이너가 정상적으로 올라간 이후, 정상 작동하니 조금만 기다려주세요. (컨테이너 동작 시간 약 30초 소요)**
   5. API 명세를 확인하여 테스팅합니다. (baseurl : localhost:8080)
   6. 테스팅 이후, docker-compose down 나 ctrl + c 명령어를 통해 컨테이너를 종료합니다.

**로컬 환경에서 구동하기**
   1. 로컬 실행을 원하면 application.yml 에서 default와 test profile의 DB 연결 코드를 수정해주세요. (username, password, database url)
   2. TestApplication main 메소드를 통해 구동합니다.

## ERD

![ERD](./images/ERD.png)

**[자세한 설명 및 기본 삽입 데이터 보기](https://low-cook-e1a.notion.site/726e801e60fd4e2bacea254bea580a72)**

## API 명세
### [API 명세 확인하기](./src/main/java/com/daagng/test/api/controller/bank/README.md)

## 문제해결전략
### [문제해결전략 확인하기](https://low-cook-e1a.notion.site/cae0fb2a2db04e748f7f57e4b4a5e5c7)

## 기능정리 및 플로우 설명

1. **인터셉터를 통한 사용자 인증**
   1. 모든 요청에 대해 Authorization 헤더 값을 받음
   2. 해당 값을 DB User에 조회함
      1. 값이 존재하지 않는다면, 에러 반환
      2. 값이 존재한다면 Attribute에 User Entity를 삽입함
   3. 추후 컨트롤러에서 해당 User을 꺼내서 활용함.
2. **뱅킹 시스템 API의 응답 지연을 위한 스케줄러**
   1. 응답 지연의 경우 지연 기록과 함께 해당 요청 클라이언트에 반환
      1. 응답 지연 기록이 존재하는 Account는 송금 불가
      2. 스케줄러를 통해 중단된 기록에 대해 이체결과 조회 API(GET /transfer/{tx_id})를 호출함
         1. 조회 결과를 토대로 송금 기록 업데이트
         2. 이후, 사용자는 송금 가능
3. **사용자의 계좌 등록**
   1. 입력값 유효성 검사
      1. 등록된 은행코드인가
      2. 계좌번호는 10자리 숫자인가
   2. 기존 등록된 계좌인지 확인
   3. 뱅킹 시스템을 활용하여 계좌등록 (POST /register)
      1. 200 응답 시, DB에 사용자 계좌등록
         1. 등록된 계좌 ID는 유니크 8자리의 숫자
         2. 클라이언트 정상 값 반환
      2. 타 status code 응답 시, 에러 핸들링 및 클라이언트에 반환
4. **등록된 계좌를 통해 타 계좌번호로 이체**
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
5. **송금 내역 조회**
   1. 사용자의 Auth 확인
   2. 사용자의 모든 Account 조회
   3. 각 Account의 송금 내역 조회 및 병합

## 시연영상
### [시연영상 보러가기](https://youtu.be/RELwQGhK6RM)

## 시스템 구성도
Java, Spring Boot, MySQL

코드관리 : Git, Github

배포 : docker, docker-compose