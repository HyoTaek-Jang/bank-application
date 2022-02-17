INSERT INTO bank (code, name) SELECT 'D001', '당근은행' FROM DUAL WHERE NOT EXISTS (SELECT * FROM bank WHERE code = 'D001');
INSERT INTO bank (code, name) SELECT 'D002', '페이은행' FROM DUAL WHERE NOT EXISTS (SELECT * FROM bank WHERE code = 'D002');
INSERT INTO bank (code, name) SELECT 'D003', '캐럿은행' FROM DUAL WHERE NOT EXISTS (SELECT * FROM bank WHERE code = 'D003');

INSERT INTO user (name) SELECT '제니' FROM DUAL WHERE NOT EXISTS (SELECT * FROM user WHERE name = '제니');
INSERT INTO user (name) SELECT '신민아' FROM DUAL WHERE NOT EXISTS (SELECT * FROM user WHERE name = '신민아');
INSERT INTO user (name) SELECT '천우희' FROM DUAL WHERE NOT EXISTS (SELECT * FROM user WHERE name = '천우희');
INSERT INTO user (name) SELECT '김당근' FROM DUAL WHERE NOT EXISTS (SELECT * FROM user WHERE name = '김당근');
INSERT INTO user (name) SELECT '장캐럿' FROM DUAL WHERE NOT EXISTS (SELECT * FROM user WHERE name = '장캐럿');

INSERT INTO account(account_id, account_number, bank_id, user_id) SELECT 12345678, 1234567890, 1, 1 FROM DUAL WHERE NOT EXISTS (SELECT * FROM account WHERE account_number = 1234567890);
INSERT INTO account(account_id, account_number, bank_id, user_id) SELECT 12312312, 8756361231, 2, 2 FROM DUAL WHERE NOT EXISTS (SELECT * FROM account WHERE account_number = 8756361231);

INSERT INTO transfer(amount, bank_tx_id, state, to_account_number, from_account_id, to_bank_id) SELECT 15000, 12378945, 2, 0987654321, 2, 2 FROM DUAL WHERE NOT EXISTS (SELECT * FROM transfer WHERE bank_tx_id = 12378945);
