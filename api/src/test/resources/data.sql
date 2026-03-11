-- 1. LIMPEZA
DELETE FROM avaliacoes;
DELETE FROM agendamentos;
DELETE FROM funcionario_especialidade;
DELETE FROM clientes;
DELETE FROM funcionarios;
DELETE FROM especialidades;

-- 2. INSERTS DE ESPECIALIDADES
INSERT INTO especialidades (id, nome, descricao, ativo, preco)
VALUES (1, 'Manicure', 'Unhas', 1, 40.00), (3, 'Cabelo', 'Corte', 1, 37.50);

-- 3. INSERTS DE CLIENTES
INSERT INTO clientes (id, nome, email, cpf, telefone, logradouro, numero, bairro, cep, cidade, uf, ativo)
VALUES (1, 'Henrique', 'h@test.com', '111', '11', 'Rua A', '1', 'Centro', '1', 'SP', 'SP', 1),
       (3, 'Carlos', 'c@test.com', '333', '33', 'Rua C', '3', 'Centro', '3', 'SP', 'SP', 1);

-- 4. INSERTS DE FUNCIONARIOS
INSERT INTO funcionarios (id, nome, email, cpf, telefone, logradouro, numero, bairro, cep, cidade, uf, ativo)
VALUES (1, 'Mafe', 'm@test.com', '555', '55', 'Rua X', '1', 'Centro', '1', 'SP', 'SP', 1),
       (3, 'Kelly', 'k@test.com', '777', '77', 'Rua Y', '3', 'Centro', '3', 'SP', 'SP', 1);

-- 5. VÍNCULOS (Tabela de Ligação)
INSERT INTO funcionario_especialidade (funcionario_id, especialidade_id) VALUES (1, 1), (3, 3);

-- 6. AGENDAMENTO PARA O TESTE DE CANCELAMENTO (ID 1)
INSERT INTO agendamentos (id, cliente_id, funcionario_id, especialidade_id, data, motivo_cancelamento)
VALUES (1, 1, 1, 1, '2030-01-01 10:00:00', NULL);