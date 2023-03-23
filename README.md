# retail-store-service

This sample SpringBoot Microservice provides various REST APIs for managing Online Retail Store. It supports following uses cases:

    1. Inventory management - on board/update retail items (Electronics, Grocery etc.). Paginated and sorted items list, list by category etc.
    2. User management - On board/update users/account. List/query users details.
    3. Order management - place/cancel/update orders with billing information. List/query order detail using Id, userId etc.
    4. Payment management (not supported, needs to be handled in the front end or expose different set of APIs)
    
The database design script (supports MySQL) is also included as part of this application. This application can work with any RDBMS technology.

Here is the Swagger url for various REST endpoint on local: http://localhost:8081/swagger-ui.html
