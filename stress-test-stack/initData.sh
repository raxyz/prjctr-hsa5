#!/bin/bash

# Init data for the app
for ((i=1;i<=10;i++)); do
firstName="First Name $(( ( RANDOM % 1000 )  + 1 ))"
lastName="Last Name $(( ( RANDOM % 1000 )  + 1 ))"

curl --request POST \
  --url http://localhost:8080/api/v1/customers \
  --header 'content-type: application/json' \
  --data "{\"firstName\": \"$firstName\",\"lastName\": \"$lastName\"}"
  
done

echo "Test data uploaded"
