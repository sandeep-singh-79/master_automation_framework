# URL for contract test
# ---------------------
URL="http://localhost:9000/"

# ----------------------------------------------------------------------------------------------- #
# ----------------------------------------------------------------------------------------------- #

# Check for FE server to be up. If not, exit with error.
# -----------------------------------------------------

POLL_FREQUENCY=5
MAX_POLL_COUNT=24
POLL_COUNT=0
#curl -sL $url -w "%{http_code}" -o /dev/null
until $(curl --output /dev/null --silent --head --fail $URL); do

    printf '.'

    POLL_COUNT=`expr $POLL_COUNT + 1`

    if [ $POLL_COUNT -eq $MAX_POLL_COUNT ]; then
	    printf '\n'
	    echo "Timeout after $(($POLL_FREQUENCY * $MAX_POLL_COUNT)) seconds."
	    exit -1
	fi

    sleep $POLL_FREQUENCY
    
done

printf '\n'
echo "Frontend server running at $URL"
printf '\n'

# ----------------------------------------------------------------------------------------------- #
# ----------------------------------------------------------------------------------------------- #

# set URL in context to FE container
FE_CONTAINER="cnFe"

URL="http://$FE_CONTAINER:9000/"

printf '\n'
echo "Running tests against $URL"
printf '\n'

# untar chrome profile for selenium
tar -xf ./src/test/resources/chrome-profiles/contract-test.tar -C ./src/test/resources/chrome-profiles/

# run docker container for selenium

if [ "$#" -eq 0 ]; then
    docker run --link $FE_CONTAINER --rm -e DISPLAY=unix:11 -e UID=`id -u` -e GID=`id -g` -v "$PWD":/usr/src/mymaven -w /usr/src/mymaven -v /tmp/.X11-unix:/tmp/.X11-unix `cat automation_image` mvn clean test -Denv=local -Durl=$URL -DlocalMode="mock" -Dbrowser=chrome -DUserFactiva="userFactiva" -DPasswordFactiva="passwordFactiva" -DTestUser=Yes -Dcucumber.options="--tags @contracttest"
else
    pkill -9 socat
    socat TCP-LISTEN:6000,reuseaddr,fork UNIX-CLIENT:\"$DISPLAY\" 2>&1 > /dev/null &
    xhost +
    docker run --link $FE_CONTAINER --rm -v ~/.m2:/home/go/.m2 -v /tmp/.X11-unix:/tmp/.X11-unix -e DISPLAY=$1 -e UID=`id -u` -e GID=`id -g` -v "$PWD":/usr/src/mymaven -w /usr/src/mymaven 10.191.60.17:5000/mck/cn-automation:latest mvn clean test -Denv=local -DlocalUrl=$URL -DlocalMode="mock" -Dbrowser=chrome -DUserFactiva="userFactiva" -DPasswordFactiva="passwordFactiva" -DTestUser=Yes -Dcucumber.options="--tags @contracttest"
fi
