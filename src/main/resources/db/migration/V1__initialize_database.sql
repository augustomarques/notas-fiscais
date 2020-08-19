create table empresas (
    id int8 not null, 
    cnpj varchar(255), 
    fantasia varchar(255), 
    razao_social varchar(255), 
    tipo varchar(255), 
    primary key (id)
);

create table notas_fiscais (
    id int8 not null, 
    data date, 
    numero int4, 
    valor numeric(19, 2), 
    prestador_id int8, 
    tomador_id int8, 
    primary key (id)
);

alter table empresas add constraint UK__empresa_cnpj unique (cnpj);

create sequence empresa_id_seq start 1 increment 1;
create sequence nota_fiscal_id_seq start 1 increment 1;

alter table notas_fiscais add constraint FK__empresa__prestador_id foreign key (prestador_id) references empresas;
alter table notas_fiscais add constraint FK__empresa__tomador_id foreign key (tomador_id) references empresas;