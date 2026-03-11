create table agendamentos(

    id bigint not null auto_increment,
    funcionario_id bigint,
    cliente_id bigint not null,
    especialidade_id bigint not null,
    data datetime not null,

    primary key(id),
    constraint fk_agendamentos_funcionario_id foreign key(funcionario_id) references funcionarios(id),
    constraint fk_agendamentos_cliente_id foreign key(cliente_id) references clientes(id),
    constraint fk_agendamentos_especialidade_id foreign key(especialidade_id) references especialidades(id)

);