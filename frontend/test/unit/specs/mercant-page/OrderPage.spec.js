import OrderPage from '../../../../src/components/mercant-page/OrderPage'
import {createLocalVue, shallowMount} from '@vue/test-utils'
import Buefy from 'buefy'

describe('OrderPage', () => {
  let localVue

  beforeEach(() => {
    localVue = createLocalVue()
    localVue.use(Buefy, {})
  })

  test('should get all product', done => {
    const query = () => {
      return Promise.resolve({
        data: {
          getAllOrders: [{
            id: 1,
            status: 'PAID',
            orderItems: [{
              quantity: 8,
              product: [{
                name: 'name'
              }]
            }],
            user: {
              firstName: 'firstName',
              lastName: 'firstName',
              street: 'street',
              postalCode: 45203,
              city: 'Toulouse'
            }
          },
          {
            id: 2,
            status: 'BASKET',
            orderItems: [{
              quantity: 4,
              product: [{
                name: 'name2'
              }]
            }],
            user: {
              firstName: 'firstName2',
              lastName: 'firstName2',
              street: 'street2',
              postalCode: 45283,
              city: 'Paris'
            }
          }
          ]
        }
      })
    }
    const wrapper = shallowMount(OrderPage, {
      localVue,
      mocks: {
        $apollo: {
          query
        }
      }
    })
    setTimeout(() => {
      done()
      expect(wrapper.vm.orders[0].id).toBe(1)
    })
  })
})
