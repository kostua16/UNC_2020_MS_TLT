FROM nginx:alpine as production
COPY ./dist/ /app
COPY --chown=nginx nginx.conf /etc/nginx/nginx.conf
