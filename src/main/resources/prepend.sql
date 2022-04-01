-- Records of dict_operation
INSERT INTO `dict_operation`
VALUES (1, 'select', '查询/搜索');
INSERT INTO `dict_operation`
VALUES (2, 'insert', '插入/添加');
INSERT INTO `dict_operation`
VALUES (3, 'delete', '删除/使无效（无效不删除但不显示）');
INSERT INTO `dict_operation`
VALUES (4, 'update', '修改');
INSERT INTO `dict_operation`
VALUES (5, 'export', '导出数据/批量查询');
INSERT INTO `dict_operation`
VALUES (6, 'import', '导入数据/批量插入');
INSERT INTO `dict_operation`
VALUES (7, 'attachRole', '修改其他用户角色');

-- generate dict_operation_object records
delete
from dict_operation_object;
alter table dict_operation_object
    AUTO_INCREMENT = 1;

insert into kpimgr.dict_operation_object(table_name, column_name)
select table_name, '*' as column_name
from information_schema.tables
where table_schema = 'kpimgr'
  and table_name != 'dict_operation'
  and table_name != 'dict_operation_object'
  and table_name != 'role'
  and table_name != 'role_scope'
  and table_name != 'user_roles';

insert into kpimgr.dict_operation_object(table_name, column_name)
select t.table_name, c.column_name
from information_schema.tables t,
     information_schema.columns c
where t.table_name = c.table_name
  and t.table_schema = 'kpimgr'
  and t.table_name != 'dict_operation'
  and t.table_name != 'dict_operation_object'
  and t.table_name != 'role'
  and t.table_name != 'role_scope'
  and t.table_name != 'user_roles';

-- Records of role
INSERT INTO `role`
VALUES (1, 'admin', '江湖掌门人', null, null);
INSERT INTO `role`
VALUES (2, 'officer', '教务办老师', null, null);
INSERT INTO `role`
VALUES (3, 'initial', '初始角色', null, null);