# Consumer (RESTful)

Application that is designed to communicate with ThirdPartyService

* [ThirdPartyService] https://github.com/SergejJerma/ThirdPartyService-to-Consumer-2021
 application that simulates a third party service. This service has two endpoints exposed:
        * POST “/thirdpartyservice/init” which accepts no parameters but returns you a UUID of a new item created and stored in that application (runtime in ConcurrentHashMap). Note: this POST method simulates long taking operations (i.e. legacy system) and response time might differ each time;
        * GET “/thirdpartyservice/checkStatus/{uuid}" that accepts path variable UUID and returns the processing status (OK, IN_PROGRESS, FAILED) of a particular item.


### Back-end
```
Java 11
Spring Boot 
Maven
Lombok 
Posgres
```

## Solution
Consumer application is prepared to receive hundreds of client requests at the same time, to create items and return them as soon as they are in status OK.
*POST CONTENT -> ITEM UUID -> new ITEM -> DB (save)
*CRON -> DB -> ITEM(IN_PROGRESS) -> GET ITEM STATUS -> DB (update)
*GET CONTENT -> DB -> ITEM(OK)

```
API: dokcumentation Swagger UI http://localhost:8038/swagger-ui/
