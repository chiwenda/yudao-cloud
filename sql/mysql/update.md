### SQL变更


```sql
alter table infra_file add column tagName varchar(128) default '' comment '标志名称' after size;
alter table infra_file add column tagType int comment '标志类型' after tagName
```