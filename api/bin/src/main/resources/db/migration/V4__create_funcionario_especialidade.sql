create table funcionario_especialidade (

    funcionario_id bigint not null,
    especialidade_id bigint not null,

    primary key (funcionario_id, especialidade_id),

    constraint fk_funcionario_especialidade_funcionario
        foreign key (funcionario_id)
        references funcionarios (id)
        on delete cascade,

    constraint fk_funcionario_especialidade_especialidade
        foreign key (especialidade_id)
        references especialidades (id)
        on delete cascade
);
