# integrationChallenge

**How to build**

```$>gradlew clean build```

the war file will be located under:

*/build/libs/hh-integration-challenge.war*

Note: The app can also by run locally with jetty using:
 ```$>gradlew jettyRunWar```

**Necessary improvements**

 - improve unit tests coverage
 - implement better data management system than file based
 - read the cancel event from appDirect and delete that entry from file DB
 - improve frontend UI
 - generate AppDirect domain objects from schemas (need to ask where to get them)
 - implement remaining APIs



