FROM nginx:alpine as production
COPY ./dist/ /app
ENV PORT=8080
COPY --chown=nginx nginx.conf /etc/nginx/nginx.conf.template
CMD /bin/bash -c "envsubst '\$PORT' < /etc/nginx/nginx.conf.template > /etc/nginx/nginx.conf" && nginx -g 'daemon off;'
