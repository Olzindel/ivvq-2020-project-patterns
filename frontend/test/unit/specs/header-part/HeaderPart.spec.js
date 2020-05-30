import HeaderPart from '../../../../src/components/header-part/HeaderPart'
import {createLocalVue, shallowMount} from '@vue/test-utils'
import Buefy from 'buefy'
import {jest} from '@jest/globals'
import VueRouter from 'vue-router'
import index from '../../../../src/router'

describe('BasketPage', () => {
  let localVue
  beforeEach(() => {
    localVue = createLocalVue()
    localVue.use(Buefy, {})
    localVue.use(VueRouter)
  })

  test('test', () => {
    const wrapper = shallowMount(HeaderPart, {
      localVue
    })
    wrapper.setData({
      showNavigation: false,
      navbarOptions: {
        fixedTop: true
      }
    }
    )
    wrapper.vm.goToAccount()
    expect(wrapper.vm.showNavigation).toBeFalsy()
  })
})
