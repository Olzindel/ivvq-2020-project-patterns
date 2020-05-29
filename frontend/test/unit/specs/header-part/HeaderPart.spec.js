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
    // const router = new VueRouter({index})
    const wrapper = shallowMount(HeaderPart, {
      localVue
    })

    // const spyDelete = jest.spyOn(wrapper.vm, 'router')
    wrapper.setData({
      showNavigation: false,
      navbarOptions: {
        fixedTop: true
      }
    }
    )
    wrapper.vm.goToAccount()
    // expect(spyDelete).toHaveBeenCalled()
    // expect(wrapper.find('.home').text()).toBe('/home')
    expect(wrapper.vm.showNavigation).toBeFalsy()
  })
})
