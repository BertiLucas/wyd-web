CREATE TABLE conta_jogo (
                            id BIGSERIAL PRIMARY KEY,
                            login VARCHAR(50) NOT NULL UNIQUE,
                            senha VARCHAR(255) NOT NULL,
                            status VARCHAR(20) NOT NULL,
                            data_criacao TIMESTAMP NOT NULL,
                            usuario_id BIGINT NOT NULL,

                            CONSTRAINT fk_conta_jogo_usuario
                                FOREIGN KEY (usuario_id)
                                    REFERENCES usuario (id)
);