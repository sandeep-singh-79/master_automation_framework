#!/bin/sh

# Start Xvfb only if enabled
if [ "$USE_XVFB" = "true" ]; then
  echo "Starting Xvfb on display :99..."
  Xvfb :99 -screen 0 1920x1080x24 &
  export DISPLAY=:99
else
  echo "Running without Xvfb..."
fi

# Execute Maven command
echo "Running command: mvn $@"
mvn "$@"