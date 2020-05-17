import Vue from 'vue'
import Router from 'vue-router'
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
      path: '/account',
      name: 'UserAccount',
      component: UserAccount
    }
  ]
})
