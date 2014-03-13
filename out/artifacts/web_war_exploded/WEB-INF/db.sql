CREATE TABLE  users(
  id integer primary key,                               --自增主键
  username VARCHAR2(50),                                   --用户名字
  password VARCHAR2(50),                                   --用户密码
  displayname VARCHAR2(50),                                 --显示名称
  divisionid  integer,                                    --行政区划id
  time timestamp ,                          --注册时间
  roleid  integer                                           --角色id
  ) ;

 CREATE SEQUENCE users_Sequence
 INCREMENT BY 1   -- 每次加几个
     START WITH 1     -- 从1开始计数
     NOMAXVALUE       -- 不设置最大值
     NOCYCLE          -- 一直累加，不循环
     CACHE 10;

CREATE TRIGGER users BEFORE
  insert ON  users FOR EACH ROW
  begin
  select users_Sequence.nextval into:New.id from dual;
  end;


CREATE  TABLE  divisions
(
    id integer primary key ,                        --自增主键
    parentid integer,                                --父节点
    divisionname VARCHAR2(100),                         --角色名称
    signaturepath VARCHAR2(250),                         --签章路径
    divisionpath VARCHAR2(200)                         --行政区划路径
);

CREATE SEQUENCE divisions_Sequence
 INCREMENT BY 1   -- 每次加几个
     START WITH 1     -- 从1开始计数
     NOMAXVALUE       -- 不设置最大值
     NOCYCLE          -- 一直累加，不循环
     CACHE 10;

CREATE TRIGGER divisions BEFORE
  insert ON  divisions FOR EACH ROW
  begin
  select divisions_Sequence.nextval into:New.id from dual;
  end;