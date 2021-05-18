FROM kostua16/unc_2020_frontend_base:latest AS build
WORKDIR /app
COPY . /app
RUN yarn build

FROM nginx:alpine as production
USER nginx
ENV PORT=8080
COPY --chown=nginx --from=build /app/dist /app
COPY --chown=nginx nginx.conf /etc/nginx/nginx.conf.template
CMD /bin/bash -c "envsubst '\$PORT' < /etc/nginx/nginx.conf.template > /etc/nginx/nginx.conf" && nginx -g 'daemon off;'
