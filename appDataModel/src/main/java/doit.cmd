set M2REPO=c:\users\r23ak\.m2\repository
set CLASSPATH=%M2REPO%\org\mybatis\generator\mybatis-generator-core\1.3.2\mybatis-generator-core-1.3.2.jar;%M2REPO%\commons-logging\commons-logging\1.1.1\commons-logging-1.1.1.jar;%M2REPO%\log4j\log4j\1.2.9\log4j-1.2.9.jar;%M2REPO%\mysql\mysql-connector-java\5.1.9\mysql-connector-java-5.1.9.jar
java org.mybatis.generator.api.ShellRunner -configfile ..\..\..\src\main\java\com\rstech\wordwatch\dao\dtcc_csc241.xml -verbose –overwrite
