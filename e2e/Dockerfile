FROM node:13

WORKDIR /app_tests

COPY package.json nightwatch.json wait-for-it.sh ./
COPY ./tests ./tests/
COPY ./custom_assertions ./custom_assertions/

RUN chmod +x wait-for-it.sh && npm install
