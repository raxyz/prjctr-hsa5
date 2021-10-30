#!/bin/bash

grafanaDashboard=$(<./init/grafana-dashboard.json)

curl -X POST http://admin:admin@localhost:3000/api/dashboards/db -H 'Accept: application/json' -H 'Content-Type: application/json' -d "{\"dashboard\":$grafanaDashboard}"
