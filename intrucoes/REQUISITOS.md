# Sistema de Biblioteca Académica ISPTEC
## Requisitos Funcionais, Não-Funcionais e Regras de Negócio

---

## 1. REGRAS DE NEGÓCIO

### 1.1 Utilizadores
- **RN-U1**: Um utilizador deve estar registado no sistema para requisitar livros
- **RN-U2**: Cada utilizador possui um identificador único (ID)
- **RN-U3**: Cada utilizador possui nome completo e contacto válido
- **RN-U4**: Um utilizador **não pode ter mais de 3 empréstimos ativos em simultâneo**
- **RN-U5**: Um utilizador pode realizar vários empréstimos ao longo do tempo (histórico mantido)

### 1.2 Livros
- **RN-L1**: Cada livro é identificado por um código único (ISBN)
- **RN-L2**: Cada livro contém: ISBN, título, ano de publicação e autor
- **RN-L3**: Um livro pode ter múltiplos exemplares físicos
- **RN-L4**: A disponibilidade de exemplares é controlada dinamicamente
- **RN-L5**: Um livro pode estar associado a vários empréstimos ao longo do tempo

### 1.3 Empréstimos
- **RN-E1**: Um empréstimo associa um utilizador a um livro numa data específica
- **RN-E2**: Cada empréstimo possui uma data de início e data prevista de devolução
- **RN-E3**: Cada empréstimo possui um identificador único (UUID)
- **RN-E4**: Um empréstimo só pode ser registado se **há exemplares disponíveis**
- **RN-E5**: Um empréstimo só pode ser registado se o utilizador **pode requisitar** (< 3 ativos)
- **RN-E6**: Cada empréstimo possui um estado: **ATIVO, DEVOLVIDO ou ATRASADO**
- **RN-E7**: Um empréstimo muda para **ATRASADO** se não for devolvido até à data prevista
- **RN-E8**: No momento da devolução, o exemplar é reposto na disponibilidade
- **RN-E9**: O sistema mantém o **histórico completo** de todos os empréstimos

---

## 2. REQUISITOS FUNCIONAIS

### 2.1 Gestão de Utilizadores
| ID | Descrição | Prioridade |
|----|-----------|-----------|
| RF-U1 | Registar um novo utilizador no sistema | Alta |
| RF-U2 | Consultar dados de um utilizador registado | Alta |
| RF-U3 | Listar todos os utilizadores | Média |
| RF-U4 | Validar limite de empréstimos ativos (máximo 3) | Alta |
| RF-U5 | Contar empréstimos ativos por utilizador | Alta |

### 2.2 Gestão de Livros
| ID | Descrição | Prioridade |
|----|-----------|-----------|
| RF-L1 | Registar um novo livro no sistema | Alta |
| RF-L2 | Consultar dados de um livro | Alta |
| RF-L3 | Listar todos os livros | Média |
| RF-L4 | Verificar disponibilidade de exemplares | Alta |
| RF-L5 | Reduzir disponibilidade ao emprestar | Alta |
| RF-L6 | Repor disponibilidade ao devolver | Alta |

### 2.3 Gestão de Empréstimos
| ID | Descrição | Prioridade |
|----|-----------|-----------|
| RF-E1 | Registar um novo empréstimo | Alta |
| RF-E2 | Validar pré-condições antes de emprestar | Alta |
| RF-E3 | Registar devolução de livro | Alta |
| RF-E4 | Atualizar estado de empréstimo para DEVOLVIDO | Alta |
| RF-E5 | Marcar empréstimo como ATRASADO | Média |
| RF-E6 | Consultar histórico de empréstimos | Média |
| RF-E7 | Consultar empréstimos ativos por utilizador | Média |
| RF-E8 | Consultar empréstimos por livro | Baixa |

### 2.4 Consultas e Relatórios
| ID | Descrição | Prioridade |
|----|-----------|-----------|
| RF-R1 | Gerar relatório de empréstimos ativos | Média |
| RF-R2 | Gerar relatório de empréstimos atrasados | Média |
| RF-R3 | Gerar relatório de livros indisponíveis | Baixa |
| RF-R4 | Visualizar disponibilidade de um livro | Média |

---

## 3. REQUISITOS NÃO-FUNCIONAIS

### 3.1 Performance
| ID | Descrição | Critério |
|----|-----------|----------|
| RNF-P1 | Tempo de resposta para registar empréstimo | < 200ms |
| RNF-P2 | Tempo de resposta para registar devolução | < 200ms |
| RNF-P3 | Tempo de resposta para consultas de disponibilidade | < 100ms |
| RNF-P4 | Capacidade de gerir até 10.000 utilizadores | Sistema não deve degenerar |
| RNF-P5 | Capacidade de gerir até 100.000 empréstimos | Sem impacto na performance |

### 3.2 Confiabilidade
| ID | Descrição | Critério |
|----|-----------|----------|
| RNF-C1 | Consistência de dados após transação de empréstimo | 100% |
| RNF-C2 | Não permitir inconsistência entre exemplares disponíveis e registos | 100% |
| RNF-C3 | Manter histórico completo de empréstimos | Permanente |
| RNF-C4 | Recuperação de falhas sem corrupção de dados | Garantido |

### 3.3 Usabilidade
| ID | Descrição | Critério |
|----|-----------|----------|
| RNF-U1 | Interface intuitiva para operações comuns | Pronto em operação |
| RNF-U2 | Mensagens de erro claras e em português | Todas as operações |
| RNF-U3 | Feedback visual de validações | Obrigatório |
| RNF-U4 | Operações frequentes com menos de 3 passos | Padrão |

