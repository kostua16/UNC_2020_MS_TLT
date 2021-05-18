#!/usr/bin/env bash
source .circleci/functions.sh
set -e
set -x

build_service discovery nc-edu-2020-discovery
build_service config nc-edu-2020-config
build_service proxy nc-edu-2020-proxy
build_service logging nc-edu-2020-logger
build_service tax nc-edu-2020-tax
build_service gibdd nc-edu-2020-gibdd
build_service account nc-edu-2020-account
build_service bank nc-edu-2020-bank
build_service communal nc-edu-2020-communal
build_service passport nc-edu-2020-passport
build_ui

