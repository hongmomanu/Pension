CREATE TABLE users (
  id          INTEGER PRIMARY KEY, --自增主键
  username    VARCHAR2(50), --用户名字
  password    VARCHAR2(50), --用户密码
  displayname VARCHAR2(50), --显示名称
  divisionid  INTEGER, --行政区划id
  time        TIMESTAMP, --注册时间
  roleid      INTEGER                                           --角色id
);

CREATE SEQUENCE users_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE -- 一直累加，不循环
CACHE 10;

CREATE TRIGGER users BEFORE
INSERT ON users FOR EACH ROW
  BEGIN
    SELECT
      users_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;


CREATE TABLE divisions
(
  id            INTEGER PRIMARY KEY, --自增主键
  parentid      INTEGER, --父节点
  divisionname  VARCHAR2(100), --角色名称
  signaturepath VARCHAR2(250), --签章路径
  divisionpath  VARCHAR2(200)                         --行政区划路径
);

CREATE SEQUENCE divisions_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE -- 一直累加，不循环
CACHE 10;

CREATE TRIGGER divisions BEFORE
INSERT ON divisions FOR EACH ROW
  BEGIN
    SELECT
      divisions_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;



create table OPAUDIT
(
  AUDITID    NUMBER(15) not null,
  AUFLAG    VARCHAR2(1) not null,
  AUUSER    VARCHAR2(32),
  AUDATE    DATE,
  AUOPSENO  NUMBER(15),
  AULEVEL   VARCHAR2(1),
  AUDESC    VARCHAR2(500),
  AAA027    VARCHAR2(6),
  AUENDFLAG VARCHAR2(1)
);
-- Add comments to the table
comment on table OPAUDIT
  is '业务审核表';
-- Add comments to the columns
comment on column OPAUDIT.AUDITID
  is '主键';
comment on column OPAUDIT.AUFLAG
  is '当前审核标志：0－审核不通过，1－审核通过';
comment on column OPAUDIT.AUUSER
  is '当前审核人';
comment on column OPAUDIT.AUDATE
  is '当前审核时间';
comment on column OPAUDIT.AULEVEL
  is '当前审核级别（默认为0）';
comment on column OPAUDIT.AUDESC
  is '审核备注（审核不通过时的原因）';
comment on column OPAUDIT.AAA027
  is '统筹区编码';
comment on column OPAUDIT.AUENDFLAG
  is '审核结束标志(0：未审核结束，1：审核结束)';

create table OPAUDITHISTORY
(
  ID          NUMBER(15) not null,
  AUDITID    NUMBER(15) not null,
  LASTAUFLAG  VARCHAR2(1),
  LASTAULEVEL VARCHAR2(1),
  LASTAUUSER  VARCHAR2(32),
  LASTAUDATE  DATE,
  CUEAUFLAG   VARCHAR2(1),
  CUEAULEVEL  VARCHAR2(1),
  CUEAUUSER   VARCHAR2(32),
  CUEAUDATE   DATE,
  CUEAUDESC   VARCHAR2(500)
);
-- Add comments to the table
comment on table OPAUDITHISTORY
  is '审核变更历史表';
-- Add comments to the columns
comment on column OPAUDITHISTORY.ID
  is '表主键';
comment on column OPAUDITHISTORY.AUDITID
  is '业务审核表id';
comment on column OPAUDITHISTORY.LASTAUFLAG
  is '上次审核标志';
comment on column OPAUDITHISTORY.LASTAULEVEL
  is '上次审核级别';
comment on column OPAUDITHISTORY.LASTAUUSER
  is '上次审核人';
comment on column OPAUDITHISTORY.LASTAUDATE
  is '上次审核时间';
comment on column OPAUDITHISTORY.CUEAUFLAG
  is '本次审核标志';
comment on column OPAUDITHISTORY.CUEAULEVEL
  is '本次审核级别';
comment on column OPAUDITHISTORY.CUEAUUSER
  is '本次审核人';
comment on column OPAUDITHISTORY.CUEAUDATE
  is '本次审核时间';
comment on column OPAUDITHISTORY.CUEAUDESC
  is '本次审核备注';

create table OPAUDITBEAN
(
  AUDITID    NUMBER(15) not null,
  BEANVALUE  Varchar2(200) not null
);
comment on table OPAUDITBEAN
  is '审核Bean';
comment on column OPAUDITBEAN.AUDITID
  is '业务审核表id';
comment on column OPAUDITBEAN.BEANVALUE
  is '传递的参数值';

create sequence AUDIT_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;
create sequence AUDITHISTORY_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;