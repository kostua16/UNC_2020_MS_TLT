#!/usr/bin/env bash
echo $DOCKER_PASSWORD | docker login -u $DOCKER_LOGIN --password-stdin
TAG=branch_${CIRCLE_BRANCH//\//_}.latest
DEV_TAG=branch_develop.latest
#TAG_DETAILED=branch_${CIRCLE_BRANCH//\//_}.${CIRCLE_BUILD_NUM}

docker pull kostua16/unc_2020_backend_base:${TAG} || true
if [[ "$(docker images -q kostua16/unc_2020_backend_base:${TAG} 2> /dev/null)" == "" ]]; then
  docker build --cache-from kostua16/unc_2020_backend_base:${TAG} -f baseline.back.Dockerfile -t kostua16/unc_2020_backend_base:${TAG} .
else
  docker pull kostua16/unc_2020_backend_base:${DEV_TAG} || true
  if [[ "$(docker images -q kostua16/unc_2020_backend_base:${DEV_TAG} 2> /dev/null)" == "" ]]; then
    docker build --cache-from kostua16/unc_2020_backend_base:${DEV_TAG} -f baseline.back.Dockerfile -t kostua16/unc_2020_backend_base:${DEV_TAG} .
  else
    docker build -f baseline.back.Dockerfile -t kostua16/unc_2020_backend_base:${DEV_TAG} .
  fi
fi
docker push kostua16/unc_2020_backend_base:${TAG}

docker pull kostua16/unc_2020_frontend_base:${TAG} || true
if [[ "$(docker images -q kostua16/unc_2020_frontend_base:${TAG} 2> /dev/null)" == "" ]]; then
  docker build --cache-from kostua16/unc_2020_frontend_base:${TAG} -f baseline.frontend.Dockerfile -t kostua16/unc_2020_frontend_base:${TAG} .
else
  docker pull kostua16/unc_2020_frontend_base:${DEV_TAG} || true
  if [[ "$(docker images -q kostua16/unc_2020_frontend_base:${DEV_TAG} 2> /dev/null)" == "" ]]; then
    docker build --cache-from kostua16/unc_2020_frontend_base:${DEV_TAG} -f baseline.frontend.Dockerfile -t kostua16/unc_2020_frontend_base:${TAG} .
  else
    docker build -f baseline.frontend.Dockerfile -t kostua16/unc_2020_frontend_base:${TAG} .
  fi
fi
docker push kostua16/unc_2020_frontend_base:${TAG}

docker build -f backend.production.Dockerfile --build-arg PROJECT=discovery -t kostua16/unc_2020_discovery:${TAG} .
docker push kostua16/unc_2020_discovery:${TAG}

docker build -f backend.production.Dockerfile --build-arg PROJECT=config -t kostua16/unc_2020_config:${TAG} .
docker push kostua16/unc_2020_config:${TAG}

docker build -f backend.production.Dockerfile --build-arg PROJECT=proxy -t kostua16/unc_2020_proxy:${TAG} .
docker push kostua16/unc_2020_proxy:${TAG}

docker build -f backend.production.Dockerfile --build-arg PROJECT=logging -t kostua16/unc_2020_logging:${TAG} .
docker push kostua16/unc_2020_logging:${TAG}

docker build -f backend.production.Dockerfile --build-arg PROJECT=tax -t kostua16/unc_2020_tax:${TAG} .
docker push kostua16/unc_2020_tax:${TAG}

docker build -f backend.production.Dockerfile --build-arg PROJECT=gibdd -t kostua16/unc_2020_gibdd:${TAG} .
docker push kostua16/unc_2020_gibdd:${TAG}

docker build -f backend.production.Dockerfile --build-arg PROJECT=account -t kostua16/unc_2020_account:${TAG} .
docker push kostua16/unc_2020_account:${TAG}

docker build -f backend.production.Dockerfile --build-arg PROJECT=bank -t kostua16/unc_2020_bank:${TAG} .
docker push kostua16/unc_2020_bank:${TAG}

docker build -f backend.production.Dockerfile --build-arg PROJECT=communal -t kostua16/unc_2020_communal:${TAG} .
docker push kostua16/unc_2020_communal:${TAG}

docker build -f backend.production.Dockerfile --build-arg PROJECT=passport -t kostua16/unc_2020_passport:${TAG} .
docker push kostua16/unc_2020_passport:${TAG}

docker build -f client-ui/prod.Dockerfile -t kostua16/unc_2020_frontend:${TAG} ./client-ui
docker push kostua16/unc_2020_frontend:${TAG}
