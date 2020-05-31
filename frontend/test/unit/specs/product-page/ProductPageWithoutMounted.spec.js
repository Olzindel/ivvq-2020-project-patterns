import ProductPage from '../../../../src/components/product-page/ProductPage'
import {createLocalVue, shallowMount} from '@vue/test-utils'
import Buefy from 'buefy'
import Vuex from 'vuex'

describe('ProductPage', () => {
  let localVue
  let query
  let spyAddBasket
  let spyAddProduct
  let spyMounted
  let store
  beforeEach(() => {
    localVue = createLocalVue()
    localVue.use(Buefy, {})
    localVue.use(Vuex)
    spyAddBasket = jest.fn()
    spyAddProduct = jest.fn()
    spyMounted = jest.fn()
    let user = {id: 2}
    let getters = {
      user: () => {
        return user
      }
    }
    store = new Vuex.Store({getters})
    ProductPage.mounted = spyMounted
  })

  test('should get all user order and add a basket because there are no basket', done => {
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
    const wrapper = shallowMount(ProductPage, {
      localVue,
      store,
      mocks: {
        $apollo: {
          query
        }
      }
    })
    wrapper.vm.addABasket = spyAddBasket
    wrapper.vm.addThisProduct = spyAddProduct
    wrapper.vm.getUserOrder()
    setTimeout(() => {
      done()
      expect(spyAddBasket).toBeCalled()
      expect(spyAddProduct).not.toBeCalled()
    })
  })
  test('should get all user order and add product because there are a basket', done => {
    query = () => {
      return Promise.resolve({
        data: {
          getuser: {
            orders: [{
              id: 2,
              status: 'BASKET'
            }]
          }
        }
      })
    }
    const wrapper = shallowMount(ProductPage, {
      localVue,
      store,
      mocks: {
        $apollo: {
          query
        }
      }
    })
    wrapper.vm.addABasket = spyAddBasket
    wrapper.vm.addThisProduct = spyAddProduct
    wrapper.vm.getUserOrder()
    setTimeout(() => {
      done()
      expect(spyAddBasket).not.toBeCalled()
      expect(spyAddProduct).toBeCalledWith(2)
    })
  })

  test('should get all user order and add product because there are a basket', done => {
    query = () => {
      return Promise.reject(new Error('mock error'))
    }
    const wrapper = shallowMount(ProductPage, {
      localVue,
      store,
      mocks: {
        $apollo: {
          query
        }
      }
    })
    wrapper.vm.addABasket = spyAddBasket
    wrapper.vm.addThisProduct = spyAddProduct
    let spyDanger = jest.fn()
    wrapper.vm.danger = spyDanger
    wrapper.vm.getUserOrder()
    setTimeout(() => {
      done()
      expect(spyDanger).toBeCalled()
      expect(spyAddBasket).not.toBeCalled()
      expect(spyAddProduct).not.toBeCalled()
    })
  })

  test('should show message error', () => {
    const wrapper = shallowMount(ProductPage, {
      localVue
    })
    let spy = jest.fn()
    wrapper.vm.$buefy.toast.open = spy
    wrapper.vm.danger('message')
    expect(spy).toBeCalledWith({
      duration: 5000,
      message: 'Vous devez vous connecter',
      position: 'is-bottom',
      type: 'is-danger'
    })
  })

  test('should show message succes', () => {
    const wrapper = shallowMount(ProductPage, {
      localVue
    })
    wrapper.setData({
      product: {
        name: 'productName'
      }
    })
    let spy = jest.fn()
    wrapper.vm.$buefy.toast.open = spy
    wrapper.vm.success()
    expect(spy).toBeCalledWith({
      message: 'productName' + ' a été ajouté à votre panier',
      type: 'is-success'
    })
  })

  test('should add a product and show a success message', done => {
    const mutate = () => {
      return Promise.resolve({
        data: {
          createOrderItem: {
            id: 5
          }
        }
      })
    }
    const wrapper = shallowMount(ProductPage, {
      localVue,
      mocks: {
        $apollo: {
          mutate
        }
      },
      propsData: {
        productId: 2
      }
    })
    let spy = jest.fn()
    wrapper.vm.success = spy
    wrapper.vm.addThisProduct(2)
    setTimeout(() => {
      done()
      expect(spy).toBeCalled()
    })
  })
  test('should add a product and show a error message', done => {
    const mutate = () => {
      return Promise.reject(new Error('mock error'))
    }
    const wrapper = shallowMount(ProductPage, {
      localVue,
      mocks: {
        $apollo: {
          mutate
        }
      },
      propsData: {
        productId: 2
      }
    })
    let spy = jest.fn()
    wrapper.vm.danger = spy
    wrapper.vm.addThisProduct(2)
    setTimeout(() => {
      done()
      expect(spy).toBeCalled()
    })
  })
})
