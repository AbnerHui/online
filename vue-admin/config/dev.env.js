'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  //BASE_API: '"https://easy-mock.com/mock/5950a2419adc231f356a6636/vue-admin"',
  //nginx开放的端口
  BASE_API: '"http://localhost:8222"',
  OSS_PATH: '"https://edu-photo.oss-cn-beijing.aliyuncs.com"',
})
