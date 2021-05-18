#!/usr/bin/env bash
source .circleci/functions.sh
set -e

build_baseline unc_2020_backend_base baseline.back.Dockerfile

