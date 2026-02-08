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
-- FREE | PG | PG-13 | 16+ | 18+

INSERT INTO movie (title, content_rating, internal_code, price, available, type, genre)
VALUES
    -- existentes (padronizados)
    ('Aventura nas Estrelas', 'PG-13', 'AS-001', 19.99, 1, 'BLU_RAY', 'SCIENCE_FICTION'),
    ('Comédia Selvagem', '18+', 'CS-002', 14.99, 1, 'DVD', 'COMEDY'),
    ('Lágrimas na Chuva', 'PG', 'LR-003', 12.99, 1, 'DVD', 'DRAMA'),
    ('Dragões e Magia', 'PG-13', 'DM-004', 24.99, 1, 'BLU_RAY', 'FANTASY'),
    ('Noite dos Mortos Vivos 2', '18+', 'NM-005', 17.99, 0, 'DVD', 'HORROR'),
    ('Um Amor para Recordar', 'PG', 'AR-006', 10.99, 1, 'DVD', 'ROMANCE'),
    ('O Suspeito', '18+', 'OS-007', 16.99, 1, 'DVD', 'THRILLER'),
    ('Guerra no Espaço', 'PG-13', 'GE-008', 22.99, 1, 'BLU_RAY', 'SCIENCE_FICTION'),
    ('Risadas Soltas', 'PG', 'RS-009', 13.99, 1, 'DVD', 'COMEDY'),
    ('Coração Partido', 'PG', 'CP-010', 11.99, 1, 'DVD', 'DRAMA'),
    ('A Princesa Guerreira', 'PG-13', 'PG-011', 27.99, 1, 'BLU_RAY', 'FANTASY'),
    ('A Maldição da Mansão', '18+', 'MM-012', 19.99, 0, 'DVD', 'HORROR'),
    ('Enquanto Você Dormia', 'PG', 'ED-013', 9.99, 1, 'DVD', 'ROMANCE'),
    ('Perseguição Implacável', '18+', 'PI-014', 18.99, 1, 'DVD', 'THRILLER'),
    ('Planeta Proibido', 'PG-13', 'PP-015', 21.99, 1, 'DVD', 'SCIENCE_FICTION'),
    ('Férias Frustradas', 'PG-13', 'FF-016', 15.99, 1, 'DVD', 'COMEDY'),
    ('Segredos do Passado', 'PG', 'SP-017', 14.99, 1, 'DVD', 'DRAMA'),
    ('A Lenda do Cavaleiro Verde', 'PG-13', 'LC-018', 26.99, 1, 'BLU_RAY', 'FANTASY'),
    ('O Exorcista Retorna', '18+', 'ER-019', 20.99, 0, 'DVD', 'HORROR'),
    ('Amor Além do Tempo', 'PG', 'AT-020', 10.99, 1, 'DVD', 'ROMANCE'),
    ('O Silêncio dos Inocentes', '18+', 'SI-021', 17.99, 1, 'DVD', 'THRILLER'),
    ('Invasores de Corpos', 'PG-13', 'IC-022', 23.99, 1, 'DVD', 'SCIENCE_FICTION'),
    ('Um Tira Muito Louco', '18+', 'TM-023', 16.99, 1, 'DVD', 'ACTION_COMEDY'),
    ('O Resgate do Soldado Ryan', '18+', 'RS-024', 19.99, 1, 'DVD', 'ACTION_DRAMA'),
    ('Missão Impossível', 'PG-13', 'MI-025', 20.99, 1, 'DVD', 'ACTION_THRILLER'),
    ('Apertem os Cintos... o Piloto Sumiu!', 'PG-13', 'AP-026', 12.99, 1, 'DVD', 'COMEDY_DRAMA'),
    ('Corra que a Polícia Vem Aí', 'PG-13', 'CP-027', 14.99, 1, 'DVD', 'COMEDY_THRILLER'),
    ('Seven - Os Sete Crimes Capitais', '18+', 'SE-028', 18.99, 1, 'DVD', 'DRAMA_THRILLER'),
    ('Bad Boys', '18+', 'BB-029', 17.99, 1, 'DVD', 'ACTION_COMEDY_DRAMA'),
    ('Máquina Mortífera', '18+', 'MM-030', 18.99, 1, 'DVD', 'ACTION_COMEDY_THRILLER'),

    -- massa extra (novos)
    ('A Viagem do Balão Vermelho', 'FREE', 'EX-031', 8.99, 1, 'DVD', 'DRAMA'),
    ('Planeta Jardim', 'FREE', 'EX-032', 9.49, 1, 'VHS', 'DOCUMENTARY'),
    ('Os Pequenos Heróis', 'PG', 'EX-033', 11.99, 1, 'DVD', 'ANIMATION'),
    ('Risos em Família', 'PG', 'EX-034', 12.49, 1, 'DVD', 'COMEDY'),
    ('O Mistério do Museu', 'PG-13', 'EX-035', 16.99, 1, 'BLU_RAY', 'THRILLER'),
    ('A Floresta Sombria', '16+', 'EX-036', 17.49, 1, 'DVD', 'HORROR'),
    ('Códigos e Segredos', '16+', 'EX-037', 18.99, 0, 'DVD', 'THRILLER'),
    ('Operação Relâmpago', 'PG-13', 'EX-038', 19.99, 1, 'BLU_RAY', 'ACTION'),
    ('Cidade das Sombras', '18+', 'EX-039', 21.49, 1, 'BLU_RAY', 'DRAMA_THRILLER'),
    ('Amor em Paris', 'PG', 'EX-040', 10.49, 1, 'DVD', 'ROMANCE'),
    ('Nebulosa X', 'PG-13', 'EX-041', 22.99, 1, 'BLU_RAY', 'SCIENCE_FICTION'),
    ('Batalha Final', '18+', 'EX-042', 24.99, 0, 'BLU_RAY', 'ACTION_THRILLER'),
    ('Magia do Norte', 'PG', 'EX-043', 13.99, 1, 'DVD', 'FANTASY'),
    ('Caçadores do Tesouro', 'PG-13', 'EX-044', 18.49, 1, 'DVD', 'ACTION_COMEDY'),
    ('Diário do Medo', '16+', 'EX-045', 15.99, 1, 'DVD', 'HORROR'),
    ('No Limite', '18+', 'EX-046', 19.49, 1, 'DVD', 'ACTION_DRAMA'),
    ('Som e Silêncio', 'PG', 'EX-047', 12.99, 1, 'VHS', 'DRAMA'),
    ('A Última Gargalhada', '18+', 'EX-048', 16.49, 1, 'DVD', 'COMEDY_THRILLER'),
    ('Universo Paralelo', 'PG-13', 'EX-049', 20.49, 1, 'BLU_RAY', 'SCIENCE_FICTION'),
    ('Contos Animados', 'FREE', 'EX-050', 9.99, 1, 'DVD', 'ANIMATION');