import Vue from 'vue'
import Router from 'vue-router'
import ProductPage from '../components/product-page/ProductPage'
import HeaderPart from '../components/home-page/HomePage'
import UserAccount from '../components/user-option/UserAccount'
import ErrorPage from '../components/error-page/ErrorPage'
import APropos from '../components/A-propos-page/APropos'
import LoginPage from '../components/login-page/LoginPage'
import SignUpPage from '../components/sign-up-page/SignUpPage'
import BasketPage from '../components/paiement-page/BasketPage'
import PaiementByCard from '../components/paiement-page/PaiementByCard'
import ProductStockPage from '../components/mercant-page/ProductStockPage'
import OrderPage from '../components/mercant-page/OrderPage'
import UserOrder from '../components/user-option/UserOrder'
import store from '../store'
import gql from 'graphql-tag'
import {apolloClient} from '../vue-appolo-config'

Vue.use(Router)

let redirectIfNotCondition = function (condition, next) {
  if (!condition) {
    next('home')
  } else {
    next(true)
  }
}

let isAuthenticated = function () {
  return localStorage.getItem('connection-token')
}
const router = new Router({
  routes: [
    {
      path: '/',
      redirect: '/home'
    },
    {
      path: '/home',
      name: 'headerPart',
      component: HeaderPart
    },
    {
      path: '/product/:productId',
      props: true,
      name: 'productPage',
      component: ProductPage
    },
    {
      path: '/account',
      name: 'userAccount',
      component: UserAccount,
      beforeEnter (to, from, next) {
        redirectIfNotCondition(isAuthenticated(), next)
      }
    },
    {
      path: '/paiement',
      name: 'paiementByCard',
      component: PaiementByCard,
      beforeEnter (to, from, next) {
        redirectIfNotCondition(isAuthenticated(), next)
      }
    },
    {
      path: '/basket',
      name: 'basketPage',
      component: BasketPage,
      beforeEnter (to, from, next) {
        redirectIfNotCondition(isAuthenticated(), next)
      }
    },
    {
      path: '/aPropos',
      name: 'aPropos',
      component: APropos
    },
    {
      path: '/stock',
      name: 'productStockPage',
      component: ProductStockPage,
      beforeRouteEnter: (to, from, next) => {
        redirectIfNotCondition(isAuthenticated() && store.getters.isMerchant, next)
      }
    },
    {
      path: '/orders',
      name: 'orderPage',
      component: OrderPage,
      beforeRouteEnter: (to, from, next) => {
        redirectIfNotCondition(isAuthenticated() && store.getters.isMerchant, next)
      }
    },
    {
      path: '/history',
      name: 'UserOrder',
      component: UserOrder
    },
    {
      path: '/login',
      name: 'login',
      component: LoginPage
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignUpPage
    },
    {
      path: '*',
      component: ErrorPage
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (isAuthenticated() && !store.getters.user) {
    apolloClient.query({
      query: gql`query UserFromToken($token: String!){
              user: userFromToken(token: $token) {
                id,
                role
                }
              }`,
      variables: {
        token: localStorage.getItem('connection-token')
      }
    }).then(result => {
      store.commit('connect', result.data.user)
      next(true)
    }, () => {
      localStorage.setItem('connection-token', '')
      next(true)
    })
  } else {
    next(true)
  }
})

export default router
