-- 系统资源表
create table sys_resource
(
  id NUMBER(11) not null PRIMARY KEY,
  parent_id NUMBER(11),
  top_id NUMBER(11),
  name VARCHAR2(255),
  url VARCHAR2(255),
  type NUMBER(3),
  menu_type NUMBER(3),
  icon VARCHAR2(64),
  sort NUMBER(5),
  memo VARCHAR2(255),
  create_time DATE,
  modify_time DATE
);
comment on table  sys_resource is '系统资源';
comment on column sys_resource.id is '主键id';
comment on column sys_resource.parent_id is '父资源id';
comment on column sys_resource.top_id is '顶级资源id';
comment on column sys_resource.name is '资源名称';
comment on column sys_resource.url is '资源路径';
comment on column sys_resource.type is '类型 1-菜单 2-按钮';
comment on column sys_resource.menu_type is 'N级菜单';
comment on column sys_resource.icon is '图标';
comment on column sys_resource.sort is '排序';
comment on column sys_resource.memo is '备注';
comment on column sys_resource.create_time is '创建时间';
comment on column sys_resource.modify_time is '修改时间';
CREATE SEQUENCE seq_sys_resource
INCREMENT BY 1
START WITH 1
MINVALUE 0
NOMAXVALUE
NOCYCLE  
NOCACHE;

-- 角色表
create table sys_role
(
  id NUMBER(11) not null PRIMARY KEY,
  name VARCHAR2(255),
  type NUMBER(11),
  create_time DATE,
  modify_time DATE
);
comment on table  sys_role is '角色';
comment on column sys_role.id is '主键id';
comment on column sys_role.name is '角色名称';
comment on column sys_role.type is '角色类型 1-管理员 2-普通用户';
comment on column sys_role.create_time is '创建时间';
comment on column sys_role.modify_time is '修改时间';
CREATE SEQUENCE seq_sys_role
INCREMENT BY 1
START WITH 1
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
START WITH 1
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
START WITH 1
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
START WITH 1
MINVALUE 0
NOMAXVALUE
NOCYCLE  
NOCACHE;

-- 门店表
create table sys_outlets
(
  id NUMBER(11) not null PRIMARY KEY,
  user_id number(11),
  name VARCHAR2(100),
  address VARCHAR2(100),
  create_time DATE,
  modify_time DATE
);
create index user_id_sys_outlets_idx on sys_outlets(user_id);
comment on table  sys_outlets is '登录账户';
comment on column sys_outlets.id is '主键id';
comment on column sys_outlets.user_id is '用户id';
comment on column sys_outlets.name is '门店名';
comment on column sys_outlets.address is '地址';
comment on column sys_outlets.create_time is '创建时间';
comment on column sys_outlets.modify_time is '修改时间';
CREATE SEQUENCE seq_sys_outlets
INCREMENT BY 1
START WITH 1
MINVALUE 0
NOMAXVALUE
NOCYCLE  
NOCACHE;


-- 设备表
create table sys_device
(
  id NUMBER(11) not null PRIMARY KEY,
  outlets_id number(11),
  seqno VARCHAR2(100),
  create_time DATE,
  modify_time DATE
);
create index outlets_id_sys_outlets_idx on sys_device(outlets_id);
create unique index seqno_sys_outlets_idx on sys_device(seqno);
comment on table  sys_device is '登录账户';
comment on column sys_device.id is '主键id';
comment on column sys_device.outlets_id is '门店id';
comment on column sys_device.seqno is '设备序列号';
comment on column sys_device.create_time is '创建时间';
comment on column sys_device.modify_time is '修改时间';
CREATE SEQUENCE seq_sys_device
INCREMENT BY 1
START WITH 1
MINVALUE 0
NOMAXVALUE
NOCYCLE  
NOCACHE;


