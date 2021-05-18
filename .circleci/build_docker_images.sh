#!/usr/bin/env bash
set -e
set -x
TAG=branch_${CIRCLE_BRANCH//\//_}.latest
DEV_TAG=branch_develop.latest
docker_hub_login() {
  echo $DOCKER_PASSWORD | docker login -u $DOCKER_LOGIN --password-stdin
}
docker_heroku_login() {
  heroku container:login
}
build_baseline() {
  BASELINE_NAME=${1}
  DOCKER_FILE=${2}
  IMAGE_NAME=kostua16/${BASELINE_NAME}
  CACHE_FROM=""
  docker_hub_login
  docker pull "${IMAGE_NAME}:${TAG}" || true
  if [[ "$(docker images -q ${IMAGE_NAME}:${TAG} 2> /dev/null)" == "" ]]; then
    CACHE_FROM="--cache-from ${IMAGE_NAME}:${TAG}"
  else
    docker pull "${IMAGE_NAME}:${DEV_TAG}" || true
    if [[ "$(docker images -q ${IMAGE_NAME}:${DEV_TAG} 2> /dev/null)" == "" ]]; then
      CACHE_FROM="--cache-from ${IMAGE_NAME}:${DEV_TAG}"
    else
      docker pull "${IMAGE_NAME}:latest" || true
      if [[ "$(docker images -q ${IMAGE_NAME}:latest 2> /dev/null)" == "" ]]; then
        CACHE_FROM="--cache-from ${IMAGE_NAME}:latest"
      fi
    fi
  fi
  docker build "${CACHE_FROM}" -f "${DOCKER_FILE}" -t "${IMAGE_NAME}:${TAG}" -t "${IMAGE_NAME}:latest" .
  docker push "${IMAGE_NAME}:${TAG}"
  docker push "${IMAGE_NAME}:latest"
}
build_service() {
  PROJECT=${1}
  IMAGE_NAME=kostua16/unc_2020_${PROJECT}
  HEROKU_IMAGE_NAME=registry.heroku.com/${2}
  CACHE_FROM=""
  docker_hub_login
  docker pull "${IMAGE_NAME}:${TAG}" || true
  if [[ "$(docker images -q ${IMAGE_NAME}:${TAG} 2> /dev/null)" == "" ]]; then
    CACHE_FROM=--cache-from "${IMAGE_NAME}:${TAG}"
  else
    docker pull "${IMAGE_NAME}:${DEV_TAG}" || true
    if [[ "$(docker images -q ${IMAGE_NAME}:${DEV_TAG} 2> /dev/null)" == "" ]]; then
      CACHE_FROM=--cache-from "${IMAGE_NAME}:${DEV_TAG}"
    else
      docker pull "${IMAGE_NAME}:latest" || true
      if [[ "$(docker images -q ${IMAGE_NAME}:latest 2> /dev/null)" == "" ]]; then
        CACHE_FROM=--cache-from "${IMAGE_NAME}:latest"
      fi
    fi
  fi
  docker build "${CACHE_FROM}" -f backend.production.Dockerfile --build-arg "PROJECT=${PROJECT}" -t "${IMAGE_NAME}:${TAG}" -t "${HEROKU_IMAGE_NAME}/web" -t "${IMAGE_NAME}:latest" .
  docker push "${IMAGE_NAME}:${TAG}"
  docker push "${IMAGE_NAME}:latest"
  docker_heroku_login
  docker push "${HEROKU_IMAGE_NAME}/web"
  heroku container:release -a "${HEROKU_IMAGE_NAME}" web
}
build_ui() {
  IMAGE_NAME=kostua16/unc_2020_frontend
  HEROKU_IMAGE_NAME=registry.heroku.com/nc-edu-2020-ui
  CACHE_FROM=""
  docker_hub_login
  docker pull "${IMAGE_NAME}:${TAG}" || true
  if [[ "$(docker images -q ${IMAGE_NAME}:${TAG} 2> /dev/null)" == "" ]]; then
    CACHE_FROM="--cache-from ${IMAGE_NAME}:${TAG}"
  else
    docker pull "${IMAGE_NAME}:${DEV_TAG}" || true
    if [[ "$(docker images -q ${IMAGE_NAME}:${DEV_TAG} 2> /dev/null)" == "" ]]; then
      CACHE_FROM="--cache-from ${IMAGE_NAME}:${DEV_TAG}"
    else
      docker pull "${IMAGE_NAME}:latest" || true
      if [[ "$(docker images -q ${IMAGE_NAME}:latest 2> /dev/null)" == "" ]]; then
        CACHE_FROM="--cache-from ${IMAGE_NAME}:latest"
      fi
    fi
  fi
  docker build "${CACHE_FROM}" -f client-ui/prod.Dockerfile -t "${IMAGE_NAME}:${TAG}" -t "${HEROKU_IMAGE_NAME}/web" -t "${IMAGE_NAME}:latest" ./client-ui
  docker push "${IMAGE_NAME}:${TAG}"
  docker push "${IMAGE_NAME}:latest"
  docker_heroku_login
  docker push "${HEROKU_IMAGE_NAME}/web"
  heroku container:release -a "${HEROKU_IMAGE_NAME}" web
}

build_baseline unc_2020_backend_base baseline.back.Dockerfile
build_baseline unc_2020_frontend_base baseline.frontend.Dockerfile
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

