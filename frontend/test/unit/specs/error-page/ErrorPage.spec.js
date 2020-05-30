import ErrorPage from '../../../../src/components/error-page/ErrorPage'
import Buefy from 'buefy'
import VueRouter from 'vue-router'
import {createLocalVue, shallowMount} from '@vue/test-utils'

describe('ErrorPage', () => {
  let localVue
  beforeEach(() => {
    localVue = createLocalVue()
    localVue.use(Buefy, {})
    localVue.use(VueRouter)
  })

  test('goToHome redirect to the home page', () => {
    const routes = [
      {
        path: '/error',
        component: ErrorPage
      }
    ]
    const router = new VueRouter({
      routes
    })
    const wrapper = shallowMount(ErrorPage, {
      localVue,
      router
    })
    wrapper.vm.goToHome()
    expect(wrapper.vm.$route.path).toBe('/')
  })
})
