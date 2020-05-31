import ProductPage from '../../../../src/components/product-page/ProductPage'
import {createLocalVue, shallowMount} from '@vue/test-utils'
import Buefy from 'buefy'
import Vuex from 'vuex'

describe('ProductPage', () => {
  let localVue
  let query
  let spy
  let spyMounted
  beforeEach(() => {
    localVue = createLocalVue()
    localVue.use(Buefy, {})
    localVue.use(Vuex)
    spy = jest.fn()
    spyMounted = jest.fn()
    ProductPage.mounted = spyMounted
  })

  test('should get user and update information', done => {
    query = () => {
      return Promise.resolve({
        data: {
          getuser: {
            orders: [{
              id: 2,
              status: 'PAID'
            }]
          }
        }
      })
    }
    let user = {id: 2}
    let getters = {
      user: () => {
        return user
      }
    }
    let store = new Vuex.Store({getters})
    const wrapper = shallowMount(ProductPage, {
      localVue,
      store,
      mocks: {
        $apollo: {
          query
        }
      }
    })
    localStorage.setItem('connection-token', 'abc')
    wrapper.vm.addABasket = spy
    wrapper.vm.getUserOrder()
    setTimeout(() => {
      done()
      expect(spy).toBeCalled()
    })
  })
})
