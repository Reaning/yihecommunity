# yihecommunity

## 参考内容
[Spring 文档](https://spring.io/guides)  
[Spring Web](https://spring.io/guides/gs/serving-web-content/)  
[Bootstrap 文档](https://v3.bootcss.com/getting-started/)  
[Github OAuth](https://docs.github.com/en/developers/apps/building-oauth-apps/creating-an-oauth-app)  
[Visual Paradigm](https://visual-paradigm.com/)  
[Fast JSON](https://github.com/alibaba/fastjson/)  
[Mybatis](http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)  
[Flyway](https://flywaydb.org/documentation/getstarted/firststeps/maven)  
[Lombok](https://projectlombok.org/)  
[MyBatis Generator](http://mybatis.org/generator/)  


## SQL脚本
```sql
create table USER
(
    ID           INT auto_increment,
    ACCOUNT_ID   VARCHAR(100),
    NAME         VARCHAR(50),
    TOKEN        CHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    constraint USER_PK
        primary key (ID)
);
```
```sql
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```
