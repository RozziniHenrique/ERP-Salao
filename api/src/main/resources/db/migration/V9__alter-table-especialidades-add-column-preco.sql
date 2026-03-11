alter table especialidades add preco decimal(19,2);

update especialidades set preco = 0.00;

alter table especialidades MODIFY preco decimal(19,2) NOT NULL;