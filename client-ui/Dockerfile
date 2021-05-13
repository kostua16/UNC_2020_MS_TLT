FROM node:lts-alpine AS development

WORKDIR /app
COPY package.json /app/package.json
COPY package-lock.json /app/package-lock.json

RUN npm ci
COPY . /app

ENV CI=true
ENV PORT=8080

FROM development AS build

RUN npm run build

FROM build as deploy

ENTRYPOINT ["npm", "start"]

