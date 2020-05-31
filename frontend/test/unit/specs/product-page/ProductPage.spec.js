import ProductPage from '../../../../src/components/product-page/ProductPage'
import {createLocalVue, shallowMount} from '@vue/test-utils'
import Buefy from 'buefy'
import Vuex from 'vuex'

describe('ProductPage', () => {
  let localVue
  let query
  let spy
  beforeEach(() => {
    localVue = createLocalVue()
    localVue.use(Buefy, {})
    spy = jest.fn()
    query = () => {
      return Promise.resolve({
        data: {
          productInfos: {
            id: 2,
            name: 'name',
            imageLinks: [{
              id: 5,
              imageLink: 'imageLink'
            }],
            price: 5,
            description: 'description'
          }
        }})
    }
  })

  test('should get the information of the product after the page is created', done => {
    const wrapper = shallowMount(ProductPage, {
      localVue,
      mocks: {
        $apollo: {
          query
        }
      },
      propsData: {
        productId: 2
      }
    })
    setTimeout(() => {
      done()
      console.log(wrapper.vm.product.id)
      expect(wrapper.vm.product.id).toBe(2)
      expect(wrapper.vm.product.name).toBe('name')
    })
  })

  test('should send a request to add a basket', done => {
    const mutate = () => {
      return Promise.resolve({
        data: {
          createOrder: 5
        }
      })
    }
    localVue.use(Vuex)
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
          query,
          mutate
        }
      }
    })
    wrapper.vm.addThisProduct = spy
    wrapper.vm.addABasket()
    setTimeout(() => {
      done()
      expect(spy).toBeCalled()
    })
  })
})
