import ProductStockPage from '../../../../src/components/mercant-page/ProductStockPage'
import {createLocalVue, shallowMount} from '@vue/test-utils'
import Buefy from 'buefy'

describe('ProductPage', () => {
  let localVue

  beforeEach(() => {
    localVue = createLocalVue()
    localVue.use(Buefy, {})
  })

  test('getAllProduct', done => {
    const query = () => {
      return Promise.resolve({
        data: {
          getAllProducts: [{
            id: 2,
            name: 'name',
            stock: 5
          }]
        }
      })
    }
    const wrapper = shallowMount(ProductStockPage, {
      localVue,
      mocks: {
        $apollo: {
          query
        }
      }
    })
    setTimeout(() => {
      done()
      expect(wrapper.vm.products[0].id).toBe(2)
      expect(wrapper.vm.products[0].name).toBe('name')
    })
  })

  test('changeStockProduct', () => {
    const query = () => {
      return Promise.resolve({
        data: {
          getAllProducts: [{
            id: 2,
            name: 'name',
            stock: 5
          }]
        }
      })
    }
    const mutate = () => {
      return Promise.resolve({
        data: {
          updateStockProduct: {
            id: 2
          }
        }
      })
    }
    const wrapper = shallowMount(ProductStockPage, {
      localVue,
      mocks: {
        $apollo: {
          query,
          mutate
        }
      }
    })
    wrapper.vm.changeStockProduct(2, 2)
    setTimeout(() => {
      expect(mutate).toBeCalledwith(2, 2)
    })
  }
  )
})
