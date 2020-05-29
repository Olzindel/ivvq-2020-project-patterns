import BasketPage from '../../../../src/components/paiement-page/BasketPage'
import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import Buefy from 'buefy'
import {jest} from '@jest/globals'

/* const mySchema = makeExecutableSchema({
  typeDefs: schema
}) */

describe('BasketPage', () => {
  let localVue
  beforeEach(() => {
    localVue = createLocalVue()
    localVue.use(Buefy, {})
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

    const spyDelete = jest.spyOn(wrapper.vm, 'refreshOrderAfterDelete')
    // wrapper.vm.refreshOrderAfterDelete = spyDelete
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
})
