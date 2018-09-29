## Difference Between Statement And PreparedStatement In JDBC
* __Statement__ 
    * 우리가 아래와 같은 구문을 쳤다.
    ~~~SQL
    SELECT * FROM EMP WHERE SALARY = 10000;
    ~~~   
    해당 Syntax 는 다음과 같은 절차를 지난다.

    1. Parse the Query
    2. Complie the Query
    3. Optimize Query
    4. Execute Query  

    그리고 매 순간마다 모든 쿼리들에 대해 2 ~ 4의 절차를 수행한다.
    
* __PreparedStatement__
    ~~~SQL
    SELECT * FROM EMP WHERE SALARY = ?;
    ~~~

    1. Parse the Query
    2. Compile the Query
    3. Optimize Query
    4. Execute Query

    __PreparedStatement__ 를 이용하게 된다면 1 ~ 3 의 과정 __단 한번__ 만 수행한다. 그리고 이후에 캐시에 담아서 재사용을 하는 것이다. 결과적으로 __PreparedStatement 가 DB에 부하를 줄여주는 역할__ 을 하고 있는 것이다.

    여기서 Dynamic SQL을 사용하는 경우 매번 조건절이 틀려지게 되기 때문에 Statement를 이용하는 것이 좋다. 반면에 Static SQL 의 경우에는 PreparedStatement를 이용하는 거이 좋다.
