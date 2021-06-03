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
  time docker pull "${IMAGE_NAME}:${TAG}" || true
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
    time docker build -f "${DOCKER_FILE}" -t "${IMAGE_NAME}:${TAG}" -t "${IMAGE_NAME}:latest" .
  else
    time docker build ${CACHE_FROM} -f "${DOCKER_FILE}" -t "${IMAGE_NAME}:${TAG}" -t "${IMAGE_NAME}:latest" .
  fi
  time docker push "${IMAGE_NAME}:${TAG}"
  time docker push "${IMAGE_NAME}:latest"
}
build_service() {
  PROJECT=${1}
  HEROKU_APP_NAME=${2}
  IMAGE_NAME=kostua16/unc_2020_${PROJECT}
  HEROKU_IMAGE_NAME=registry.heroku.com/${HEROKU_APP_NAME}
  CACHE_FROM=""
  generate_common_vars
  docker_hub_login
  echo "------------- [BUILD_SERVICE] ${PROJECT} 1/6 -------------"
  time docker pull "${IMAGE_NAME}:${TAG}" || true
  if [[ "$(docker images -q ${IMAGE_NAME}:${TAG} 2> /dev/null)" == "" ]]; then
    CACHE_FROM="--cache-from ${IMAGE_NAME}:${TAG}"
  else
    time docker pull "${IMAGE_NAME}:${DEV_TAG}" || true
    if [[ "$(docker images -q ${IMAGE_NAME}:${DEV_TAG} 2> /dev/null)" == "" ]]; then
      CACHE_FROM="--cache-from ${IMAGE_NAME}:${DEV_TAG}"
    else
      time docker pull "${IMAGE_NAME}:latest" || true
      if [[ "$(docker images -q ${IMAGE_NAME}:latest 2> /dev/null)" == "" ]]; then
        CACHE_FROM="--cache-from ${IMAGE_NAME}:latest"
      fi
    fi
  fi
  echo "------------- [BUILD_SERVICE] ${PROJECT} 2/6 -------------"
  if [[ -z "${CACHE_FROM}" ]]; then
    time docker build -f backend.production.Dockerfile --build-arg "PROJECT=${PROJECT}" -t "${IMAGE_NAME}:${TAG}" -t "${HEROKU_IMAGE_NAME}/web" -t "${IMAGE_NAME}:latest" .
  else
    time docker build ${CACHE_FROM} -f backend.production.Dockerfile --build-arg "PROJECT=${PROJECT}" -t "${IMAGE_NAME}:${TAG}" -t "${HEROKU_IMAGE_NAME}/web" -t "${IMAGE_NAME}:latest" .
  fi
  echo "------------- [BUILD_SERVICE] ${PROJECT} 3/6 -------------"
  time docker push "${IMAGE_NAME}:${TAG}"
  echo "------------- [BUILD_SERVICE] ${PROJECT} 4/6 -------------"
  time docker push "${IMAGE_NAME}:latest"
  docker_heroku_login
  echo "------------- [BUILD_SERVICE] ${PROJECT} 5/6 -------------"
  time docker push "${HEROKU_IMAGE_NAME}/web"
  echo "------------- [BUILD_SERVICE] ${PROJECT} 6/6 -------------"
  time heroku container:release -a "${HEROKU_APP_NAME}" web
}
build_ui() {
  IMAGE_NAME=kostua16/unc_2020_frontend
  HEROKU_APP_NAME=nc-edu-2020-ui
  HEROKU_IMAGE_NAME=registry.heroku.com/${HEROKU_APP_NAME}
  CACHE_FROM=""
  generate_common_vars
  docker_hub_login
  echo "------------- [BUILD_UI] 1/6 -------------"
  time docker pull "${IMAGE_NAME}:${TAG}" || true
  if [[ "$(docker images -q ${IMAGE_NAME}:${TAG} 2> /dev/null)" == "" ]]; then
    CACHE_FROM="--cache-from ${IMAGE_NAME}:${TAG}"
  else
    time docker pull "${IMAGE_NAME}:${DEV_TAG}" || true
    if [[ "$(docker images -q ${IMAGE_NAME}:${DEV_TAG} 2> /dev/null)" == "" ]]; then
      CACHE_FROM="--cache-from ${IMAGE_NAME}:${DEV_TAG}"
    else
      time docker pull "${IMAGE_NAME}:latest" || true
      if [[ "$(docker images -q ${IMAGE_NAME}:latest 2> /dev/null)" == "" ]]; then
        CACHE_FROM="--cache-from ${IMAGE_NAME}:latest"
      fi
    fi
  fi
  echo "------------- [BUILD_UI] 2/6 -------------"
  if [[ -z "${CACHE_FROM}" ]]; then
    time docker build -f client-ui/prod.Dockerfile -t "${IMAGE_NAME}:${TAG}" -t "${HEROKU_IMAGE_NAME}/web" -t "${IMAGE_NAME}:latest" ./client-ui
  else
    time docker build ${CACHE_FROM} -f client-ui/prod.Dockerfile -t "${IMAGE_NAME}:${TAG}" -t "${HEROKU_IMAGE_NAME}/web" -t "${IMAGE_NAME}:latest" ./client-ui
  fi
  echo "------------- [BUILD_UI] 3/6 -------------"
  time docker push "${IMAGE_NAME}:${TAG}"
  echo "------------- [BUILD_UI] 4/6 -------------"
  time docker push "${IMAGE_NAME}:latest"
  docker_heroku_login
  echo "------------- [BUILD_UI] 5/6 -------------"
  time docker push "${HEROKU_IMAGE_NAME}/web"
  echo "------------- [BUILD_UI] 6/6 -------------"
  time heroku container:release -a "${HEROKU_APP_NAME}" web
}

