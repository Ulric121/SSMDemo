--���ݿ��ʼ���ű�

--�������ݿ�
create database ssm;

--ʹ�����ݿ�
use ssm;

--������ɱ����֧������Ĵ洢����ֻ��InnoDB
create table inventory(
inventory_id bigint not null auto_increment comment '��Ʒ���id',
name varchar(120) not null comment '��Ʒ����',
number int not null comment '�������',
create_time timestamp not null default current_timestamp comment '����ʱ��',
start_time timestamp not null comment '��ɱ����ʱ��',
end_time timestamp not null comment '��ɱ����ʱ��',
primary key (inventory_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)engine=InnoDB auto_increment=1000 default charset=UTF8 comment='��ɱ����';

--��ʼ������
insert into 
	inventory(name,number,start_time,end_time)
values
	('1000Ԫ��ɱiphone7','100','2017-05-22 00:00:00','2017-06-22 00:00:00'),
	('500Ԫ��ɱiphone6','200','2017-05-22 00:00:00','2017-06-22 00:00:00'),
	('300Ԫ��ɱС��4','300','2017-05-22 00:00:00','2017-06-22 00:00:00'),
	('200Ԫ��ɱ����note','400','2017-05-22 00:00:00','2017-06-22 00:00:00');
	
--������ɱ�ɹ���ϸ��
create table success_killed(
inventory_id bigint not null comment '��ɱ��Ʒid',
user_phone bigint not null comment '�û��ֻ���',
state tinyint not null default -1 comment '״̬��ʶ��-1��Ч��0�ɹ���1�Ѹ��2�ѷ���',
create_time timestamp not null comment '����ʱ��',
primary key(inventory_id,user_phone),
key idx_create_time(create_time)
)engine=InnoDB default charset=UTF8 comment='��ɱ�ɹ���ϸ��';
	
--�������ݿ����̨
mysql -uroot -p19920222