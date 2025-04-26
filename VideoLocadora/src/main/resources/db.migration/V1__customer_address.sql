CREATE TABLE address
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    street             VARCHAR(255) NOT NULL COMMENT 'Rua',
    number             INT NULL COMMENT 'Número',
    city               VARCHAR(255) NOT NULL COMMENT 'Cidade',
    state              VARCHAR(255) NOT NULL COMMENT 'Estado',
    country            VARCHAR(255) NOt NULL COMMENT 'País',
    complement         VARCHAR(255) NULL COMMENT 'Complemento',
    neighborhood       VARCHAR(255) NOT NULL COMMENT 'Bairro',
    zip_code           VARCHAR(255) NOT NULL COMMENT 'CEP',
    active          BIT(1) NOT NULL COMMENT 'Endereço ativo',
    primary_address BIT(1) NOT NULL COMMENT 'Endereço principal',
    customer_id        BIGINT NOT NULL COMMENT 'Id do cliente',
    CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE customer
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    name             VARCHAR(255) NULL COMMENT 'Nome',
    cpf              VARCHAR(11) NULL COMMENT 'CPF',
    email            VARCHAR(255) NULL COMMENT 'Email',
    delay_devolution BIT(1) NULL COMMENT 'Tem atraso em alguma devolução',
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

ALTER TABLE address ADD CONSTRAINT FK_ADDRESS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);