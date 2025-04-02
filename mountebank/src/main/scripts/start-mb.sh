#!/bin/bash

echo "Starting Mountebank..."
mb --port 2525 --configfile src/main/resources/mb-configs/mb-config.json