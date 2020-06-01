import BasketPage from '../../../../src/components/paiement-page/BasketPage'
import {createLocalVue, shallowMount} from '@vue/test-utils'
import Buefy from 'buefy'
import Vuex from 'vuex'

describe('BasketPage', () => {
  let localVue

  beforeEach(() => {
    localVue = createLocalVue()
    localVue.use(Buefy, {})
  })
  test('should get user and order on mounted', done => {
    const query = () => {
      return Promise.resolve({
        data: {
          getuser: {
            id: 2,
            orders: [{
              id: 5,
              status: 'BASKET',
              orderItems: [{
                id: 4,
                product: [{
                  id: 8
                }]
              }]
            },
            {
              id: 9,
              status: 'PAID',
              orderItems: [{
                id: 4,
                product: [{
                  id: 8
                }]
              }]
            }]
          }
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
    const wrapper = shallowMount(BasketPage, {
      localVue,
      store,
      mocks: {
        $apollo: {
          query
        }
      }
    })

    setTimeout(() => {
      done()
      expect(wrapper.vm.user.id).toBe(2)
      expect(wrapper.vm.order.length).toBe(1)
      expect(wrapper.vm.order[0].status).toBe('BASKET')
    })
  })
})
