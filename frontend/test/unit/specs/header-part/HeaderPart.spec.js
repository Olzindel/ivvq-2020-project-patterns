import HeaderPart from '../../../../src/components/header-part/HeaderPart'
import {createLocalVue, shallowMount} from '@vue/test-utils'
import Buefy from 'buefy'
import VueRouter from 'vue-router'
import Vuex from 'vuex'

describe('BasketPage', () => {
  let localVue
  let wrapper
  let spy
  let storeSpy
  beforeEach(() => {
    localVue = createLocalVue()
    localVue.use(Buefy, {})
    localVue.use(VueRouter)
    localVue.use(Vuex)
    const routes = [
      {
        path: '/',
        component: HeaderPart
      }
    ]
    const router = new VueRouter({
      routes
    })
    storeSpy = jest.fn()
    const mutations = {
      logout: storeSpy

    }
    const store = new Vuex.Store({mutations})
    spy = jest.fn()
    wrapper = shallowMount(HeaderPart, {
      localVue,
      router,
      store
    })
    wrapper.setData({
      showNavigation: true,
      navbarOptions: {
        fixedTop: true
      }
    }
    )
  })

  test('goToAccount', () => {
    wrapper.vm.$router.push = spy
    expect(wrapper.vm.showNavigation).toBeTruthy()
    wrapper.vm.goToAccount()
    expect(spy).toBeCalledWith('/account')
    expect(wrapper.vm.showNavigation).toBeFalsy()
  })

  test('goToHome', () => {
    wrapper.vm.$router.push = spy
    expect(wrapper.vm.showNavigation).toBeTruthy()
    wrapper.vm.goToHome()
    expect(spy).toBeCalledWith('/')
    expect(wrapper.vm.showNavigation).toBeFalsy()
  })

  test('goToAPropos', () => {
    wrapper.vm.$router.push = spy
    expect(wrapper.vm.showNavigation).toBeTruthy()
    wrapper.vm.goToAPropos()
    expect(spy).toBeCalledWith('/aPropos')
    expect(wrapper.vm.showNavigation).toBeFalsy()
  })

  test('goToLogin', () => {
    wrapper.vm.$router.push = spy
    expect(wrapper.vm.showNavigation).toBeTruthy()
    wrapper.vm.goToLogin()
    expect(spy).toBeCalledWith('/login')
    expect(wrapper.vm.showNavigation).toBeFalsy()
  })

  test('goToSignUp', () => {
    wrapper.vm.$router.push = spy
    expect(wrapper.vm.showNavigation).toBeTruthy()
    wrapper.vm.goToSignUp()
    expect(spy).toBeCalledWith('/signup')
    expect(wrapper.vm.showNavigation).toBeFalsy()
  })

  test('goToBasket', () => {
    wrapper.vm.$router.push = spy
    expect(wrapper.vm.showNavigation).toBeTruthy()
    wrapper.vm.goToBasket()
    expect(spy).toBeCalledWith('/basket')
    expect(wrapper.vm.showNavigation).toBeFalsy()
  })

  test('goToProductStockPage', () => {
    wrapper.vm.$router.push = spy
    expect(wrapper.vm.showNavigation).toBeTruthy()
    wrapper.vm.goToProductStockPage()
    expect(spy).toBeCalledWith('/stock')
    expect(wrapper.vm.showNavigation).toBeFalsy()
  })
  test('goToOrders', () => {
    wrapper.vm.$router.push = spy
    expect(wrapper.vm.showNavigation).toBeTruthy()
    wrapper.vm.goToOrders()
    expect(spy).toBeCalledWith('/orders')
    expect(wrapper.vm.showNavigation).toBeFalsy()
  })

  test('goToHistory', () => {
    wrapper.vm.$router.push = spy
    expect(wrapper.vm.showNavigation).toBeTruthy()
    wrapper.vm.goToHistory()
    expect(spy).toBeCalledWith('/history')
    expect(wrapper.vm.showNavigation).toBeFalsy()
  })

  test('logout', () => {
    wrapper.vm.goToHome = spy
    wrapper.vm.logout()
    expect(storeSpy).toBeCalled()
    expect(spy).toBeCalled()
  })
})
