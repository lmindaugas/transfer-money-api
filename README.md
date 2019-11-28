# Money Transfer API

This API transfers money between two accounts. The API uses frameworks only, like Spark, JOOQ.    

# Requirements for API

Design and implement a RESTful API (including data model and the backing implementation) for money transfers between accounts.

1. You can use Java or Kotlin. 
2. Keep it simple and to the point (e.g. no need to implement any authentication). 
3. Assume the API is invoked by multiple systems and services on behalf of end users. 
4. You can use frameworks/libraries if you like ( except Spring ), but don't forget about  requirement #2 and keep it simple and avoid heavy frameworks. 
5. The datastore should run in-memory for the sake of this test. 
6. The final result should be executable as a standalone program (should not require a  pre-installed container/server). 
7. Demonstrate with tests that the API works as expected. 
8. The code produced by you is expected to be of high quality. 
9. There are no detailed requirements, use common sense.  

## Executing API

    Run the command to start the server:
    java -jar transfer-money-api.jar

* API requires 11 or newer version of JAVA installed on local machine. 


## Building API

API is built using [Apache Maven](https://maven.apache.org/).
To build API run:

    ./build/mvn -DskipTests clean package

## API Endpoints

    - POST /payments
    - GET /payments
    - GET /payments/{paymentId}
    
    - POST /accounts
    - GET /accounts
    - GET /accounts/{accountId}

## Execute Payment

To execute a payment call POST /payments endpoint with the json payload:

    {
        "Data": {
            "InstructedAmount": {
                "Amount": "50.00",
                "Currency": "EUR"
            },
            "DebtorAccount": {
                "SchemeName": "BBAN",
                "Identification": "DK120000400440116243",
                "Name": "Joe T"
            },
            "CreditorAccount": {
                "SchemeName": "BBAN",
                "Identification": "GB371205100000309657",
                "Name": "Jesica P"
            }
        }
    }


Before the payment is executed, some validations must be applied. Both accounts (creditor and debtor) should be present and exist in DB. In order to create new accounts, use the endpoint:

    POST /accounts

and provide the payload: 

    {
        "Id": "LT470000600440112909",
        "Name": "Mindaugas",
        "Balance": "99.00",
        "Currency": "EUR",
        "Status": "ACTIVE"
    }

For the sake of simplicity, the API only checks if all required fields are set properly, but it doesn't make any complicated validations.