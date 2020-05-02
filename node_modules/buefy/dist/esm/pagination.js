import './chunk-a3856f8d.js';
import './helpers.js';
import './chunk-df34e5c4.js';
import './chunk-b580944a.js';
import { r as registerComponent, u as use } from './chunk-cca88db8.js';
import { P as Pagination, a as PaginationButton } from './chunk-1797358f.js';
export { P as BPagination, a as BPaginationButton } from './chunk-1797358f.js';

var Plugin = {
  install: function install(Vue) {
    registerComponent(Vue, Pagination);
    registerComponent(Vue, PaginationButton);
  }
};
use(Plugin);

export default Plugin;
