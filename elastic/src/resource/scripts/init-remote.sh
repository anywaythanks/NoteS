#!/bin/bash

echo "Pre-cached models.."
python -c "
from transformers import AutoModel, AutoTokenizer
AutoModel.from_pretrained('cointegrated/rubert-tiny2')
AutoTokenizer.from_pretrained('cointegrated/rubert-tiny2')
"

#хотелось бы deepvk/USER-bge-m3

echo "activate trial..."
curl -X POST http://clearlaptop:9200/_license/start_trial?acknowledge=true

# Wait for ES (using service name from compose network)
until curl -s http://clearlaptop:9200; do
  echo "Waiting for Elasticsearch..."
  sleep 5
done

# Import E5 model
echo "Importing rubert-tiny2..."
eland_import_hub_model \
  --url http://clearlaptop:9200 \
  --hub-model-id cointegrated/rubert-tiny2 \
  --task-type text_embedding \
  --start

echo "Load model finish!" 