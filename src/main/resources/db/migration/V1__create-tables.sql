CREATE TABLE categorias(

    id bigint not null auto_increment,
    nome varchar(100) not null,
    primary key (id)

);

CREATE TABLE cursos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    categoria_id BIGINT,
    CONSTRAINT fk_curso_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

CREATE TABLE aulas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    curso_id BIGINT,
    CONSTRAINT fk_aula_curso FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

CREATE TABLE conteudo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255),
    aula_id BIGINT,
    dtype VARCHAR(31),
    CONSTRAINT fk_conteudo_aula FOREIGN KEY (aula_id) REFERENCES aulas(id)
);

CREATE TABLE videos (
    id BIGINT PRIMARY KEY,
    url VARCHAR(500),
    duracao INT,
    CONSTRAINT fk_video_conteudo FOREIGN KEY (id) REFERENCES conteudo(id)
);

CREATE TABLE artigos (
    id BIGINT PRIMARY KEY,
    texto TEXT,
    CONSTRAINT fk_artigo_conteudo FOREIGN KEY (id) REFERENCES conteudo(id)
);

CREATE TABLE questoes (
    id BIGINT PRIMARY KEY,
    enunciado TEXT,
    correta VARCHAR(255),
    CONSTRAINT fk_questao_conteudo FOREIGN KEY (id) REFERENCES conteudo(id)
);

CREATE TABLE questao_alternativas (
    questao_id BIGINT,
    alternativas VARCHAR(255),
    CONSTRAINT fk_alternativas_questao FOREIGN KEY (questao_id) REFERENCES questoes(id)
);