# 1

## Project Links

- <https://docs.google.com/document/d/1fxoeF2Km8R_XsSQAPygTtu_ChfAsN0_PbFhFM4acZUs/edit#heading=h.rwie5j6trm48>
- <https://app.diagrams.net/#G1Y60H89koJr6YISKXPoL6rqFgUSyfdsol#%7B%22pageId%22%3A%22sBIROoyu85cNoxN56vfw%22%7D>

## 2

- One service setting up the topic as well as the queue
- One publisher
- One Consumer
- <https://www.quartz-scheduler.org/documentation/2.4.0-SNAPSHOT/tutorials/tutorial-lesson-08.html>

```
java -cp target/setup-1.0.jar SimpleKafkaProducer
java -cp target/setup-1.0.jar SimpleKafkaConsumer
mvn exec:java -Dexec.mainClass="com.app.SimpleKafkaProducer"
mvn exec:java -Dexec.mainClass="com.app.SimpleKafkaConsumer"
mvn exec:java -Dexec.mainClass="com.app.KafkaController"
mvn exec:java -Dexec.mainClass="com.app.MatchMakingService" -Dexec.args="TEST2"
mvn exec:java -Dexec.mainClass="com.app.JobSchedular"
mvn spring-boot:run
```