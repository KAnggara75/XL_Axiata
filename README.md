# API Microservice Mini Hackaton

## Use Case: Query Subscriber Monthly Transaction

![Subscriber Monthly Transaction](./Subscriber%20Monthly%20Transaction.png)

### Context:  
As a software engineer, we have request from Touchpoint team to provide new API to get subscriber monthly transaction summary. Touchpoint only need total amount and total transaction for each month. For security measurement we need to validate subscriber PIN.

### Task:
*	Create sequence diagram of the API’s journey (sequencediagram.org).
*	Enhance provided service to create working API according to context.

### Plus Point:
*	Unit test
*	Readability and traceability code
*	Simplicity

### Pre-requisites:  
*	Java 17
*	Apache Maven 3.9.4
*	MySQL 8.0.37
*	Lombok Plugin

### Resource Detail:
*	subscriber-transaction
*	DB dump (xl_playground)
*	Table name: transaction
*	Valid MSISDN: 62819123456
*	Sample API Success Response

#### 200 OK

```json
{
	"status": "ok",
	"code": "00",
	"message": "success",
	"data": [
		{
			"month": "2024-04",
			"totalAmount": 100000,
			"totalTransaction": 10
		},
		{
			"month": "2024-03",
			"totalAmount": 90000,
			"totalTransaction": 80
		}
	]
}
```

### GET Pin

#### Valid Response 200

```bash
curl --location 'https://4k38m.wiremockapi.cloud/subscriber/62819123456/pin'
```

```json
{
	"status": "ok",
	"code": "00",
	"message": "success",
	"data": "1234"
}
```

#### Invalid Response 404

```bash
curl --location 'https://4k38m.wiremockapi.cloud/subscriber/628191/pin'
```

```json
{
	"status": "failed",
	"code": "01",
	"message": "subscriber not found"
}
```