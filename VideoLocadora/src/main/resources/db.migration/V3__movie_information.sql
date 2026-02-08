CREATE TABLE movie
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    title          VARCHAR(255)        NOT NULL COMMENT 'Título',
    content_rating VARCHAR(50)         NOT NULL COMMENT 'Classificação indicativa',
    internal_code  VARCHAR(255) UNIQUE NOT NULL COMMENT 'Código interno',
    price          DOUBLE PRECISION    NOT NULL COMMENT 'Preço',
    available      BIT(1)              NOT NULL COMMENT 'Disponibilidade no estoque',
    type           VARCHAR(50)         NOT NULL COMMENT 'Tipo',
    genre          VARCHAR(50)         NOT NULL COMMENT 'Gênero'
);

-- Valores permitidos para content_rating (conforme enum ContentRating.dbValue):
-- FREE | PG_10 | PG_13 | AGE_16 | AGE_18

INSERT INTO movie (title, content_rating, internal_code, price, available, type, genre)
VALUES
    -- existentes (padronizados)
    ('Aventura nas Estrelas', 'PG_13', 'AS-001', 19.99, 1, 'BLU_RAY', 'SCIENCE_FICTION'),
    ('Comédia Selvagem', 'AGE_18', 'CS-002', 14.99, 1, 'DVD', 'COMEDY'),
    ('Lágrimas na Chuva', 'PG_10', 'LR-003', 12.99, 1, 'DVD', 'DRAMA'),
    ('Dragões e Magia', 'PG_13', 'DM-004', 24.99, 1, 'BLU_RAY', 'FANTASY'),
    ('Noite dos Mortos Vivos 2', 'AGE_18', 'NM-005', 17.99, 0, 'DVD', 'HORROR'),
    ('Um Amor para Recordar', 'PG_10', 'AR-006', 10.99, 1, 'DVD', 'ROMANCE'),
    ('O Suspeito', 'AGE_18', 'OS-007', 16.99, 1, 'DVD', 'THRILLER'),
    ('Guerra no Espaço', 'PG_13', 'GE-008', 22.99, 1, 'BLU_RAY', 'SCIENCE_FICTION'),
    ('Risadas Soltas', 'PG_10', 'RS-009', 13.99, 1, 'DVD', 'COMEDY'),
    ('Coração Partido', 'PG_10', 'CP-010', 11.99, 1, 'DVD', 'DRAMA'),
    ('A Princesa Guerreira', 'PG_13', 'PG-011', 27.99, 1, 'BLU_RAY', 'FANTASY'),
    ('A Maldição da Mansão', 'AGE_18', 'MM-012', 19.99, 0, 'DVD', 'HORROR'),
    ('Enquanto Você Dormia', 'PG_10', 'ED-013', 9.99, 1, 'DVD', 'ROMANCE'),
    ('Perseguição Implacável', 'AGE_18', 'PI-014', 18.99, 1, 'DVD', 'THRILLER'),
    ('Planeta Proibido', 'PG_13', 'PP-015', 21.99, 1, 'DVD', 'SCIENCE_FICTION'),
    ('Férias Frustradas', 'PG_13', 'FF-016', 15.99, 1, 'DVD', 'COMEDY'),
    ('Segredos do Passado', 'PG_10', 'SP-017', 14.99, 1, 'DVD', 'DRAMA'),
    ('A Lenda do Cavaleiro Verde', 'PG_13', 'LC-018', 26.99, 1, 'BLU_RAY', 'FANTASY'),
    ('O Exorcista Retorna', 'AGE_18', 'ER-019', 20.99, 0, 'DVD', 'HORROR'),
    ('Amor Além do Tempo', 'PG_10', 'AT-020', 10.99, 1, 'DVD', 'ROMANCE'),
    ('O Silêncio dos Inocentes', 'AGE_18', 'SI-021', 17.99, 1, 'DVD', 'THRILLER'),
    ('Invasores de Corpos', 'PG_13', 'IC-022', 23.99, 1, 'DVD', 'SCIENCE_FICTION'),
    ('Um Tira Muito Louco', 'AGE_18', 'TM-023', 16.99, 1, 'DVD', 'ACTION_COMEDY'),
    ('O Resgate do Soldado Ryan', 'AGE_18', 'RS-024', 19.99, 1, 'DVD', 'ACTION_DRAMA'),
    ('Missão Impossível', 'PG_13', 'MI-025', 20.99, 1, 'DVD', 'ACTION_THRILLER'),
    ('Apertem os Cintos... o Piloto Sumiu!', 'PG_13', 'AP-026', 12.99, 1, 'DVD', 'COMEDY_DRAMA'),
    ('Corra que a Polícia Vem Aí', 'PG_13', 'CP-027', 14.99, 1, 'DVD', 'COMEDY_THRILLER'),
    ('Seven - Os Sete Crimes Capitais', 'AGE_18', 'SE-028', 18.99, 1, 'DVD', 'DRAMA_THRILLER'),
    ('Bad Boys', 'AGE_18', 'BB-029', 17.99, 1, 'DVD', 'ACTION_COMEDY_DRAMA'),
    ('Máquina Mortífera', 'AGE_18', 'MM-030', 18.99, 1, 'DVD', 'ACTION_COMEDY_THRILLER'),

    -- massa extra (novos)
    ('A Viagem do Balão Vermelho', 'FREE', 'EX-031', 8.99, 1, 'DVD', 'DRAMA'),
    ('Planeta Jardim', 'FREE', 'EX-032', 9.49, 1, 'VHS', 'DOCUMENTARY'),
    ('Os Pequenos Heróis', 'PG_10', 'EX-033', 11.99, 1, 'DVD', 'ANIMATION'),
    ('Risos em Família', 'PG_10', 'EX-034', 12.49, 1, 'DVD', 'COMEDY'),
    ('O Mistério do Museu', 'PG_13', 'EX-035', 16.99, 1, 'BLU_RAY', 'THRILLER'),
    ('A Floresta Sombria', 'AGE_16', 'EX-036', 17.49, 1, 'DVD', 'HORROR'),
    ('Códigos e Segredos', 'AGE_16', 'EX-037', 18.99, 0, 'DVD', 'THRILLER'),
    ('Operação Relâmpago', 'PG_13', 'EX-038', 19.99, 1, 'BLU_RAY', 'ACTION'),
    ('Cidade das Sombras', 'AGE_18', 'EX-039', 21.49, 1, 'BLU_RAY', 'DRAMA_THRILLER'),
    ('Amor em Paris', 'PG_10', 'EX-040', 10.49, 1, 'DVD', 'ROMANCE'),
    ('Nebulosa X', 'PG_13', 'EX-041', 22.99, 1, 'BLU_RAY', 'SCIENCE_FICTION'),
    ('Batalha Final', 'AGE_18', 'EX-042', 24.99, 0, 'BLU_RAY', 'ACTION_THRILLER'),
    ('Magia do Norte', 'PG_10', 'EX-043', 13.99, 1, 'DVD', 'FANTASY'),
    ('Caçadores do Tesouro', 'PG_13', 'EX-044', 18.49, 1, 'DVD', 'ACTION_COMEDY'),
    ('Diário do Medo', 'AGE_16', 'EX-045', 15.99, 1, 'DVD', 'HORROR'),
    ('No Limite', 'AGE_18', 'EX-046', 19.49, 1, 'DVD', 'ACTION_DRAMA'),
    ('Som e Silêncio', 'PG_10', 'EX-047', 12.99, 1, 'VHS', 'DRAMA'),
    ('A Última Gargalhada', 'AGE_18', 'EX-048', 16.49, 1, 'DVD', 'COMEDY_THRILLER'),
    ('Universo Paralelo', 'PG_13', 'EX-049', 20.49, 1, 'BLU_RAY', 'SCIENCE_FICTION'),
    ('Contos Animados', 'FREE', 'EX-050', 9.99, 1, 'DVD', 'ANIMATION');