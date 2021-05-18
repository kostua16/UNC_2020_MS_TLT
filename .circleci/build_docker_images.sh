#!/usr/bin/env bash
TAG=branch_${CIRCLE_BRANCH//\//_}.latest
DEV_TAG=branch_develop.latest
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
docker tag kostua16/unc_2020_backend_base:${TAG} kostua16/unc_2020_backend_base:latest
docker push kostua16/unc_2020_backend_base:${TAG}
docker push kostua16/unc_2020_backend_base:latest

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
docker tag kostua16/unc_2020_frontend_base:${TAG} kostua16/unc_2020_frontend_base:latest
docker push kostua16/unc_2020_frontend_base:${TAG}
docker push kostua16/unc_2020_frontend_base:latest

docker build -f backend.production.Dockerfile --build-arg PROJECT=discovery -t kostua16/unc_2020_discovery:${TAG} -t registry.heroku.com/nc-edu-2020-discovery/web .
docker push kostua16/unc_2020_discovery:${TAG}
docker push registry.heroku.com/nc-edu-2020-discovery/web
heroku container:release -a nc-edu-2020-discovery web


docker build -f backend.production.Dockerfile --build-arg PROJECT=config -t kostua16/unc_2020_config:${TAG} -t registry.heroku.com/nc-edu-2020-config/web .
docker push kostua16/unc_2020_config:${TAG}
docker push registry.heroku.com/nc-edu-2020-config/web
heroku container:release -a nc-edu-2020-config web

docker build -f backend.production.Dockerfile --build-arg PROJECT=proxy -t kostua16/unc_2020_proxy:${TAG} -t registry.heroku.com/nc-edu-2020-proxy/web .
docker push kostua16/unc_2020_proxy:${TAG}
docker push registry.heroku.com/nc-edu-2020-proxy/web
heroku container:release -a nc-edu-2020-proxy web

docker build -f backend.production.Dockerfile --build-arg PROJECT=logging -t kostua16/unc_2020_logging:${TAG} -t registry.heroku.com/nc-edu-2020-logger/web .
docker push kostua16/unc_2020_logging:${TAG}
docker push registry.heroku.com/nc-edu-2020-logger/web
heroku container:release -a nc-edu-2020-logger web

docker build -f backend.production.Dockerfile --build-arg PROJECT=tax -t kostua16/unc_2020_tax:${TAG} -t registry.heroku.com/nc-edu-2020-tax/web .
docker push kostua16/unc_2020_tax:${TAG}
docker push registry.heroku.com/nc-edu-2020-tax/web
heroku container:release -a nc-edu-2020-tax web

docker build -f backend.production.Dockerfile --build-arg PROJECT=gibdd -t kostua16/unc_2020_gibdd:${TAG} -t registry.heroku.com/nc-edu-2020-gibdd/web .
docker push kostua16/unc_2020_gibdd:${TAG}
docker push registry.heroku.com/nc-edu-2020-gibdd/web
heroku container:release -a nc-edu-2020-gibdd web

docker build -f backend.production.Dockerfile --build-arg PROJECT=account -t kostua16/unc_2020_account:${TAG} -t registry.heroku.com/nc-edu-2020-account/web .
docker push kostua16/unc_2020_account:${TAG}
docker push registry.heroku.com/nc-edu-2020-account/web
heroku container:release -a nc-edu-2020-account web

docker build -f backend.production.Dockerfile --build-arg PROJECT=bank -t kostua16/unc_2020_bank:${TAG} -t registry.heroku.com/nc-edu-2020-bank/web .
docker push kostua16/unc_2020_bank:${TAG}
docker push registry.heroku.com/nc-edu-2020-bank/web
heroku container:release -a nc-edu-2020-bank web

docker build -f backend.production.Dockerfile --build-arg PROJECT=communal -t kostua16/unc_2020_communal:${TAG} -t registry.heroku.com/nc-edu-2020-communal/web .
docker push kostua16/unc_2020_communal:${TAG}
docker push registry.heroku.com/nc-edu-2020-communal/web
heroku container:release -a nc-edu-2020-communal web

docker build -f backend.production.Dockerfile --build-arg PROJECT=passport -t kostua16/unc_2020_passport:${TAG} -t registry.heroku.com/nc-edu-2020-passport/web .
docker push kostua16/unc_2020_passport:${TAG}
docker push registry.heroku.com/nc-edu-2020-passport/web
heroku container:release -a nc-edu-2020-passport web

docker build -f client-ui/prod.Dockerfile -t kostua16/unc_2020_frontend:${TAG} -t registry.heroku.com/nc-edu-2020-ui/web ./client-ui
docker push kostua16/unc_2020_frontend:${TAG}
docker push registry.heroku.com/nc-edu-2020-ui/web
heroku container:release -a nc-edu-2020-ui web
