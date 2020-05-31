import WaifuCard from '../../../../src/components/home-page/WaifuCard'
import {createLocalVue, shallowMount} from '@vue/test-utils'
import Buefy from 'buefy'
import VueRouter from 'vue-router'

describe('BasketPage', () => {
  let localVue
  let spy
  let wrapper
  beforeEach(() => {
    localVue = createLocalVue()
    localVue.use(Buefy, {})
    localVue.use(VueRouter)
    spy = jest.fn()
    const routes = [
      {
        path: '/',
        component: WaifuCard
      }
    ]
    const router = new VueRouter({
      routes
    })
    spy = jest.fn()
    wrapper = shallowMount(WaifuCard, {
      localVue,
      router,
      propsData: {
        product: {
          id: 1,
          name: 'name',
          imageLinks: [{
            imageLink: 'imageLink'
          }],
          price: 5
        }
      }
    })
  })

  test('changeToProductInfo', () => {
    wrapper.vm.$router.push = spy
    wrapper.vm.changeToProductInfo()
    expect(spy).toBeCalledWith({path: '/product/1'})
  })
})
