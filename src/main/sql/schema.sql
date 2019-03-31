--数据库初始化脚本

--创建数据库
create database ssm;

--使用数据库
use ssm;

--创建秒杀库存表，支持事务的存储引擎只有InnoDB
create table inventory(
inventory_id bigint not null auto_increment comment '商品库存id',
name varchar(120) not null comment '商品名称',
number int not null comment '库存数量',
create_time timestamp not null default current_timestamp comment '创建时间',
start_time timestamp not null comment '秒杀开启时间',
end_time timestamp not null comment '秒杀结束时间',
primary key (inventory_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)engine=InnoDB auto_increment=1000 default charset=UTF8 comment='秒杀库存表';

--初始化数据
insert into 
	inventory(name,number,start_time,end_time)
values
	('1000元秒杀iphone7','100','2017-05-22 00:00:00','2017-06-22 00:00:00'),
	('500元秒杀iphone6','200','2017-05-22 00:00:00','2017-06-22 00:00:00'),
	('300元秒杀小米4','300','2017-05-22 00:00:00','2017-06-22 00:00:00'),
	('200元秒杀红米note','400','2017-05-22 00:00:00','2017-06-22 00:00:00');
	
--创建秒杀成功明细表
create table success_killed(
inventory_id bigint not null comment '秒杀商品id',
user_phone bigint not null comment '用户手机号',
state tinyint not null default -1 comment '状态标识：-1无效，0成功，1已付款，2已发货',
create_time timestamp not null comment '创建时间',
primary key(inventory_id,user_phone),
key idx_create_time(create_time)
)engine=InnoDB default charset=UTF8 comment='秒杀成功明细表';
	
--连接数据库控制台
mysql -uroot -p19920222