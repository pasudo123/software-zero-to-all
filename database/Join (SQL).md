## Join (SQL)
Join 은 데이터베이스 내의 여러 테이블의 레코드를 조합하여 하나의 열로 표현하는 것이다. 조인은 테이블로서 저장하거나, 그 자체로 이용할 수 있는 결과 셋을 만들어낸다. 조인은 아래의 네가지 유형으로 존재한다. [위키참조](https://ko.wikipedia.org/wiki/Join_(SQL))

1. INNER JOIN
2. LEFT JOIN
3. RIGHT JOIN
4. OUTER JOIN

### 실습환경
```SQL
mysql  Ver 8.0.13 for Win64 on x86_64 (MySQL Community Server - GPL)
```

### 실습하기 이전, 데이터 생성
```SQL
CREATE TABLE department
(
 DepartmentID INT Primary key,
 DepartmentName VARCHAR(20)
);

CREATE TABLE employee
(
 LastName VARCHAR(20),
 DepartmentID INT references department(DepartmentID)
);

INSERT INTO department VALUES(31, 'Sales');
INSERT INTO department VALUES(33, 'Engineering');
INSERT INTO department VALUES(34, 'Clerical');
INSERT INTO department VALUES(35, 'Marketing');

INSERT INTO employee VALUES('Rafferty', 31);
INSERT INTO employee VALUES('Jones', 33);
INSERT INTO employee VALUES('Heisenberg', 33);
INSERT INTO employee VALUES('Robinson', 34);
INSERT INTO employee VALUES('Smith', 34);
INSERT INTO employee VALUES('Williams', NULL);
```

### SELECT Query
```SQL
mysql> SELECT * FROM department;
+--------------+----------------+
| DepartmentID | DepartmentName |
+--------------+----------------+
|           31 | Sales          |
|           33 | Engineering    |
|           34 | Clerical       |
|           35 | Marketing      |
+--------------+----------------+

mysql> SELECT * FROM employee;
+------------+--------------+
| LastName   | DepartmentID |
+------------+--------------+
| Rafferty   |           31 |
| Jones      |           33 |
| Heisenberg |           33 |
| Robinson   |           34 |
| Smith      |           34 |
| Williams   |         NULL |
+------------+--------------+
```

### INNER JOIN
![](https://upload.wikimedia.org/wikipedia/commons/thumb/9/99/Venn0001.svg/120px-Venn0001.svg.png)   
두 개의 테이블에서 서로 가지고 있는 행을 추출하는데 이용된다.
```SQL
mysql> SELECT * FROM department d INNER JOIN employee e ON d.DepartmentID = e.DepartmentID;
+--------------+----------------+------------+--------------+
| DepartmentID | DepartmentName | LastName   | DepartmentID |
+--------------+----------------+------------+--------------+
|           31 | Sales          | Rafferty   |           31 |
|           33 | Engineering    | Jones      |           33 |
|           33 | Engineering    | Heisenberg |           33 |
|           34 | Clerical       | Robinson   |           34 |
|           34 | Clerical       | Smith      |           34 |
+--------------+----------------+------------+--------------+
```

### LEFT JOIN (= LEFT OUTER JOIN)
![](https://upload.wikimedia.org/wikipedia/commons/thumb/1/10/Venn0101.svg/120px-Venn0101.svg.png)   
두 개의 테이블에서 왼쪽의 테이블의 모든 행의 값을 추출한다. 추가로 거기 포함된 오른쪽 테이블의 값도 추출한다.
```SQL
mysql> SELECT * FROM department d LEFT JOIN employee e ON d.DepartmentID = e.DepartmentID;
+--------------+----------------+------------+--------------+
| DepartmentID | DepartmentName | LastName   | DepartmentID |
+--------------+----------------+------------+--------------+
|           31 | Sales          | Rafferty   |           31 |
|           33 | Engineering    | Jones      |           33 |
|           33 | Engineering    | Heisenberg |           33 |
|           34 | Clerical       | Robinson   |           34 |
|           34 | Clerical       | Smith      |           34 |
|           35 | Marketing      | NULL       |         NULL |
+--------------+----------------+------------+--------------+
```

### LEFT JOIN
![](https://upload.wikimedia.org/wikipedia/commons/thumb/e/e6/Venn0100.svg/120px-Venn0100.svg.png)   
LEFT JOIN 과 동일하지만 왼쪽 테이블과 오른쪽 테이블에 포함된 부분은 추출하지 않고 순수한 왼쪽 테이블 값만 추출한다.
```SQL
mysql> SELECT * FROM department d LEFT JOIN employee e ON d.DepartmentID = e.DepartmentID WHERE e.DepartmentID IS NULL;
+--------------+----------------+----------+--------------+
| DepartmentID | DepartmentName | LastName | DepartmentID |
+--------------+----------------+----------+--------------+
|           35 | Marketing      | NULL     |         NULL |
+--------------+----------------+----------+--------------+
```

### RIGHT JOIN (= RIGHT OUTER JOIN)
![](https://upload.wikimedia.org/wikipedia/commons/thumb/7/76/Venn0011.svg/120px-Venn0011.svg.png)   
LEFT OUTER JOIN 과 유사하다. 오른쪽 테이블의 모든 행의 값을 추출한다. 왼쪽 테이블에 포함된 값도 같이 추추한다.
```SQL
mysql> SELECT * FROM department d RIGHT JOIN employee e ON d.DepartmentID = e.DepartmentID;
+--------------+----------------+------------+--------------+
| DepartmentID | DepartmentName | LastName   | DepartmentID |
+--------------+----------------+------------+--------------+
|           31 | Sales          | Rafferty   |           31 |
|           33 | Engineering    | Jones      |           33 |
|           33 | Engineering    | Heisenberg |           33 |
|           34 | Clerical       | Robinson   |           34 |
|           34 | Clerical       | Smith      |           34 |
|         NULL | NULL           | Williams   |         NULL |
+--------------+----------------+------------+--------------+
```

### RIGHT JOIN
![](https://upload.wikimedia.org/wikipedia/commons/thumb/5/5a/Venn0010.svg/120px-Venn0010.svg.png)   
RIGHT OUTER JOIN 에서 왼쪽 테이블의 포함된 값을 제외하고 추출한다.
```SQL
mysql> SELECT * FROM department d RIGHT JOIN employee e ON d.DepartmentID = e.DepartmentID WHERE d.DepartmentID IS NULL;
+--------------+----------------+----------+--------------+
| DepartmentID | DepartmentName | LastName | DepartmentID |
+--------------+----------------+----------+--------------+
|         NULL | NULL           | Williams |         NULL |
+--------------+----------------+----------+--------------+
```

### FULL OUTER JOIN
![](https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/Venn0111.svg/120px-Venn0111.svg.png)   
두 테이블에 포함된 행의 값을 출력한다. UNION 키워드는 두 개 혹은 그 이상의 SELECT 문을 합치는데 이용할 수 있다.  
UNION ALL 키워드를 이용한다면 중복은 제거되지 않고 나타남을 확인 가능하다.
```SQL
mysql> SELECT * FROM department d1 LEFT JOIN employee e1 ON d1.DepartmentID = e1.DepartmentID
    -> UNION
    -> SELECT * FROM department d2 RIGHT JOIN employee e2 ON d2.DepartmentID = e2.DepartmentID;
+--------------+----------------+------------+--------------+
| DepartmentID | DepartmentName | LastName   | DepartmentID |
+--------------+----------------+------------+--------------+
|           31 | Sales          | Rafferty   |           31 |
|           33 | Engineering    | Jones      |           33 |
|           33 | Engineering    | Heisenberg |           33 |
|           34 | Clerical       | Robinson   |           34 |
|           34 | Clerical       | Smith      |           34 |
|           35 | Marketing      | NULL       |         NULL |
|         NULL | NULL           | Williams   |         NULL |
+--------------+----------------+------------+--------------+

-- UNION ALL 이용
mysql> SELECT * FROM department d1 LEFT JOIN employee e1 ON d1.DepartmentID = e1.DepartmentID
    -> UNION ALL
    -> SELECT * FROM department d2 RIGHT JOIN employee e2 ON d2.DepartmentID = e2.DepartmentID;
+--------------+----------------+------------+--------------+
| DepartmentID | DepartmentName | LastName   | DepartmentID |
+--------------+----------------+------------+--------------+
|           31 | Sales          | Rafferty   |           31 |
|           33 | Engineering    | Jones      |           33 |
|           33 | Engineering    | Heisenberg |           33 |
|           34 | Clerical       | Robinson   |           34 |
|           34 | Clerical       | Smith      |           34 |
|           35 | Marketing      | NULL       |         NULL |
|           31 | Sales          | Rafferty   |           31 |
|           33 | Engineering    | Jones      |           33 |
|           33 | Engineering    | Heisenberg |           33 |
|           34 | Clerical       | Robinson   |           34 |
|           34 | Clerical       | Smith      |           34 |
|         NULL | NULL           | Williams   |         NULL |
+--------------+----------------+------------+--------------+
```

### FULL OUTER JOIN 에서 INNER JOIN 부분 제외
![](https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Venn0110.svg/120px-Venn0110.svg.png)   
```SQL
mysql> SELECT * FROM department d1 LEFT JOIN employee e1 ON d1.DepartmentID = e1.DepartmentID WHERE d1.DepartmentID IS NULL
    -> UNION
    -> SELECT * FROM department d2 RIGHT JOIN employee e2 ON d2.DepartmentID = e2.DepartmentID WHERE e2.DepartmentID IS NULL;
+--------------+----------------+----------+--------------+
| DepartmentID | DepartmentName | LastName | DepartmentID |
+--------------+----------------+----------+--------------+
|         NULL | NULL           | Williams |         NULL |
+--------------+----------------+----------+--------------+
```
