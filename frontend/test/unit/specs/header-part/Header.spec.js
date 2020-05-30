import HeaderPart from '../../../../src/components/header-part/HeaderPart'
import {createLocalVue, shallowMount} from '@vue/test-utils'
import Buefy from 'buefy'
import BasketPage from '../../../../src/components/paiement-page/BasketPage'

describe('Some feature', () => {
  let localVue
  beforeEach(() => {
    localVue = createLocalVue()
    localVue.use(Buefy, {})
  })

  it('first test', () => {
    const wrapper = shallowMount(HeaderPart, {
      localVue
    })
    wrapper.setData({ showNavigation: false,
      navbarOptions: {
        fixedTop: true
      }})
    wrapper.vm.$router = jest.fn()
  })
})
