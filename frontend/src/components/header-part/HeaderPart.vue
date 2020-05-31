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
        <!-- Merchant -->
        <b-menu-list v-if="isAuthenticated() && $store.getters.isMerchant" label="Administration">
          <div>
            <b-menu-item label="Gérer les stocks" @click="goToProductStockPage()"/>
            <b-menu-item label="Historique des commandes clients" @click="goToOrders()"/>
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

export default {
  name: 'headerPart',
  data: () => ({
    showNavigation: false,
    navbarOptions: {
      fixedTop: true
    }
  }),
  methods: {
    goToAccount () {
      this.$router.push('/account')
      this.showNavigation = false
    },
    goToHome () {
      this.$router.push('/')
      this.showNavigation = false
    },
    goToAPropos () {
      this.$router.push('/aPropos')
      this.showNavigation = false
    },
    goToBasket () {
      this.$router.push('/basket')
      this.showNavigation = false
    },
    goToLogin () {
      this.$router.push('/login')
      this.showNavigation = false
    },
    goToSignUp () {
      this.$router.push('/signup')
      this.showNavigation = false
    },
    logout () {
      this.$store.commit('logout')
      localStorage.setItem('connection-token', '')
      this.goToHome()
    },
    goToHistory () {
      this.$router.push('/history')
      this.showNavigation = false
    },
    goToProductStockPage () {
      this.$router.push('/stock')
      this.showNavigation = false
    },
    goToOrders () {
      this.$router.push('/orders')
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
