alter table agendamentos add concluido tinyint;

update agendamentos set concluido = 0;

create table avaliacoes(
    id bigint not null auto_increment,
    nota int not null,
    comentario varchar(255),
    agendamento_id bigint not null,

    primary key(id),
    constraint fk_avaliacoes_agendamento_id foreign key(agendamento_id) references agendamentos(id)
);