docker build -f baseline.back.Dockerfile -t kostua16/unc_2020_backend_base .
docker push kostua16/unc_2020_backend_base

docker build -f baseline.frontend.Dockerfile -t kostua16/unc_2020_frontend_base .
docker push kostua16/unc_2020_frontend_base
