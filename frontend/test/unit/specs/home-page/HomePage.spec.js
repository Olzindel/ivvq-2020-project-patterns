import HomePage from '../../../../src/components/home-page/HomePage'
import {createLocalVue, shallowMount} from '@vue/test-utils'
import Buefy from 'buefy'

describe('HomePage', () => {
  let localVue

  beforeEach(() => {
    localVue = createLocalVue()
    localVue.use(Buefy, {})
  })

  test('get all product', () => {
    const query = () => {
      return Promise.resolve({
        data: {
          products: [{
            id: 2,
            name: 'name',
            price: 5,
            imageLinks: [{
              imageLink: 'imageLink'
            }]
          }]
        }
      })
    }
    const wrapper = shallowMount(HomePage, {
      localVue,
      mocks: {
        $apollo: {
          query
        }
      }
    })
    setTimeout(() => {
      expect(wrapper.vm.products.id).toBe(2)
      expect(wrapper.vm.products.imageLinks.imagelink).toBe('imageLink')
      expect(wrapper.vm.products.price).toBe(5)
    })
  })
})
