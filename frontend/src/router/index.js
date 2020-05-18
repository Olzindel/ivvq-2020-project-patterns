import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import HeaderPart from '../components/home-page/HomePage'
import UserAccount from '../components/user-option/UserAccount'
import ErrorPage from '../components/error-page/ErrorPage'
import APropos from '../components/A-propos-page/APropos'

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
      path: '/hello',
      name: 'HelloWorld',
      component: HelloWorld
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
    }
  ]
})
