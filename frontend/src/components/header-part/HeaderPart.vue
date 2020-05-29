<template>
  <div>
    <b-sidebar :open.sync="showNavigation" :fullheight="true" :overlay="true">
      <img src="../../assets/imageDefault.png" alt="People" @click="goToHome()">
      <b-menu style="padding:12px">
        <b-menu-list label="Les produits">
          <b-menu-item icon-pack="fa" icon="eye" label="Voir les produits" @click="goToHome()"/>
        </b-menu-list>
        <b-menu-list label="Mon compte">
          <div v-if="isAuthenticated()">
            <b-menu-item icon-pack="fa" icon="shopping-basket" label="Mon panier" @click="goToBasket()"/>
            <b-menu-item icon-pack="fa" icon="history" label="Historique d'achat" @click="goToHistory()"/>
            <b-menu-item icon="account" label="Mes informations personnelles" @click="goToAccount()"/>
          </div>
          <div v-else>
            <b-menu-item icon="login" label="Se connecter" @click="goToLogin()"/>
            <b-menu-item icon="account-plus" label="S'inscrire" @click="goToSignUp()"/>
          </div>
        </b-menu-list>
        <b-menu-list v-if="isAuthenticated() && store.getters.isMerchant" label="Administration">
          <div>
            <!-- Insert merchant options there -->
          </div>
        </b-menu-list>
        <b-menu-list label="Information">
          <b-menu-item icon-pack="fa" icon="info-circle" label="A propos" @click="goToAPropos()"></b-menu-item>
        </b-menu-list>
      </b-menu>
      <b-button v-if="isAuthenticated()" class="button" type="is-text" @click="logout()">Se déconnecter</b-button>
    </b-sidebar>
    <b-navbar :fixed-top=navbarOptions.fixedTop class="toolbar">
      <template slot="brand">
        <b-navbar-item tag="div">
          <b-button @click="showNavigation = true" icon-left="menu"/>
        </b-navbar-item>
      </template>
    </b-navbar>
  </div>
</template>

<script>
import Login from './login/Login'
import router from '../../router/index'
import store from '../../store'

export default {
  name: 'headerPart',
  components: {Login},
  data: () => ({
    showNavigation: false,
    navbarOptions: {
      fixedTop: true
    },
    store: store
  }),
  methods: {
    goToAccount () {
      router.push('/account')
      this.showNavigation = false
    },
    goToHome () {
      router.push('/')
      this.showNavigation = false
    },
    goToAPropos () {
      router.push('/aPropos')
      this.showNavigation = false
    },
    goToBasket () {
      router.push('/basket')
      this.showNavigation = false
    },
    goToLogin () {
      router.push('/login')
      this.showNavigation = false
    },
    goToSignUp () {
      router.push('/signup')
      this.showNavigation = false
    },
    logout () {
      store.commit('logout')
      localStorage.setItem('connection-token', '')
      this.goToHome()
    },
    goToHistory () {
      // TODO
      // router.push('/history')
      this.showNavigation = false
    },
    isAuthenticated () {
      return localStorage.getItem('connection-token')
    }
  }
}
</script>

<style scoped>
  .toolbar {
    min-height: 56px;
    max-height: 56px;
  }

</style>
