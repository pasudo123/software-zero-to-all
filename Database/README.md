## Database

### Difference Between Statement And PreparedStatement In JDBC
* Statement 
    * 우리가 아래와 같은 구문을 쳤다.
    ~~~SQL
    SELECT * FROM EMP WHERE SALARY = 10000;
    ~~~   
    해당 Syntax 는 다음과 같은 절차를 지난다.

    1. Parse the Query
    2. Complie the Query
    3. Optimize Query
    4. Execute Query  

    그리고 매 순간마다 모든 쿼리들은 위의 같은 절차를 수행한다.

    
