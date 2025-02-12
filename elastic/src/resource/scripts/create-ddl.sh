#!/bin/bash
echo "Create ddl start!" 

curl -X PUT -H "Content-Type: application/json" \
     --data-binary @/resource/config/embedding_pipeline.json \
     http://elasticsearch:9200/_ingest/pipeline/rubert-embeddings

curl -X PUT -H "Content-Type: application/json" \
     --data-binary @/resource/config/notes_index.json \
     http://elasticsearch:9200/notes


echo "Create ddl finish!" 