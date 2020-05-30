FROM node:13

WORKDIR /app_tests

COPY package.json package-lock.json nightwatch.json wait-for-it.sh ./
COPY ./tests ./tests/

RUN npm install
