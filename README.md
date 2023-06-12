# Car Dealer Listing API
The following rest service exposes car dealer's listings API build in Java/Springboot
### Pré-réquis technologique
* Java 17
* Maven 3.9.2
* MySQL 8.0.30

# Getting Started
This car dealer Rest API endpoints requres a user to authenticat
The authentication type is JWT.

## How to run the project

To run the project follow these steps

* Download the zipped package and then unzipping the project
* Clone the project from github
`git clone https://github.com/ernestteoro/car-dealer-app`
* Go to the project folder `cd car-dealer-app`
* Build the application
`mvn clean package`
* Now run the appliation 
`java -jar target/car-dealer-0.0.1.jar`
* Check the documentation from [here](http://localhost:8082/swagger-ui/index.html), by default the app runs on port 8082.
## Test the endpoints
1. Create a user
    * Http method: `POST`
    * Endpoint: `localhost:8082/api/v1/auth/signup`
    * Request Body(User data):
`{
    "username":"ernesto",
    "password": "ernesto",
    "isLogin": 0,
    "rolesUsersByIdUsers": [
        {"roles": "ADMIN"}
    ]
}`
* Response containing the token:
`{
    "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlcm5lc3RvIiwiaWF0IjoxNjg2MzE1MzE3LCJleHAiOjE3MTc4NzIyNjl9.4cTWLCsKCnT-gnTHyGCrgXUy_-_Bsuo1Ac8NNcwAQts",
    "refresh_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlcm5lc3RvIiwiaWF0IjoxNjg2MzE1MzE3LCJleHAiOjE2ODY5MjAxMTd9.XX3rs_g_xQHGtUFtkEO-3xCS0RNR6wLYV3jZHVB8NwQ",
    "username": "ernesto"
}`
2. Add a Dealer
* Http method: `POST`
* Endpoint: `localhost:8082/api/dealers`
* Copy the token from the user creation response and then add it to whatever tool you are using for testing
If Postman then go to `Authorization` and choose `Bearer Token` and paste the copied token in the field on the right.
* Request Body: `{
    "name": "Ernest",
    "tierLimit": 10
}`
3. Add a listing
* Http method: `POST`
* Endpoint: `localhost:8082/api/listings/{dealerId}/add`
* Copy the token from the user creation response and then add it to whatever tool you are using for testing
If Postman then go to `Authorization` and choose `Bearer Token` and paste the copied token in the field on the right.
* Request Body: `{
    "vehicle": "BMW",
    "price": 150250
}`
4. Publish a listing(staying in tier limit)
* Http method: `POST`
* Endpoint: `localhost:8082/api/listings/{listingId}/publish?inTierLimit=true`
* Copy the token from the user creation response and then add it to whatever tool you are using for testing
If Postman then go to `Authorization` and choose `Bearer Token` and paste the copied token in the field on the right.
5. Unpublish a listing
* Http method: `POST`
* Endpoint: `localhost:8082/api/listings/{listingId}/unpublish`
* Copy the token from the user creation response and then add it to whatever tool you are using for testing
If Postman then go to `Authorization` and choose `Bearer Token` and paste the copied token in the field on the right.
