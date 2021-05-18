FROM kostua16/unc_2020_frontend_base AS build
WORKDIR /app
COPY . /app
RUN yarn build

FROM nginx:alpine as production
USER nginx
COPY --chown=nginx --from=build /app/dist /app
COPY --chown=nginx nginx.conf /etc/nginx/nginx.conf
