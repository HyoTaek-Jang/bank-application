## Index
### [registerAccount](#registerAccount)

## 뱅킹 시스템 API를 연동해서 사용자끼리 계좌이체를 할 수 있는 API Router입니다.

## Base URL

`http://localhost:8080/bank`

## registerAccount

### description

사용자의 계좌를 등록하는 API입니다.

### Request

- Method

    - `POST`

- URI

    - `/register`

- header

- | Field        | Type   | Required  | Description |
  | ------------ | ------ | --------- | ----------- |
  | Authorization  | Integer | True | 현재 토큰을 사용하지 않기에 userId를 넣어주시면 됩니다. |

- body

```json
{
  "code" : "D001",
  "accountNumber" : "1234567890"
}
```

- | Field                | Type   | Description                 |
  | -------------------- | ------ | --------------------------- |
  | code                 | String   | 은행코드 ("D001", "D002", "D003)  |
  | accountNumber | String | 계좌번호 (10자리 숫자 값) |

### Response

- success

```
201
  {
    "message": "계좌 등록을 완료했습니다.",
    "accountId": "71438664"
  }
 ```

- | Field                          | Type    | Description                  |
  | ------------------------------ | ------- | ---------------------------- |
  | message                        | String | 요청 응답에 대한 메시지          |
  | accountId                      | String  | 발급받은 account id           |

- fail
```
400
{
  "message": "계좌번호 길이는 10자리 숫자입니다."
}
```
```
400
{
  "message": "은행코드는 4자리입니다."
}
```
```
409
{
  "message": "이미 등록된 계좌번호입니다."
}
```
```
500
{
  "message": "뱅킹 서비스의 요청이 지연되고 있습니다."
}
```
```
400, 422, 500
{
  "message": {뱅킹 시스템 메시지}
}
```