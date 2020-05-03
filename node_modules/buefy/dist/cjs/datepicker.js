'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

require('./chunk-e0c9228b.js');
require('./helpers.js');
require('./chunk-805257cc.js');
require('./chunk-be4a5cfb.js');
require('./chunk-82a892f6.js');
var __chunk_5 = require('./chunk-13e039f5.js');
require('./chunk-43b1195e.js');
require('./chunk-ae7e641a.js');
require('./chunk-ccfba9b8.js');
require('./chunk-4b6b0ba7.js');
require('./chunk-66dfb443.js');
var __chunk_15 = require('./chunk-79b5defc.js');

var Plugin = {
  install: function install(Vue) {
    __chunk_5.registerComponent(Vue, __chunk_15.Datepicker);
  }
};
__chunk_5.use(Plugin);

exports.BDatepicker = __chunk_15.Datepicker;
exports.default = Plugin;
