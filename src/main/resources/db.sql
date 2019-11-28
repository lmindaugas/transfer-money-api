DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS account;

CREATE TABLE payment (
    payment_id UUID NOT NULL,
    payload VARCHAR(1024) NOT NULL,
    timestamp TIMESTAMP NOT NULL,

    CONSTRAINT pk_t_payment PRIMARY KEY (payment_id)
);

CREATE TABLE account (
    id VARCHAR(20) NOT NULL,
    name VARCHAR(50) NOT NULL,
    balance DECIMAL(19, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    status varchar(20) NOT NULL,

    CONSTRAINT pk_t_account PRIMARY KEY (id)
);

-- INSERT INTO payment VALUES (RANDOM_UUID(), '{\"InstructedAmount\":{\"Amount\":\"3000.00\",\"Currency\":\"GBP\"},\"DebtorAccount\":{\"SchemeName\":\"IBAN\",\"Identification\":\"LT12 1000 0111 0100 1000\",\"Name\":\"Mindaugas L\"},\"CreditorAccount\":{\"SchemeName\":\"IBAN\",\"Identification\":\"LT12 1000 0111 0100 1000\",\"Name\":\"Joe B\"}}', CURRENT_TIMESTAMP);
-- INSERT INTO payment VALUES (RANDOM_UUID(), '{\"InstructedAmount\":{\"Amount\":\"1000.00\",\"Currency\":\"EUR\"},\"DebtorAccount\":{\"SchemeName\":\"IBAN\",\"Identification\":\"LT57 0005 4111 2130 5333\",\"Name\":\"Michael O\"},\"CreditorAccount\":{\"SchemeName\":\"IBAN\",\"Identification\":\"LT57 0005 4111 2130 5333\",\"Name\":\"John D\"}}', CURRENT_TIMESTAMP);

INSERT INTO account VALUES ('DK120000400440116243', 'Thom E', 5000.00, 'EUR', 'DELETED');
INSERT INTO account VALUES ('DE570005411121305333', 'Joe B', 3000.00, 'EUR', 'INACTIVE');
INSERT INTO account VALUES ('GB371205100000309657', 'Michael O', 100.00, 'EUR', 'DELETED');
INSERT INTO account VALUES ('DK470009566600055543', 'John D', 99.00, 'EUR', 'ACTIVE');