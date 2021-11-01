#!/bin/bash

# Upload Dashboard to Grafana
grafanaDashboard=$(<./init/grafana-dashboard.json)
curl -X POST http://admin:admin@localhost:3000/api/dashboards/db -H 'Accept: application/json' -H 'Content-Type: application/json' -d "{\"dashboard\":$grafanaDashboard}"

echo "Dashboard created"

# Upload Datasource to Grafana
grafanaDatasource=$(<./init/grafana-datasource.json)
curl -X POST http://admin:admin@localhost:3000/api/datasources -H 'Accept: application/json' -H 'Content-Type: application/json' -d "$grafanaDatasource"

echo "Datasource created"
echo "======="

# Init data for the app
for ((i=1;i<=20;i++)); do
firstName="First Name $(( ( RANDOM % 1000 )  + 1 ))"
lastName="Last Name $(( ( RANDOM % 1000 )  + 1 ))"

curl --request POST \
  --url http://localhost:8080/api/v1/users \
  --header 'content-type: application/json' \
  --data "{\"firstName\": \"$firstName\",\"lastName\": \"$lastName\"}"
  
done

echo "Test data uploaded"