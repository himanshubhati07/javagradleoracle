#!/usr/bin/env bash
set -euo pipefail
SERVER_PORT=40697
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"
./gradlew bootJar -q
JAR_FILE=$(ls build/libs/*.jar | head -n 1)
exec java -jar "$JAR_FILE" --server.port=40697
