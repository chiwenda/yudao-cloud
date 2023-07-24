### SQL变更


```sql
alter table infra_file add column tag_name varchar(128) default '' comment '标志名称' after size;
alter table infra_file add column tag_type int comment '标志类型' after tag_name
```