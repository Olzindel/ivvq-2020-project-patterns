// install the vue plugin
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    user: null
  },
  getters: {
    isMerchant: (state) => {
      return state.user && state.user.role === 'MERCHANT'
    },
    user: state => {
      return state.user
    }
  },
  mutations: {
    connect (state, user) {
      state.user = user
    },
    logout (state) {
      state.connectionToken = ''
      state.user = null
    }
  }
})

export default store
