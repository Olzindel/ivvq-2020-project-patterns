import Vue from 'vue'
import Router from 'vue-router'
import ProductPage from '../components/product-page/ProductPage'
import HeaderPart from '../components/home-page/HomePage'
import UserAccount from '../components/user-option/UserAccount'
import ErrorPage from '../components/error-page/ErrorPage'
import APropos from '../components/A-propos-page/APropos'
import BasketPage from '../components/paiement-page/basketPage'
import paiementByCard from '../components/paiement-page/paiementByCard'
import ProductStockPage from '../components/mercant-page/ProductStockPage'
import OrderPage from '../components/mercant-page/OrderPage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/home'
    },
    {
      path: '/home',
      name: 'HeaderPart',
      component: HeaderPart
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
    },
    {
      path: '/paiement',
      name: 'paiemenByCard',
      component: paiementByCard
    },
    {
      path: '/basket',
      name: 'BasketPage',
      component: BasketPage
    },
    {
      path: '/aPropos',
      name: 'APropos',
      component: APropos
    },
    {
      path: '/stock',
      name: 'ProductStockPage',
      component: ProductStockPage
    },
    {
      path: '/order',
      name: 'OrderPage',
      component: OrderPage
    },
    {
      path: '*',
      component: ErrorPage
    }
  ]
})
