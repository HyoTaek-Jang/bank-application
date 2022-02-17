INSERT INTO bank (code, name) SELECT 'D001', '당근은행' FROM DUAL WHERE NOT EXISTS (SELECT * FROM bank WHERE code = 'D001');
INSERT INTO bank (code, name) SELECT 'D002', '페이은행' FROM DUAL WHERE NOT EXISTS (SELECT * FROM bank WHERE code = 'D002');
INSERT INTO bank (code, name) SELECT 'D003', '캐럿은행' FROM DUAL WHERE NOT EXISTS (SELECT * FROM bank WHERE code = 'D003');

INSERT INTO user (name) SELECT '제니' FROM DUAL WHERE NOT EXISTS (SELECT * FROM user WHERE name = '제니');
INSERT INTO user (name) SELECT '신민아' FROM DUAL WHERE NOT EXISTS (SELECT * FROM user WHERE name = '신민아');
INSERT INTO user (name) SELECT '천우희' FROM DUAL WHERE NOT EXISTS (SELECT * FROM user WHERE name = '천우희');
INSERT INTO user (name) SELECT '김당근' FROM DUAL WHERE NOT EXISTS (SELECT * FROM user WHERE name = '김당근');
INSERT INTO user (name) SELECT '장캐럿' FROM DUAL WHERE NOT EXISTS (SELECT * FROM user WHERE name = '장캐럿');

INSERT INTO account(account_id, account_number, bank_id, user_id) SELECT 12345678, 1234567890, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT * FROM account WHERE account_number = 1234567890);
