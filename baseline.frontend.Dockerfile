FROM node:lts-alpine AS deps
WORKDIR /app
COPY client-ui/package.json /app/package.json
COPY client-ui/yarn.lock /app/yarn.lock
RUN yarn install --ignore-scripts

FROM node:lts-alpine AS build
WORKDIR /app
COPY --from=deps /app/node_modules /app/node_modules
