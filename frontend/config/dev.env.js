'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  VUE_APP_API_HTTP: '"http://localhost:8080/graphql"',
  VUE_APP_LOGIN_HTTP: '"http://localhost:8080/login"'
})
