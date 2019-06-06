#!/bin/sh
groupadd -g $GID go
useradd -ms /bin/bash go -u $UID -g $GID
exec /usr/local/bin/gosu go "$@"