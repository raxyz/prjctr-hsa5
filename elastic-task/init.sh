#!/bin/bash

echo "Start script"

echo "Delete index"

curl -s -o ./null -X DELETE http://localhost:9200/english-dictionary

echo "Create index"

curl -s -o ./null -XPUT http://localhost:9200/english-dictionary?pretty=true -H 'Content-Type: application/json' -d '
{
  "settings": {
        "index": {
            "analysis": {
                "analyzer": {
                    "analyzer_startswith": {
                        "tokenizer": "keyword",
                        "filter": "lowercase"
                    }
                }
            }
        }
    },
    "mappings": {
        "properties": {
            "word": {
                "type": "text",
                "analyzer": "analyzer_startswith"
            }
        }
    }
}'

echo "Index created"

dictionary=$(curl https://raw.githubusercontent.com/dwyl/english-words/master/words.txt)

echo "Data loaded"

echo "" > upload.json

count=0

for i in ${dictionary[@]}
do 
    printf "{\"index\":{}}\n{\"word\": \"$i\"}\n" >> upload.json
    if (( $count > 50000 )); then 
        curl -s -o ./null -X POST http://localhost:9200/english-dictionary/_bulk -H 'Content-Type: application/json'  --data-binary @upload.json
        echo "Sent 50k records"
        echo "" > upload.json
        count=$((0))
    fi
    count=$((count + 1))
done

echo "Sent last $count records"

curl -s -o ./null -X POST http://localhost:9200/english-dictionary/_bulk -H 'Content-Type: application/json'  --data-binary @upload.json

echo "Data uploaded to Elasticsearch"

rm -f upload.json
rm -f null

echo "File removed"