import UserAccount from '../../../../src/components/user-option/UserAccount'
import {createLocalVue, shallowMount} from '@vue/test-utils'
import Buefy from 'buefy'

describe('UserAccount', () => {
  let localVue
  let spy
  let spyMounted
  beforeEach(() => {
    localVue = createLocalVue()
    localVue.use(Buefy, {})
    spy = jest.fn()
    spyMounted = jest.fn()
    UserAccount.mounted = spyMounted
  })

  test('update info with success', done => {
    const mutate = () => {
      return Promise.resolve({
        data: {
          updateUserAdress: {
            id: 5
          }
        }
      })
    }
    const wrapper = shallowMount(UserAccount, {
      localVue,
      mocks: {
        $apollo: {
          mutate
        }
      }
    })
    wrapper.setData({
      user: {
        firstName: 'firstName',
        lastName: 'lastName',
        gender: 'M',
        email: 'email',
        street: 'street',
        postalCode: '12345',
        city: 'Toulouse'
      },
      password1: 'azerty',
      password2: 'azerty'
    })
    wrapper.vm.$buefy.toast.open = spy
    wrapper.vm.updateInfo()
    setTimeout(() => {
      done()
      expect(spy).toBeCalledWith({
        duration: 3000,
        message: 'Informations mises à jour',
        position: 'is-bottom',
        type: 'is-success'
      })
    })
  })
  // TODO: change to test when solve
  xtest('update info with different password', done => {
    const mutate = () => {
      return Promise.resolve({
        data: {
          updateUserAdress: {
            id: 5
          }
        }
      })
    }
    const wrapper = shallowMount(UserAccount, {
      localVue,
      mocks: {
        $apollo: {
          mutate
        }
      }
    })
    wrapper.setData({
      user: {
        firstName: 'firstName',
        lastName: 'lastName',
        gender: 'M',
        email: 'email',
        street: 'street',
        postalCode: '12345',
        city: 'Toulouse'
      },
      password1: 'azerty',
      password2: 'ytreza'
    })
    wrapper.vm.$buefy.toast.open = spy
    wrapper.vm.updateInfo()
    setTimeout(() => {
      done()
      expect(spy).toBeCalledWith({
        duration: 3000,
        message: 'Les mots de passe doivent être identiques',
        position: 'is-bottom',
        type: 'is-danger'
      })
    })
  })

  test('update info with empty field', done => {
    const mutate = () => {
      return Promise.resolve({
        data: {
          updateUserAdress: {
            id: 5
          }
        }
      })
    }
    const wrapper = shallowMount(UserAccount, {
      localVue,
      mocks: {
        $apollo: {
          mutate
        }
      }
    })
    wrapper.setData({
      user: {
        firstName: 'firstName',
        lastName: 'lastName',
        gender: 'M',
        email: '',
        street: 'street',
        postalCode: '12345',
        city: 'Toulouse'
      },
      password1: 'azerty',
      password2: 'azerty'
    })
    wrapper.vm.$buefy.toast.open = spy
    wrapper.vm.updateInfo()
    setTimeout(() => {
      done()
      expect(spy).toBeCalledWith({
        duration: 3000,
        message: 'Un champ est vide',
        position: 'is-bottom',
        type: 'is-danger'
      })
    })
  })

  test('update info with no password', done => {
    const mutate = () => {
      return Promise.resolve({
        data: {
          updateUserAdress: {
            id: 5
          }
        }
      })
    }
    const wrapper = shallowMount(UserAccount, {
      localVue,
      mocks: {
        $apollo: {
          mutate
        }
      }
    })
    wrapper.setData({
      user: {
        firstName: 'firstName',
        lastName: 'lastName',
        gender: 'M',
        email: 'email',
        street: 'street',
        postalCode: '12345',
        city: 'Toulouse'
      },
      password1: '',
      password2: ''
    })
    wrapper.vm.$buefy.toast.open = spy
    wrapper.vm.updateInfo()
    setTimeout(() => {
      done()
      expect(spy).toBeCalledWith({
        duration: 3000,
        message: 'Informations mises à jour',
        position: 'is-bottom',
        type: 'is-success'
      })
    })
  })
})
