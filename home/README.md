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
