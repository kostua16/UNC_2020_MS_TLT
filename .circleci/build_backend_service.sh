#!/usr/bin/env bash
source .circleci/functions.sh
set -e
build_service ${1} ${2}

