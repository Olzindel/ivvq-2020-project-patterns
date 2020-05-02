'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

require('./chunk-e0c9228b.js');
require('./helpers.js');
require('./chunk-805257cc.js');
require('./chunk-be4a5cfb.js');
require('./chunk-82a892f6.js');
var __chunk_5 = require('./chunk-13e039f5.js');
require('./chunk-43b1195e.js');
var __chunk_7 = require('./chunk-4047774d.js');

var Plugin = {
  install: function install(Vue) {
    __chunk_5.registerComponent(Vue, __chunk_7.Autocomplete);
  }
};
__chunk_5.use(Plugin);

exports.BAutocomplete = __chunk_7.Autocomplete;
exports.default = Plugin;