### 3.4 Segurança
| ID | Descrição | Critério |
|----|-----------|----------|
| RNF-S1 | Validação de todos os inputs | Implementado |
| RNF-S2 | Prevenção de valores inválidos (ex.: datas inversas) | Implementado |
| RNF-S3 | Encapsulamento de dados (acesso controlado) | Implementado |
| RNF-S4 | Imutabilidade de dados históricos | Recomendado |

### 3.5 Manutenibilidade
| ID | Descrição | Critério |
|----|-----------|----------|
| RNF-M1 | Código estruturado em pacotes lógicos | entities, utils |
| RNF-M2 | Padrão de design orientado a objetos | Aplicado |
| RNF-M3 | Documentação de classes e métodos | JavaDoc |
| RNF-M4 | Coerência entre diagrama UML e implementação | Garantida |

### 3.6 Escalabilidade
| ID | Descrição | Critério |
|----|-----------|----------|
| RNF-E1 | Modelo preparado para adicionar autenticação | Extensível |
| RNF-E2 | Estrutura preparada para persistência em BD | Desacoplada |
| RNF-E3 | Possibilidade de adicionar notificações | Arquitetura aberta |
| RNF-E4 | Extensão para multas e suspensões | Preparado |

---

## 4. VALIDAÇÕES E RESTRIÇÕES

### 4.1 Validações de Input
- **V1**: ID do utilizador não pode ser vazio ou nulo
- **V2**: Nome completo do utilizador não pode ser vazio
- **V3**: Contacto do utilizador não pode ser vazio
- **V4**: ISBN não pode ser vazio ou duplicado
- **V5**: Título do livro não pode ser vazio
- **V6**: Ano de publicação deve ser válido (1000 <= ano <= ano atual)
- **V7**: Autor não pode ser vazio
- **V8**: Total de exemplares > 0
- **V9**: Data de devolução prevista >= data de início
- **V10**: Não podem haver empréstimos para utilizadores não registados
- **V11**: Não podem haver empréstimos para livros não registados

### 4.2 Restrições de Estado
- **E1**: Um empréstimo DEVOLVIDO não pode voltar a ATIVO
- **E2**: Disponibilidade de um livro não pode exceder total de exemplares
- **E3**: Disponibilidade de um livro não pode ser negativa
- **E4**: Um empréstimo não pode ter data de devolução anterior à data de devolução prevista (se devolvido com atraso)

---

## 5. CASOS DE USO PRINCIPAIS

### CU-1: Requisitar um Livro
**Actores**: Utilizador, Sistema
**Pré-condições**: 
- Utilizador está registado
- Livro está registado
- Utilizador tem < 3 empréstimos ativos

**Fluxo Principal**:
1. Utilizador solicita empréstimo de um livro
2. Sistema valida disponibilidade
3. Sistema valida limite de empréstimos
4. Sistema regista o empréstimo
5. Sistema reduz disponibilidade do livro
6. Sistema retorna confirmação

**Fluxo de Exceção**:
- Se utilizador não está registado → erro
- Se livro não está registado → erro
- Se utilizador tem 3 empréstimos ativos → erro
- Se livro não tem exemplares → erro

---

### CU-2: Devolver um Livro
**Actores**: Utilizador, Sistema
**Pré-condições**:
- Empréstimo existe e está ATIVO

**Fluxo Principal**:
1. Utilizador solicita devolução
2. Sistema marca empréstimo como DEVOLVIDO
3. Sistema repõe disponibilidade do livro
4. Sistema retorna confirmação

**Fluxo de Exceção**:
- Se empréstimo já foi devolvido → erro
- Se empréstimo não existe → erro

---

### CU-3: Consultar Estado de Empréstimo
**Actores**: Utilizador, Sistema
**Pré-condições**: Empréstimo existe

**Fluxo Principal**:
1. Utilizador consulta empréstimo
2. Sistema verifica se está atrasado
3. Sistema exibe estado (ATIVO, DEVOLVIDO, ATRASADO)

---

## 6. MAPEAMENTO DIAGRAMA → IMPLEMENTAÇÃO

| Elemento UML | Classe Java | Localização |
|--------------|------------|------------|
| EstadoEmprestimo | EstadoEmprestimo.java | entities |
| Utilizador | Utilizador.java | entities |
| Livro | Livro.java | entities |
| Emprestimo | Emprestimo.java | entities |
| Biblioteca | Biblioteca.java | utils |
| Main | App.java | root |

---

## 7. DEPENDÊNCIAS E COMPATIBILIDADE

### Linguagem
- Java 8+ (LocalDate, List, UUID)

### Bibliotecas
- java.time.LocalDate
- java.util.* (ArrayList, Collections, UUID)

### Padrões Aplicados
- **Encapsulamento**: Atributos private com getters
- **Imutabilidade**: Strings, datas e listas retornam cópias
- **Validação**: Checks nos construtores
- **Responsabilidade Única**: Cada classe gerencia seu domínio

---

## 8. NOTAS E CONSIDERAÇÕES FUTURAS

### Melhorias Sugeridas
1. **Persistência**: Integrar com BD (ex.: JPA/Hibernate)
2. **Segurança**: Adicionar autenticação e controlo de acesso
3. **Notificações**: Alertas para empréstimos atrasados
4. **Multas**: Sistema de cálculo de multas por atraso
5. **Renovação**: Permitir renovação de empréstimos
6. **Reservas**: Permitir reserva de livros indisponíveis
7. **Relatórios**: Gerar PDF com estatísticas
8. **API REST**: Expor funcionalidades via web service

---

**Versão**: 1.0  
**Data**: 5 de Abril de 2026  
**Disciplina**: Engenharia de Software II (ISPTEC)
