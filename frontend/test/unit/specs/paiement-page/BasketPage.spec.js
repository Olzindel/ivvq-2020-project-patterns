import BasketPage from '../../../../src/components/paiement-page/BasketPage'
import {createLocalVue, shallowMount} from '@vue/test-utils'
import Buefy from 'buefy'

describe('BasketPage', () => {
  let localVue
  let spy
  let mountedSpy
  beforeEach(() => {
    localVue = createLocalVue()
    localVue.use(Buefy, {})
    spy = jest.fn()
    mountedSpy = jest.fn(() => {
      return true
    })
    BasketPage.mounted = mountedSpy
  })

  test('deleteItem', done => {
    const mutate = () => {
      return Promise.resolve({
        data: {
          data: {
            deleteOrderItem: {
              id: 1
            }
          }
        }
      })
    }
    const wrapper = shallowMount(BasketPage, {
      localVue,
      mocks: {
        $apollo: {
          mutate
        }
      }
    })
    wrapper.vm.BasketInfo = spy

    const spyDelete = jest.spyOn(wrapper.vm, 'refreshOrderAfterDelete')
    wrapper.setData({
      order: [{
        orderItems:
            [{
              id: 1,
              quantity: 1,
              product: {
                price: 5
              }
            },
            {
              id: 2,
              quantity: 1,
              product: {
                price: 5
              }
            }
            ]
      }
      ]
    }
    )
    wrapper.vm.deleteItem(1)
    setTimeout(() => {
      expect(spyDelete).toHaveBeenCalled()
      done()
    })
  })

  test('calcul price', () => {
    const wrapper = shallowMount(BasketPage, {
      localVue
    })
    wrapper.setData({
      order: [{
        orderItems:
          [{
            id: 1,
            quantity: 1,
            product: {
              price: 5
            }
          },
          {
            id: 2,
            quantity: 2,
            product: {
              price: 5
            }
          }
          ]
      }
      ]
    })
    expect(wrapper.vm.price).toBe(15)
  })

  test('basket empty with no order', () => {
    const wrapper = shallowMount(BasketPage, {
      localVue
    })
    wrapper.setData({
      order: [{orderItems: []}]
    })
    expect(wrapper.vm.basketIsEmpty).toBeTruthy()
    wrapper.setData({
      order: [{
        orderItems:
          [{
            id: 1,
            quantity: 1,
            product: {
              price: 5
            }
          },
          {
            id: 2,
            quantity: 2,
            product: {
              price: 5
            }
          }
          ]
      }
      ]
    })
    expect(wrapper.vm.basketIsEmpty).toBeFalsy()
  })

  test('validate Basket not empty', done => {
    const query = () => {
      return Promise.resolve({
        data: {
          checkStockForAnOrder: [{
            id: 5
          }]
        }
      })
    }
    const wrapper = shallowMount(BasketPage, {
      localVue,
      mocks: {
        $apollo: {
          query
        }
      }
    })

    wrapper.setData({
      order: [{
        id: 5,
        orderItems:
            [{
              id: 1,
              quantity: 1,
              product: {
                price: 5
              }
            },
            {
              id: 2,
              quantity: 1,
              product: {
                price: 5
              }
            }
            ]
      }
      ],
      user: {
        id: 10
      }
    }
    )
    wrapper.vm.errorMessage = spy
    wrapper.vm.validateBasket()
    setTimeout(() => {
      expect(spy).toBeCalled()
      done()
    })
  })

  test('validate Basket empty', done => {
    const query = () => {
      return Promise.resolve({
        data: {
          checkStockForAnOrder: []
        }
      })
    }
    const wrapper = shallowMount(BasketPage, {
      localVue,
      mocks: {
        $apollo: {
          query
        }
      }
    })

    wrapper.setData({
      order: [{
        id: 5,
        orderItems:
            [{
              id: 1,
              quantity: 1,
              product: {
                price: 5
              }
            },
            {
              id: 2,
              quantity: 1,
              product: {
                price: 5
              }
            }
            ]
      }
      ],
      user: {
        id: 10
      }
    }
    )
    wrapper.vm.errorMessage = spy
    wrapper.vm.validateBasket()
    setTimeout(() => {
      expect(spy).not.toBeCalled()
      expect(wrapper.vm.basket).toBeFalsy()
      done()
    })
  })

  test('function error message', () => {
    const wrapper = shallowMount(BasketPage, {
      localVue
    })
    wrapper.setData({
      order: [{
        id: 5,
        orderItems:
          [{
            id: 1,
            quantity: 1,
            product: {
              id: 1,
              name: 'name1',
              stock: 'stock1',
              price: 5
            }
          },
          {
            id: 2,
            quantity: 1,
            product: {
              id: 2,
              name: 'name2',
              stock: 'stock2',
              price: 5
            }
          }
          ]
      }
      ]
    })
    wrapper.vm.$buefy.toast.open = spy
    const message = [{id: 2}]
    wrapper.vm.errorMessage(message)
    expect(spy).toBeCalledWith({
      duration: 5000,
      message: 'pas assez de stock pour name2 juste stock2 produit(s) disponible<br/>',
      position: 'is-bottom',
      type: 'is-danger'
    })
  })
})
