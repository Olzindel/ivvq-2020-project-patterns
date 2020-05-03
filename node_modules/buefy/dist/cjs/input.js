'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

require('./chunk-e0c9228b.js');
require('./helpers.js');
require('./chunk-805257cc.js');
require('./chunk-be4a5cfb.js');
require('./chunk-82a892f6.js');
var __chunk_5 = require('./chunk-13e039f5.js');
var __chunk_6 = require('./chunk-43b1195e.js');

var Plugin = {
  install: function install(Vue) {
    __chunk_5.registerComponent(Vue, __chunk_6.Input);
  }
};
__chunk_5.use(Plugin);

exports.BInput = __chunk_6.Input;
exports.default = Plugin;
