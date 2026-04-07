-- ============================================================================
-- SISTEMA DE BIBLIOTECA ACADÉMICA - ISPTEC
-- Base de Dados: biblioteca_lab2
-- SGBD: MySQL 8.0+
-- Engenharia de Software II (ESII)
-- ============================================================================

DROP DATABASE IF EXISTS biblioteca_lab2;
CREATE DATABASE biblioteca_lab2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE biblioteca_lab2;

-- ============================================================================
-- TABELA: utilizador
-- ============================================================================
CREATE TABLE utilizador (
    id VARCHAR(20) PRIMARY KEY,
    nome_completo VARCHAR(150) NOT NULL,
    contacto VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABELA: livro
-- ============================================================================
CREATE TABLE livro (
    id INT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    ano_publicacao INT NOT NULL,
    total_exemplares INT NOT NULL,
    exemplares_disponiveis INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABELA: estado_emprestimo
-- ============================================================================
CREATE TABLE estado_emprestimo (
    id INT PRIMARY KEY,
    nome VARCHAR(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO estado_emprestimo VALUES 
(1, 'ATIVO'),
(2, 'DEVOLVIDO'),
(3, 'ATRASADO');

-- ============================================================================
-- TABELA: emprestimo
-- ============================================================================
CREATE TABLE emprestimo (
    id VARCHAR(36) PRIMARY KEY,
    data_inicio DATE NOT NULL,
    data_prevista_devolucao DATE NOT NULL,
    data_devolucao DATE NULL,
    estado INT NOT NULL,
    utilizador_id VARCHAR(20) NOT NULL,
    livro_id INT NOT NULL,
    
    FOREIGN KEY (utilizador_id) REFERENCES utilizador(id),
    FOREIGN KEY (livro_id) REFERENCES livro(id),
    FOREIGN KEY (estado) REFERENCES estado_emprestimo(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
