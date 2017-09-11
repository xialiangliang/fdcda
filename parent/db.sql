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
create index user_id_sys_userrole_idx on sys_userrole(user_id);
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
  loginpwd VARCHAR2(300),
  username VARCHAR2(100),
  phone VARCHAR2(11),
  telephone VARCHAR2(30),
  email VARCHAR2(100),
  address VARCHAR2(100),
  valid number(2),
  create_time DATE,
  modify_time DATE
);
create unique index phone_sys_user_idx on sys_user(phone);
create unique index loginname_sys_user_idx on sys_user(loginname);
comment on table  sys_user is '登录账户';
comment on column sys_user.id is '主键id';
comment on column sys_user.loginname is '用户登录名';
comment on column sys_user.loginpwd is '登录密码，加密后';
comment on column sys_user.username is '用户名';
comment on column sys_user.phone is '手机号';
comment on column sys_user.telephone is '固定电话';
comment on column sys_user.email is '邮件地址';
comment on column sys_user.address is '地址';
comment on column sys_user.valid is '状态 1-有效 2-无效';
comment on column sys_user.create_time is '创建时间';
comment on column sys_user.modify_time is '修改时间';
CREATE SEQUENCE seq_sys_user
INCREMENT BY 1
START WITH 0
MINVALUE 0
NOMAXVALUE
NOCYCLE  
NOCACHE;


-- 短信模板表
create table sms_template
(
  id NUMBER(11) not null PRIMARY KEY,
  type NUMBER(11),
  phones VARCHAR2(255),
  template VARCHAR2(255),
  state NUMBER(3),
  create_time DATE,
  modify_time DATE
);
create unique index type_sms_template_idx on sms_template(type);
comment on table  sms_template is '短信模板';
comment on column sms_template.id is '主键id';
comment on column sms_template.type is '模板号';
comment on column sms_template.phones is '默认发送手机号';
comment on column sms_template.template is '模板内容';
comment on column sms_template.state is '状态 1-有效 2-无效';
comment on column sms_template.create_time is '创建时间';
comment on column sms_template.modify_time is '修改时间';
CREATE SEQUENCE seq_sms_template
INCREMENT BY 1
START WITH 0
MINVALUE 0
NOMAXVALUE
NOCYCLE  
NOCACHE;


INSERT into SYS_USER (ID, LOGINNAME, LOGINPWD, USERNAME, PHONE, TELEPHONE, EMAIL, ADDRESS, VALID, CREATE_TIME, MODIFY_TIME) VALUES (seq_sys_user.nextval, 'root', '68c892493719e46b605f9e8d976a52c44aa0c749229726fbbdfbf740a005d3d322b9cbe3a17bb8e2796fd3d891e9f5bc29811e7207121b8da7e32a96810932f4$ec5681a66aa22fcae1274f66db029c9085c5d32af3adffb58395c6e3956071ca84769f848c5990487d50e6b1b1fd87be730ee3eef73db5332223ce28a02d8083', 'root', '13300000222', null, null, null, 1, sysdate, sysdate);
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
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, NAME, URL, TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) VALUES (SEQ_SYS_RESOURCE.nextval, 2, '统计报表', '/analysisReport', 1, 'glyphicon-th', 1, '统计报表', sysdate, sysdate);

INSERT INTO SYS_RESOURCE (ID, PARENT_ID, NAME, URL, TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, '资源管理/新增', '/sysResource/new;/sysResource/save;/sysResource/getPublicKey', 2, 'glyphicon-th', 1, '新增', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysResource';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, NAME, URL, TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, '资源管理/修改', '/sysResource/find;/sysResource/update;/sysResource/listMap', 2, 'glyphicon-th', 2, '修改', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysResource';

INSERT INTO SYS_RESOURCE (ID, PARENT_ID, NAME, URL, TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, '角色管理/新增', '/sysRole/new;/sysRole/save;/sysRole/getResourceWithAuth', 2, 'glyphicon-th', 1, '新增', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysRole';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, NAME, URL, TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, '角色管理/修改', '/sysRole/find;/sysRole/update;/sysRole/getResourceWithAuth', 2, 'glyphicon-th', 2, '修改', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysRole';

INSERT INTO SYS_RESOURCE (ID, PARENT_ID, NAME, URL, TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, '人员管理/新增', '/sysUser/new;/sysUser/save;/sysUser/getPublicKey', 2, 'glyphicon-th', 1, '新增', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysUser';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, NAME, URL, TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, '人员管理/修改', '/sysUser/find;/sysUser/update;/sysUser/getPublicKey;/sysUser/resetPassword', 2, 'glyphicon-th', 2, '修改', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysUser';

INSERT INTO SMS_TEMPLATE (id, type, phones, template, state, create_time, modify_time) VALUES (SEQ_SMS_TEMPLATE.nextval, 1, null, '注册成功，手机号%s，登录名%s，初始密码%s，请及时修改密码。', 1, sysdate, sysdate);
INSERT INTO SMS_TEMPLATE (id, type, phones, template, state, create_time, modify_time) VALUES (SEQ_SMS_TEMPLATE.nextval, 2, null, '密码重置成功，手机号%s，登录名%s，密码%s，请及时修改密码。', 1, sysdate, sysdate);
