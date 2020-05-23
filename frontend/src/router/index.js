import Vue from 'vue'
import Router from 'vue-router'
import HeaderPart from '../components/home-page/HomePage'
import UserAccount from '../components/user-option/UserAccount'
import ErrorPage from '../components/error-page/ErrorPage'
import APropos from '../components/A-propos-page/APropos'
import LoginPage from '../components/login-page/LoginPage'
import SignUpPage from '../components/sign-up-page/SignUpPage'

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
      path: '/account',
      name: 'UserAccount',
      component: UserAccount
    },
    {
      path: '/aPropos',
      name: 'APropos',
      component: APropos
    },
    {
      path: '*',
      component: ErrorPage
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
    }
  ]
})
