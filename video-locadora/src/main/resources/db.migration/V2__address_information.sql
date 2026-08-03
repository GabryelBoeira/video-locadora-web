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
    active             BIT(1) NOT NULL COMMENT 'Endereço ativo',
    zip_code           VARCHAR(255) NOT NULL COMMENT 'CEP',
    primary_address    BIT(1) NOT NULL COMMENT 'Endereço principal',
    customer_id        BIGINT NOT NULL COMMENT 'Id do cliente',
    CONSTRAINT pk_address PRIMARY KEY (id)
);

ALTER TABLE address ADD CONSTRAINT FK_ADDRESS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

INSERT INTO address (neighborhood, street, number, city, state, country, zip_code, active, primary_address, customer_id) VALUES
('teste 1', 'Rua A', 123, 'São Paulo', 'SP', 'Brasil', '01000000', 1, 1, 1),
('Teste 2', 'Avenida B', 456, 'Rio de Janeiro', 'RJ', 'Brasil', '20000000', 1, 1, 2),
('teste 3', 'Rua C', 789, 'Belo Horizonte', 'MG', 'Brasil', '30000000', 1, 1, 3),
('teste 4','Avenida D', 101, 'Porto Alegre', 'RS', 'Brasil', '90000000', 1, 1, 4),
('teste 5','Rua E', 202, 'Salvador', 'BA', 'Brasil', '40000000', 1, 1, 5),
('teste 6','Avenida F', 303, 'Recife', 'PE', 'Brasil', '50000000', 1, 1, 6),
('teste 7','Rua G', 404, 'Curitiba', 'PR', 'Brasil', '80000000', 1, 1, 7),
('teste 8','Avenida H', 505, 'Brasília', 'DF', 'Brasil', '70000000', 1, 1, 8),
('teste 9','Rua I', 606, 'Fortaleza', 'CE', 'Brasil', '60000000', 1, 1, 9),
('teste 10','Avenida J', 707, 'Manaus', 'AM', 'Brasil', '69000000', 1, 1, 10);
