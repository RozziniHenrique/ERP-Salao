create table especialidades (

    id bigint not null auto_increment,
    nome varchar(100) not null,
    descricao varchar(255),
    ativo boolean not null,

    primary key (id)
);
