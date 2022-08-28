## mysql 테이블 사이즈 확인하기

## mysql 특정 테이블 사이즈 알기
byte, kbyte, mbyte 단위로 구분해서 볼 수 있도록 한다. (이진수 표현의 1024 는 간단히 1000 으로 표현한다.)
```sql
SELECT
  concat(table_schema, '.', table_name) as 'table',
  concat(DATA_LENGTH, ' byte') as 'B SIZE',
  concat((DATA_LENGTH / 1000), ' kb') as 'KB',
  concat((DATA_LENGTH / (1000 * 1000)), ' mb') as 'MB'
FROM information_schema.TABLES
WHERE TABLE_ROWS IS NOT NULL AND TABLE_SCHEMA = '{table-name}';
```