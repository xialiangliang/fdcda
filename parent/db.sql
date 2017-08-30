-- 系统资源表
create table sys_resource
(
  id NUMBER(11) not null PRIMARY KEY,
  parent_id NUMBER(11),
  name VARCHAR2(255),
  url VARCHAR2(255),
  type NUMBER(3),
  icon VARCHAR2(64),
  sort NUMBER(5),
  memo VARCHAR2(255),
  create_time DATE,
  modify_time DATE
);
comment on table  sys_resource is '系统资源';
comment on column sys_resource.id is '主键id';
comment on column sys_resource.parent_id is '父资源id';
comment on column sys_resource.name is '资源名称';
comment on column sys_resource.url is '资源路径';
comment on column sys_resource.type is '类型 1-菜单 2-按钮';
comment on column sys_resource.icon is '图标';
comment on column sys_resource.sort is '排序';
comment on column sys_resource.memo is '备注';
comment on column sys_resource.create_time is '创建时间';
comment on column sys_resource.modify_time is '修改时间';
CREATE SEQUENCE seq_sys_resource
INCREMENT BY 1
START WITH 0
MINVALUE 0
NOMAXVALUE
NOCYCLE  
NOCACHE;

-- 角色表
create table sys_role
(
  id NUMBER(11) not null PRIMARY KEY,
  name VARCHAR2(255),
  create_time DATE,
  modify_time DATE
);
comment on table  sys_role is '角色';
comment on column sys_role.id is '主键id';
comment on column sys_role.name is '角色名称';
comment on column sys_role.create_time is '创建时间';
comment on column sys_role.modify_time is '修改时间';
CREATE SEQUENCE seq_sys_role
INCREMENT BY 1
START WITH 0
MINVALUE 0
NOMAXVALUE
NOCYCLE  
NOCACHE;

-- 用户角色表
create table sys_userrole
(
  id NUMBER(11) not null PRIMARY KEY,
  user_id NUMBER(11),
  role_id NUMBER(11),
  create_time DATE,
  modify_time DATE
);
comment on table  sys_userrole is '用户角色';
comment on column sys_userrole.id is '主键id';
comment on column sys_userrole.user_id is '用户id';
comment on column sys_userrole.role_id is '角色id';
comment on column sys_userrole.create_time is '创建时间';
comment on column sys_userrole.modify_time is '修改时间';
create index user_id_idx on sys_userrole(user_id);
CREATE SEQUENCE seq_sys_userrole
INCREMENT BY 1
START WITH 0
MINVALUE 0
NOMAXVALUE
NOCYCLE  
NOCACHE;

-- 角色资源表
create table sys_roleinfo
(
  id NUMBER(11) not null PRIMARY KEY,
  role_id NUMBER(11),
  resource_id NUMBER(11),
  create_time DATE,
  modify_time DATE
);
comment on table  sys_roleinfo is '角色';
comment on column sys_roleinfo.id is '主键id';
comment on column sys_roleinfo.role_id is '角色id';
comment on column sys_roleinfo.resource_id is '资源id';
comment on column sys_roleinfo.create_time is '创建时间';
comment on column sys_roleinfo.modify_time is '修改时间';
CREATE SEQUENCE seq_sys_roleinfo
INCREMENT BY 1
START WITH 0
MINVALUE 0
NOMAXVALUE
NOCYCLE  
NOCACHE;

-- 登录账户表
create table sys_user
(
  id NUMBER(11) not null PRIMARY KEY,
  loginname VARCHAR2(20),
  loginpwd VARCHAR2(255),
  username VARCHAR2(100),
  phone VARCHAR2(11),
  telephone VARCHAR2(30),
  email VARCHAR2(100),
  address VARCHAR2(100),
  valid number(2),
  create_time DATE,
  modify_time DATE
);
create index phone_idx on sys_user(phone);
comment on table  sys_user is '登录账户';
comment on column sys_user.id is '主键id';
comment on column sys_user.loginname is '用户登录名';
comment on column sys_user.loginpwd is '登录密码，加密后';
comment on column sys_user.username is '用户名';
comment on column sys_user.phone is '手机号';
comment on column sys_user.telephone is '固定电话';
comment on column sys_user.email is '邮件地址';
comment on column sys_user.address is '地址';
comment on column sys_user.valid is '备注';
comment on column sys_user.create_time is '创建时间';
comment on column sys_user.modify_time is '修改时间';
CREATE SEQUENCE seq_sys_user
INCREMENT BY 1
START WITH 0
MINVALUE 0
NOMAXVALUE
NOCYCLE  
NOCACHE;


INSERT into SYS_USER (ID, LOGINNAME, LOGINPWD, USERNAME, PHONE, TELEPHONE, EMAIL, ADDRESS, VALID, CREATE_TIME, MODIFY_TIME) VALUES (seq_sys_user.nextval, 'root', '123456', 'root', '13300000222', null, null, null, 1, sysdate, sysdate);
INSERT INTO SYS_ROLE (ID, NAME, CREATE_TIME, MODIFY_TIME) VALUES (SEQ_SYS_ROLE.nextval, '超级管理员', sysdate, sysdate);
INSERT INTO sys_roleinfo (id, role_id, resource_id, create_time, modify_time) VALUES (SEQ_SYS_ROLEINFO.nextval, 1, 0, sysdate, sysdate);
INSERT INTO SYS_USERROLE (ID, USER_ID, ROLE_ID, CREATE_TIME, MODIFY_TIME) VALUES (SEQ_SYS_USERROLE.nextval, 1, 1, sysdate, sysdate);


INSERT INTO SYS_RESOURCE (ID, PARENT_ID, NAME, URL, TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) VALUES (SEQ_SYS_RESOURCE.nextval, 0, '系统管理', '/', 1, 'glyphicon-th', 1, '系统管理', sysdate, sysdate);
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, NAME, URL, TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) VALUES (SEQ_SYS_RESOURCE.nextval, 0, '经营分析', '/', 1, 'glyphicon-th', 2, '经营分析', sysdate, sysdate);
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, NAME, URL, TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) VALUES (SEQ_SYS_RESOURCE.nextval, 1, '资源管理', '/sysResource', 1, 'glyphicon-th', 1, '资源管理', sysdate, sysdate);
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, NAME, URL, TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) VALUES (SEQ_SYS_RESOURCE.nextval, 1, '角色管理', '/sysRole', 1, 'glyphicon-th', 2, '角色管理', sysdate, sysdate);
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, NAME, URL, TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) VALUES (SEQ_SYS_RESOURCE.nextval, 1, '人员管理', '/sysUser', 1, 'glyphicon-th', 3, '人员管理', sysdate, sysdate);
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, NAME, URL, TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) VALUES (SEQ_SYS_RESOURCE.nextval, 1, '商品管理', '/sysGoods', 1, 'glyphicon-th', 4, '商品管理', sysdate, sysdate);
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, NAME, URL, TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) VALUES (SEQ_SYS_RESOURCE.nextval, 1, '平台使用情况', '/sysPlatform', 1, 'glyphicon-th', 5, '平台使用情况', sysdate, sysdate);
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, NAME, URL, TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) VALUES (SEQ_SYS_RESOURCE.nextval, 2, '统计报表', '/analysisReport', 1, 'glyphicon-th', 1, '人员管理', sysdate, sysdate);
