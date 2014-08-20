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



