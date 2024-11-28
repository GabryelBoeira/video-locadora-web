#-- ADD CLIENTES, CPFs gerados automaticamente https://www.4devs.com.br/gerador_de_cpf

create table CUSTOMER (
    ID bigint not null,
    NAME varchar(255),
    CPF varchar(255),
    DELAY_DEVOLUTION boolean,
    EMAIL varchar(255),
    primary key (ID)
);

