docker build -f baseline.back.Dockerfile -t kostua16/unc_2020_backend_base .
docker tag  kostua16/unc_2020_backend_base kostua16/unc_2020_backend_base:0.1.0
docker push kostua16/unc_2020_backend_base

docker build -f baseline.frontend.Dockerfile -t kostua16/unc_2020_frontend_base .
docker tag  kostua16/unc_2020_frontend_base kostua16/unc_2020_frontend_base:0.1.0
docker push kostua16/unc_2020_frontend_base
