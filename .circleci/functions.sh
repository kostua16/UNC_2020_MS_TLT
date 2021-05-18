#!/usr/bin/env bash
generate_common_vars() {
  export TAG=branch_${CIRCLE_BRANCH//\//_}.latest
  export DEV_TAG=branch_develop.latest
}

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
  generate_common_vars
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
  if [[ -z "${CACHE_FROM}" ]]; then
    docker build -f "${DOCKER_FILE}" -t "${IMAGE_NAME}:${TAG}" -t "${IMAGE_NAME}:latest" .
  else
    docker build ${CACHE_FROM} -f "${DOCKER_FILE}" -t "${IMAGE_NAME}:${TAG}" -t "${IMAGE_NAME}:latest" .
  fi
  docker build ${CACHE_FROM} -f "${DOCKER_FILE}" -t "${IMAGE_NAME}:${TAG}" -t "${IMAGE_NAME}:latest" .
  docker push "${IMAGE_NAME}:${TAG}"
  docker push "${IMAGE_NAME}:latest"
}
build_service() {
  PROJECT=${1}
  HEROKU_APP_NAME=${2}
  IMAGE_NAME=kostua16/unc_2020_${PROJECT}
  HEROKU_IMAGE_NAME=registry.heroku.com/${HEROKU_APP_NAME}
  CACHE_FROM=""
  generate_common_vars
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
  if [[ -z "${CACHE_FROM}" ]]; then
    docker build -f backend.production.Dockerfile --build-arg "PROJECT=${PROJECT}" -t "${IMAGE_NAME}:${TAG}" -t "${HEROKU_IMAGE_NAME}/web" -t "${IMAGE_NAME}:latest" .
  else
    docker build ${CACHE_FROM} -f backend.production.Dockerfile --build-arg "PROJECT=${PROJECT}" -t "${IMAGE_NAME}:${TAG}" -t "${HEROKU_IMAGE_NAME}/web" -t "${IMAGE_NAME}:latest" .
  fi
  docker push "${IMAGE_NAME}:${TAG}"
  docker push "${IMAGE_NAME}:latest"
  docker_heroku_login
  docker push "${HEROKU_IMAGE_NAME}/web"
  heroku container:release -a "${HEROKU_APP_NAME}" web
}
build_ui() {
  IMAGE_NAME=kostua16/unc_2020_frontend
  HEROKU_APP_NAME=nc-edu-2020-ui
  HEROKU_IMAGE_NAME=registry.heroku.com/${HEROKU_APP_NAME}
  CACHE_FROM=""
  generate_common_vars
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
  if [[ -z "${CACHE_FROM}" ]]; then
    docker build -f client-ui/prod.Dockerfile -t "${IMAGE_NAME}:${TAG}" -t "${HEROKU_IMAGE_NAME}/web" -t "${IMAGE_NAME}:latest" ./client-ui
  else
    docker build ${CACHE_FROM} -f client-ui/prod.Dockerfile -t "${IMAGE_NAME}:${TAG}" -t "${HEROKU_IMAGE_NAME}/web" -t "${IMAGE_NAME}:latest" ./client-ui
  fi
  docker push "${IMAGE_NAME}:${TAG}"
  docker push "${IMAGE_NAME}:latest"
  docker_heroku_login
  docker push "${HEROKU_IMAGE_NAME}/web"
  heroku container:release -a "${HEROKU_APP_NAME}" web
}

