#!/bin/sh

# Enable strict mode for better error handling
set -e

# Optionally log detected browser paths
echo "CHROME: $CHROME_BIN"
echo "FIREFOX: $FIREFOX_BIN"

# Start Xvfb only if enabled
if [ "$USE_XVFB" = "true" ]; then
  echo "Starting Xvfb on display :99..."
  Xvfb :99 -screen 0 1920x1080x24 &
  export DISPLAY=:99
else
  echo "Running without Xvfb..."
fi

# Check if any command is passed
if [ $# -eq 0 ]; then
  echo "No Maven command specified. Exiting..."
  exit 1
fi

# Show the full command being executed
echo "Running: mvn $*"
exec mvn "$@"
