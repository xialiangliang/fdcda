###### 添加 /opt/config/dbConfig.properties 文件，内容如下(test)
    jdbc.driver=oracle.jdbc.driver.OracleDriver
    jdbc.url=jdbc:oracle:thin:@61.153.254.69:3521:orcl
    jdbc.username=user_dev
    jdbc.password=user_dev
    jdbc.initialSize=0
    jdbc.maxActive=20
    jdbc.maxIdle=20
    jdbc.minIdle=1
    jdbc.maxWait=60000

###### 添加 /opt/config/redisConfig.properties 文件，内容如下(test)
    redis.host=127.0.0.1
    redis.port=6379
    redis.password=
    redis.maxIdle=300
    redis.maxTotal=600
    redis.MaxWaitMillis=2000
    redis.timeout=2000
    redis.testOnBorrow=true
    redis.db.index=0
    
###### 添加 /opt/config/urlConfig.properties 文件，内容如下(test)
    url.web_address=http://localhost