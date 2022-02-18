## Index

**[registerAccount](#registerAccount)**

**[transferMoney](#transferMoney)**

**[findTransferHistory](#findTransferHistory)**

## 뱅킹 시스템 API를 연동해서 사용자끼리 계좌이체를 할 수 있는 Controller입니다.

### Base URL

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

- | Field        | Type   | Required  | Description | Example |
  | ------------ | ------ | --------- | ----------- | ------- |
  | Authorization  | Integer | True | 현재 토큰을 사용하지 않기에 userId를 넣어주시면 됩니다. | 1 |

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
400
{
  "message": "존재하지 않는 은행코드입니다."
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

## transferMoney

### description

등록된 계좌를 사용해 다른 사람 계좌번호로 계좌이체를 하는 API입니다.

### Request

- Method

  - `POST`

- URI

  - `/transfer`

- header

- | Field        | Type   | Required  | Description | Example |
  | ------------ | ------ | --------- | ----------- | ------- |
  | Authorization  | Integer | True | 현재 토큰을 사용하지 않기에 userId를 넣어주시면 됩니다. | 1 |

- body

```json
{
  "toCode" : "D001",
  "toAccountNumber" : "0987654321",
  "amount" : 10000,
  "fromAccountId" : "12345678"
}
```

- | Field                | Type   | Description                 |
  | -------------------- | ------ | --------------------------- |
  | toCode                 | String   | 받는 사람의 은행코드 ("D001", "D002", "D003)  |
  | toAccountNumber | String | 받는 사람의 계좌번호 (10자리 숫자 값) |
  | amount | Long | 이체금액 |
  | fromAccountId | String | 보내는 사람의 계좌ID (8자리 숫자 값) |

### Response

- success

```
201
{
    "message": "요청을 성공적으로 수행했습니다.",
    "txId": "00000001",
    "bankTxId": "73711073",
    "result": "SUCCESS"
}
 ```

- | Field                          | Type    | Description                  |
    | ------------------------------ | ------- | ---------------------------- |
  | message                        | String | 요청 응답에 대한 메시지          |
  | txId                      | String  | 이체에 대한 자체 서버의 id           |
  | bankTxId                      | String  | 이체에 대한 뱅킹서비스의 id           |
  | result                      | String  | 이체의 성공여부           |

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
400
{
  "message": "존재하지 않는 은행코드입니다."
}
```
```
409
{
  "message": "현재 진행 중인 계좌이체가 존재합니다."
}
```
```
409
{
  "message": "등록된 계좌의 사용자가 아닙니다."
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

## findTransferHistory

### description

사용자의 모든 계좌에 대해 송금 내역을 조회합니다.

### Request

- Method

  - `GET`

- URI

  - `/history`

- header

- | Field        | Type   | Required  | Description | Example |
  | ------------ | ------ | --------- | ----------- | ------- |
  | Authorization  | Integer | True | 현재 토큰을 사용하지 않기에 userId를 넣어주시면 됩니다. | 1 |

### Response

- success

```
200
{
    "message": "송금내역 조회를 완료했습니다.",
    "historyList": [
        {
            "fromAccountId": "12345678",
            "toAccountNumber": "4123123123",
            "toBank": "D001",
            "state": 0
        },
        {
            "fromAccountId": "12345678",
            "toAccountNumber": "1234561231",
            "toBank": "D001",
            "state": 0
        },
        {
            "fromAccountId": "12345678",
            "toAccountNumber": "7774561231",
            "toBank": "D001",
            "state": 0
        }
    ]
}
 ```

- | Field                          | Type    | Description                  |
  | ------------------------------ | ------- | ---------------------------- |
  | message                        | String | 요청 응답에 대한 메시지          |
  | historyList                      | List  | 송금내역 리스트           |
  | historyList[].fromAccountId      | String  | 보내는 사람의 account id       |
  | historyList[].toAccountNumber      | String  | 받는 사람의 계좌번호       |
  | historyList[].toBank      | String  | 받는 사람의 은행코드       |
  | historyList[].state      | Integer  | 송금에 대한 상태 (0: 완료, 1: 실패, 2: 송금중)       |
