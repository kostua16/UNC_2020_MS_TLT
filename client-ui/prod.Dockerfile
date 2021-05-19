FROM kostua16/unc_2020_frontend_base:latest AS build
WORKDIR /app
COPY . /app
RUN yarn build

FROM nginx:alpine as production
USER nginx
ENV PORT=8080
CMD ["frontend.sh"]
COPY --chown=nginx nginx.conf /etc/nginx/nginx.conf.template
COPY --chown=nginx ./frontend.sh /app/frontend.sh
RUN chmod +x /app/frontend.sh
COPY --chown=nginx --from=build /app/dist /app
