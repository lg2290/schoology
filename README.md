# Schoology - Autocomplete Backend Task

This repository contains a Java 13 / SpringBoot application, developed as a Backend for an autocomplete component.

### Get the Code
Clone the repo:
```bash
git clone
```

### Tests


### Execute
To execute the application, navigate to the root folder of the application, and then folow the comamands below

#### Via gradle
```bash
# linux
./gradlew bootRun

# windows
gradlew.abt bootRun
```

#### Via Docker
```bash
docker build . -t autocomplete:1.0

docker run -p 8080:8080 autocomplete:1.0
```

## Request

The application has a single endpoint, `/v1/users/names`, which returns a list of names. 
The names available can be found in the file [src/main/resources/names.sql]().

The endpoint accepts one parameter, `nameFilter`:
* It is optional - if not present, then all the names are returned
* If a value is assigned, only the names that contains the exactly value will be returned
* Spaces in the start and/or end of the value will not be considered when filtering

### Examples
Get all the names:
```bash
curl localhost:8080/v1/users/names
```

Get the names that contains the text "a a"
```bash
curl localhost:8080/v1/users/names?nameFilter=a%20a
```

## Response

The response JSON will contain two fields: `timestamp`, with the date/time from the request, and `responseData` with the list of names:
```json
{
  "timestamp": "2020-02-09T23:01:32.786745Z",
  "responseData": [
    "Richard Harris",
    "Leah Duncan",
    "Sophie Short",
    "..."
  ]
}
```

## Improvements
* Add error handling via ControllerAdvice
* Add custom exceptions and custom Error Response

