FROM node:lts-alpine AS development
WORKDIR /app
COPY package.json /app/package.json
COPY yarn.lock /app/yarn.lock
RUN yarn install --ignore-scripts

FROM development AS build
ENV CI=true
ENV PORT=3000
COPY . /app
RUN yarn build

#FROM build as deploy
#ENTRYPOINT ["yarn", "start"]

FROM nginx:alpine as production
RUN mkdir /app
COPY --from=build /app/dist /app
COPY nginx.conf /etc/nginx/nginx.conf
