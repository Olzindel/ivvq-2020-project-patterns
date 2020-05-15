import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import ProductPage from '../components/product-page/ProductPage'
import HeaderPart from '../components/home-page/HomePage'
import UserAccount from '../components/user-option/UserAccount'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HeaderPart',
      component: HeaderPart
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
    },
    {
      path: '/account',
      name: 'UserAccount',
      component: UserAccount
    }
  ]
})
