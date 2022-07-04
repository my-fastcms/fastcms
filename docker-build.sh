#!/bin/bash
# abort on errors
set -e

version="$1"

if [[ "$version" == "" ]]; then
	echo "./please designated docker image version"
	exit 0
fi

echo "exec : docker build . -t wangjun/fastcms:"${version}
docker build . -t wangjun/fastcms:${version}
