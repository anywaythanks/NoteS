# es-init/Dockerfile
FROM python:3.9-slim

# Install system dependencies
RUN apt-get update && \
    apt-get install -y curl g++ make && \
    rm -rf /var/lib/apt/lists/*

# Install Python dependencies with exact versions
RUN pip install --no-cache-dir \
    'eland[pytorch]==8.13' \
    'tqdm==4.66.1'