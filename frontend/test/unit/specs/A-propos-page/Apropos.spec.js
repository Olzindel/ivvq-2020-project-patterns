import APropos from '../../../../src/components/A-propos-page/APropos'
import {createLocalVue, shallowMount} from '@vue/test-utils'
import Buefy from 'buefy'

describe('APropos', () => {
  let localVue
  let wrapper
  beforeEach(() => {
    localVue = createLocalVue()
    localVue.use(Buefy, {})
    wrapper = shallowMount(APropos, {
      localVue
    })
  })

  test('are data only string', () => {
    expect(typeof wrapper.vm.presentation === 'string').toBeTruthy()
    expect(typeof wrapper.vm.team === 'string').toBeTruthy()
    expect(typeof wrapper.vm.thanks === 'string').toBeTruthy()
  })
})
