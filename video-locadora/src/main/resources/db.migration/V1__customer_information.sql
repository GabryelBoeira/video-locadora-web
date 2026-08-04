CREATE TABLE customer
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    name             VARCHAR(255) NULL COMMENT 'Nome',
    cpf              VARCHAR(11) NULL COMMENT 'CPF',
    email            VARCHAR(255) NULL COMMENT 'Email',
    delay_devolution BIT(1) NULL COMMENT 'Tem atraso em alguma devolução',
    enable           BOOLEAN COMMENT 'usuario ativo',
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

INSERT INTO customer (name, cpf, email, delay_devolution, enable) VALUES
('Ana Silva', '12345678901', 'ana.silva@email.com', 0, true),
('Bruno Oliveira', '98765432109', 'bruno.oliveira@email.com', 1, true),
('Carla Souza', '45678901234', 'carla.souza@email.com', 0, true),
('Daniel Pereira', '65432109876', 'daniel.pereira@email.com', 1, true),
('Elisa Santos', '78901234567', 'elisa.santos@email.com', 0, true),
('Fernando Lima', '01234567890', 'fernando.lima@email.com', 1, true),
('Gabriela Rocha', '34567890123', 'gabriela.rocha@email.com', 0, true),
('Henrique Costa', '21098765432', 'henrique.costa@email.com', 1, true),
('Isabela Gomes', '56789012345', 'isabela.gomes@email.com', 0, true),
('João Rodrigues', '89012345678', 'joao.rodrigues@email.com', 1, true);