-- 登录日志表
create table sys_loginlog
(
  id NUMBER(11) not null PRIMARY KEY,
  user_id NUMBER(11),
  ip VARCHAR2(32),
  device VARCHAR2(32),
  create_time DATE,
  modify_time DATE
);
create index user_id_sys_loginlog_idx on sys_loginlog(user_id);
comment on table  sys_loginlog is '登录日志';
comment on column sys_loginlog.id is '主键id';
comment on column sys_loginlog.user_id is '用户id';
comment on column sys_loginlog.ip is 'ip地址';
comment on column sys_loginlog.device is '登录设备';
comment on column sys_loginlog.create_time is '创建时间';
comment on column sys_loginlog.modify_time is '修改时间';
CREATE SEQUENCE seq_sys_loginlog
INCREMENT BY 1
START WITH 1
MINVALUE 0
NOMAXVALUE
NOCYCLE  
NOCACHE;


-- 商品表
create table sys_good
(
  id NUMBER(11) not null PRIMARY KEY,
  user_id NUMBER(11),
  name VARCHAR2(255),
  price number(18,2),
  state number(11),
  good_category_id NUMBER (11),
  description VARCHAR2(255),
  total_count NUMBER (11),
  remained_count NUMBER (11),
  up_time DATE,
  down_time DATE,
  memo VARCHAR2(255),
  create_time DATE,
  modify_time DATE
);
create index user_id_sys_good_idx on sys_good(user_id);
comment on table  sys_good is '商品';
comment on column sys_good.id is '主键id';
comment on column sys_good.user_id is '用户id';
comment on column sys_good.name is '商品名称';
comment on column sys_good.price is '单价';
comment on column sys_good.state is '商品状态1-上架2-下架';
comment on column sys_good.good_category_id is '商品分类id';
comment on column sys_good.description is '商品描述';
comment on column sys_good.total_count is '商品总数';
comment on column sys_good.remained_count is '商品剩余数量';
comment on column sys_good.up_time is '上架时间';
comment on column sys_good.down_time is '下架时间';
comment on column sys_good.memo is '备注';
comment on column sys_good.create_time is '创建时间';
comment on column sys_good.modify_time is '修改时间';
CREATE SEQUENCE seq_sys_good
INCREMENT BY 1
START WITH 1
MINVALUE 0
NOMAXVALUE
NOCYCLE  
NOCACHE;



-- 商品分类表
create table sys_good_category
(
  id NUMBER(11) not null PRIMARY KEY,
  parent_id NUMBER(11),
  child_id_list VARCHAR2(512),
  name VARCHAR2(64),
  create_time DATE,
  modify_time DATE
);
comment on table  sys_good_category is '商品分类';
comment on column sys_good_category.id is '主键id';
comment on column sys_good_category.parent_id is '上级id';
comment on column sys_good_category.child_id_list is '所有下级id';
comment on column sys_good_category.name is '分类名称';
comment on column sys_good_category.create_time is '创建时间';
comment on column sys_good_category.modify_time is '修改时间';
CREATE SEQUENCE seq_sys_good_category
INCREMENT BY 1
START WITH 1
MINVALUE 0
NOMAXVALUE
NOCYCLE  
NOCACHE;

-- 商品订单表
create table sys_good_order
(
  id NUMBER(11) not null PRIMARY KEY,
  user_id NUMBER (11),
  customer_id NUMBER (11),
  state NUMBER (11),
  memo VARCHAR2(255),
  create_time DATE,
  modify_time DATE
);
create index user_id_sys_good_order_idx on sys_good_order(user_id);
create index customer_id_sys_good_order_idx on sys_good_order(customer_id);
comment on table  sys_good_order is '商品订单';
comment on column sys_good_order.id is '主键id';
comment on column sys_good_order.user_id is '用户id';
comment on column sys_good_order.customer_id is '采购商id';
comment on column sys_good_order.state is '订单状态';
comment on column sys_good_order.memo is '备注';
comment on column sys_good_order.create_time is '创建时间';
comment on column sys_good_order.modify_time is '修改时间';
CREATE SEQUENCE seq_sys_good_order
INCREMENT BY 1
START WITH 1
MINVALUE 0
NOMAXVALUE
NOCYCLE  
NOCACHE;

