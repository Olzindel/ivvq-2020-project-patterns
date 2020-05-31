FROM node:13

WORKDIR /app_tests

COPY package.json package-lock.json nightwatch.json wait-for-it.sh ./
COPY ./tests ./tests/
COPY ./custom_assertions ./custom_assertions/

RUN npm install
