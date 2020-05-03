import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import ProductPage from '../components/product-page/ProductPage'
import Header from '../components/HomePage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Header',
      component: Header
    },
    {
      path: '/hello',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/product/:productId',
      props: true,
      name: 'Product-Page',
      component: ProductPage
    }
  ]
})