-- 商品订单明细表
create table sys_good_order_detail
(
  id NUMBER(11) not null PRIMARY KEY,
  good_order_id NUMBER (11),
  good_id NUMBER (11),
  price NUMBER (11),
  count NUMBER (11),
  state NUMBER (11),
  create_time DATE,
  modify_time DATE
);
create index good_order_id_sgod_idx on sys_good_order_detail(good_order_id);
comment on table  sys_good_order_detail is '商品订单明细';
comment on column sys_good_order_detail.id is '主键id';
comment on column sys_good_order_detail.good_order_id is '订单id';
comment on column sys_good_order_detail.good_id is '商品id';
comment on column sys_good_order_detail.price is '实际单价';
comment on column sys_good_order_detail.count is '商品数量';
comment on column sys_good_order_detail.state is '订单状态';
comment on column sys_good_order_detail.create_time is '创建时间';
comment on column sys_good_order_detail.modify_time is '修改时间';
CREATE SEQUENCE seq_sys_good_order_detail
INCREMENT BY 1
START WITH 1
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
START WITH 1
MINVALUE 0
NOMAXVALUE
NOCYCLE  
NOCACHE;


-- 系统黑名单申请表
create table sys_blacklist_apply
(
  id NUMBER(11) not null PRIMARY KEY,
  customer_id NUMBER(11),
  user_id NUMBER(11),
  images VARCHAR2(512),
  memo VARCHAR2(512),
  state NUMBER(3),
  create_time DATE,
  modify_time DATE
);
comment on table  sys_blacklist_apply is '系统黑名单申请';
comment on column sys_blacklist_apply.id is '主键id';
comment on column sys_blacklist_apply.customer_id is '采购商id';
comment on column sys_blacklist_apply.user_id is '用户id';
comment on column sys_blacklist_apply.images is '图片url';
comment on column sys_blacklist_apply.memo is '备注';
comment on column sys_blacklist_apply.state is '状态 0-新申请 1-同意 2-不同意';
comment on column sys_blacklist_apply.create_time is '创建时间';
comment on column sys_blacklist_apply.modify_time is '修改时间';
CREATE SEQUENCE seq_sys_blacklist_apply
INCREMENT BY 1
START WITH 1
MINVALUE 0
NOMAXVALUE
NOCYCLE  
NOCACHE;


INSERT into SYS_USER (ID, LOGINNAME, LOGINPWD, USERNAME, PHONE, TELEPHONE, EMAIL, ADDRESS, VALID, CREATE_TIME, MODIFY_TIME) VALUES (seq_sys_user.nextval, 'root', '68c892493719e46b605f9e8d976a52c44aa0c749229726fbbdfbf740a005d3d322b9cbe3a17bb8e2796fd3d891e9f5bc29811e7207121b8da7e32a96810932f4$ec5681a66aa22fcae1274f66db029c9085c5d32af3adffb58395c6e3956071ca84769f848c5990487d50e6b1b1fd87be730ee3eef73db5332223ce28a02d8083', 'root', '13300000222', null, null, null, 1, sysdate, sysdate);
INSERT INTO SYS_ROLE (ID, NAME, CREATE_TIME, MODIFY_TIME) VALUES (SEQ_SYS_ROLE.nextval, '超级管理员', sysdate, sysdate);
INSERT INTO sys_roleinfo (id, role_id, resource_id, create_time, modify_time) VALUES (SEQ_SYS_ROLEINFO.nextval, 1, 0, sysdate, sysdate);
INSERT INTO SYS_USERROLE (ID, USER_ID, ROLE_ID, CREATE_TIME, MODIFY_TIME) VALUES (SEQ_SYS_USERROLE.nextval, 1, 1, sysdate, sysdate);


INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) VALUES (SEQ_SYS_RESOURCE.nextval, 0, SEQ_SYS_RESOURCE.currval, '首页', '/index', 1, 1, 'glyphicon-th', 1, '系统管理', sysdate, sysdate);
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) VALUES (SEQ_SYS_RESOURCE.nextval, 0, SEQ_SYS_RESOURCE.currval, '系统管理', '/systemManage', 1, 1, 'glyphicon-th', 1, '系统管理', sysdate, sysdate);
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) VALUES (SEQ_SYS_RESOURCE.nextval, 0, SEQ_SYS_RESOURCE.currval, '交易中心', '/analysis', 1, 1, 'glyphicon-th', 2, '交易中心', sysdate, sysdate);
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) VALUES (SEQ_SYS_RESOURCE.nextval, 0, SEQ_SYS_RESOURCE.currval, '访客记录', '/viewRecord', 1, 1, 'glyphicon-th', 3, '访客记录', sysdate, sysdate);
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) VALUES (SEQ_SYS_RESOURCE.nextval, 0, SEQ_SYS_RESOURCE.currval, '采购商', '/customer', 1, 1, 'glyphicon-th', 4, '采购商', sysdate, sysdate);

INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) select SEQ_SYS_RESOURCE.nextval, ID, ID, '资源管理', '/sysResource', 1, 2, 'glyphicon-th', 1, '资源管理', sysdate, sysdate from SYS_RESOURCE where URL = '/systemManage';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) select SEQ_SYS_RESOURCE.nextval, ID, ID, '角色管理', '/sysRole', 1, 2, 'glyphicon-th', 2, '角色管理', sysdate, sysdate from SYS_RESOURCE where URL = '/systemManage';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) select SEQ_SYS_RESOURCE.nextval, ID, ID, '人员管理', '/sysUser', 1, 2, 'glyphicon-th', 3, '人员管理', sysdate, sysdate from SYS_RESOURCE where URL = '/systemManage';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) select SEQ_SYS_RESOURCE.nextval, ID, ID, '商品管理', '/sysGood', 1, 2, 'glyphicon-th', 4, '商品管理', sysdate, sysdate from SYS_RESOURCE where URL = '/systemManage';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) select SEQ_SYS_RESOURCE.nextval, ID, ID, '平台使用情况', '/sysPlatform', 1, 2, 'glyphicon-th', 5, '平台使用情况', sysdate, sysdate from SYS_RESOURCE where URL = '/systemManage';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) select SEQ_SYS_RESOURCE.nextval, ID, ID, '门店管理', '/sysOutlets', 1, 2, 'glyphicon-th', 6, '门店管理', sysdate, sysdate from SYS_RESOURCE where URL = '/systemManage';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) select SEQ_SYS_RESOURCE.nextval, ID, ID, '统计报表', '/analysisReport', 1, 2, 'glyphicon-th', 1, '统计报表', sysdate, sysdate from SYS_RESOURCE where URL = '/analysis';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) select SEQ_SYS_RESOURCE.nextval, ID, ID, '我的访客', '/analysisReport', 1, 2, 'glyphicon-th', 1, '我的访客', sysdate, sysdate from SYS_RESOURCE where URL = '/viewRecord';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) select SEQ_SYS_RESOURCE.nextval, ID, ID, '交易记录', '/analysisReport', 1, 2, 'glyphicon-th', 1, '交易记录', sysdate, sysdate from SYS_RESOURCE where URL = '/viewRecord';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) select SEQ_SYS_RESOURCE.nextval, ID, ID, '采购商管理', '/customerInfo/manage', 1, 2, 'glyphicon-th', 1, '采购商管理', sysdate, sysdate from SYS_RESOURCE where URL = '/customer';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) select SEQ_SYS_RESOURCE.nextval, ID, ID, '黑名单管理', '/blackList', 1, 2, 'glyphicon-th', 2, '黑名单管理', sysdate, sysdate from SYS_RESOURCE where URL = '/customer';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) select SEQ_SYS_RESOURCE.nextval, ID, ID, 'VIP管理', '/customerInfo/vipManage', 1, 2, 'glyphicon-th', 1, 'VIP管理', sysdate, sysdate from SYS_RESOURCE where URL = '/customer';

INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) select SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/customer'), '用户黑名单', '/blackList/user', 1, 3, 'glyphicon-th', 2, '用户黑名单', sysdate, sysdate from SYS_RESOURCE where URL = '/blackList';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) select SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/customer'), '系统黑名单', '/blackList/system', 1, 3, 'glyphicon-th', 2, '系统黑名单', sysdate, sysdate from SYS_RESOURCE where URL = '/blackList';

INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) select SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/customerInfo'), '信息录入', '/customerInfo', 1, 2, 'glyphicon-th', 1, '信息录入', sysdate, sysdate from SYS_RESOURCE where URL = '/customerInfo/manage';

INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) select SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/customer'), '我的VIP', '/customerInfo/vipList', 1, 2, 'glyphicon-th', 1, '我的VIP', sysdate, sysdate from SYS_RESOURCE where URL = '/customer/vipManage';

INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '资源管理/新增', '/sysResource/new;/sysResource/save;/sysResource/getPublicKey', 2, null, 'glyphicon-th', 1, '新增', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysResource';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '资源管理/修改', '/sysResource/find;/sysResource/update;/sysResource/listMap', 2, null, 'glyphicon-th', 2, '修改', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysResource';

INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '角色管理/新增', '/sysRole/new;/sysRole/save;/sysRole/getResourceWithAuth', 2, null, 'glyphicon-th', 1, '新增', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysRole';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '角色管理/修改', '/sysRole/find;/sysRole/update;/sysRole/getResourceWithAuth', 2, null, 'glyphicon-th', 2, '修改', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysRole';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '角色管理/删除', '/sysRole/delete', 2, null, 'glyphicon-th', 3, '删除', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysRole';

INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '人员管理/新增', '/sysUser/new;/sysUser/save;/sysUser/getPublicKey', 2, null, 'glyphicon-th', 1, '新增', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysUser';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '人员管理/修改', '/sysUser/find;/sysUser/update;/sysUser/getPublicKey;/sysUser/resetPassword', 2, null, 'glyphicon-th', 2, '修改', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysUser';

INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '门店管理/新增', '/sysOutlets/new;/sysOutlets/save', 2, null, 'glyphicon-th', 1, '新增', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysOutlets';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '门店管理/修改', '/sysOutlets/find;/sysOutlets/update', 2, null, 'glyphicon-th', 2, '修改', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysOutlets';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '门店管理/删除', '/sysOutlets/delete', 2, null, 'glyphicon-th', 3, '删除', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysOutlets';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '门店管理/设备管理', '/sysOutlets/sysDevice/list', 2, null, 'glyphicon-th', 4, '设备管理', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysOutlets';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '设备管理/新增', '/sysOutlets/sysDevice/new;/sysOutlets/sysDevice/save', 2, null, 'glyphicon-th', 1, '新增', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysOutlets/sysDevice/list';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '设备管理/修改', '/sysOutlets/sysDevice/find;/sysOutlets/sysDevice/update', 2, null, 'glyphicon-th', 2, '修改', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysOutlets/sysDevice/list';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '设备管理/删除', '/sysOutlets/sysDevice/delete', 2, null, 'glyphicon-th', 3, '删除', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysOutlets/sysDevice/list';

INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '商品管理/新增', '/sysGood/new;/sysGood/save', 2, null, 'glyphicon-th', 1, '新增', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysGood';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '商品管理/修改', '/sysGood/find;/sysUser/update', 2, null, 'glyphicon-th', 2, '修改', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysGood';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '商品管理/上架&下架', '/sysGood/on;/sysGood/off', 2, null, 'glyphicon-th', 3, '上架&下架', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysGood';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '商品管理/删除', '/sysGood/delete', 2, null, 'glyphicon-th', 4, '删除', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysGood';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/systemManage'), '商品管理/分类管理', '/sysGood/category;/sysGood/category/listMap;/sysGood/category/new;/sysGood/category/save;/sysGood/category/update;/sysGood/category/find;/sysGood/category/delete', 2, null, 'glyphicon-th', 3, '分类管理', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/sysGood';

INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/customer'), '采购商管理/查看', '/customerInfo/listJson', 2, null, 'glyphicon-th', 1, '查看', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/customerInfo';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/customer'), '采购商管理/新增', '/customerInfo/new;/customerInfo/save', 2, null, 'glyphicon-th', 1, '新增', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/customerInfo';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/customer'), '采购商管理/修改', '/customerInfo/find;/customerInfo/update', 2, null, 'glyphicon-th', 2, '修改', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/customerInfo';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/customer'), '采购商管理/删除', '/customerInfo/delete', 2, null, 'glyphicon-th', 3, '删除', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/customerInfo';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/customer'), '采购商管理/添加黑名单', '/customerInfo/addToBlackList', 2, null, 'glyphicon-th', 4, '添加黑名单', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/customerInfo';

INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/customer'), '查看我的VIP', '/customerInfo/vipListJson', 2, null, 'glyphicon-th', 4, '查看我的VIP', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/customerInfo/vipList';

-- INSERT INTO SYS_RESOURCE (ID, PARENT_ID, NAME, URL, TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, '黑名单管理/用户黑名单', '/blackList/user', 2, 'glyphicon-th', 1, '用户黑名单', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/blackList';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/customer'), '查看用户黑名单', '/blackList/user/listJson;/blackList/user/find', 2, null, 'glyphicon-th', 2, '查看用户黑名单', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/blackList/user';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/customer'), '黑名单管理/移除用户黑名单', '/blackList/user/remove', 2, null, 'glyphicon-th', 2, '移除用户黑名单', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/blackList/user';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/customer'), '黑名单管理/申请系统黑名单', '/blackList/user/applySystemBlacklist;/blackList/user/applySystemBlacklist/page', 3, null, 'glyphicon-th', 2, '申请系统黑名单', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/blackList/user';
INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/customer'), '查看系统黑名单', '/blackList/system/listJson;/blackList/user/find', 2, null, 'glyphicon-th', 2, '查看系统黑名单', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/blackList/system';
-- INSERT INTO SYS_RESOURCE (ID, PARENT_ID, TOP_ID, NAME, URL, TYPE, MENU_TYPE, ICON, SORT, MEMO, CREATE_TIME, MODIFY_TIME) SELECT SEQ_SYS_RESOURCE.nextval, ID, (select ID from SYS_RESOURCE WHERE URL = '/customer'), '黑名单管理/系统黑名单', '/blackList/system', 2, 'glyphicon-th', 4, '系统黑名单', sysdate, sysdate FROM SYS_RESOURCE WHERE url = '/blackList/user';

INSERT INTO SMS_TEMPLATE (id, type, phones, template, state, create_time, modify_time) VALUES (SEQ_SMS_TEMPLATE.nextval, 1, null, '注册成功，手机号%s，登录名%s，初始密码%s，请及时修改密码。', 1, sysdate, sysdate);
INSERT INTO SMS_TEMPLATE (id, type, phones, template, state, create_time, modify_time) VALUES (SEQ_SMS_TEMPLATE.nextval, 2, null, '密码重置成功，手机号%s，登录名%s，密码%s，请及时修改密码。', 1, sysdate, sysdate);
