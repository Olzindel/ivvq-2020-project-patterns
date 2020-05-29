<template>
  <div>
    <b-sidebar :open.sync="showNavigation" :fullheight="true" :overlay="true">
      <img src="../../assets/imageDefault.png" alt="People" @click="goToHome()">
      <b-menu style="padding:12px">
        <b-menu-list label="Mon compte">
          <b-menu-item icon-pack="fa" icon="info-circle" label="Info" @click="goToAccount()"></b-menu-item>
          <b-menu-item icon-pack="fa" icon="settings" label="Mon panier" @click="goToBasket()"></b-menu-item>
          <b-menu-item icon="account" label="historique d'achat">
          </b-menu-item>
        </b-menu-list>
        <b-menu-list label="Information">
          <b-menu-item label="A propos" @click="goToAPropos()"></b-menu-item>
        </b-menu-list>
      </b-menu>
    </b-sidebar>
    <b-navbar :fixed-top=navbarOptions.fixedTop class="toolbar">
      <template slot="brand">
        <b-navbar-item tag="div">
          <b-button @click="showNavigation = true">Show</b-button>
        </b-navbar-item>
      </template>
      <template slot="end">
        <b-navbar-item tag="div">
          <!--<Login/> -->
        </b-navbar-item>
      </template>
    </b-navbar>
  </div>
</template>

<script>
// import Login from './login/Login'
import router from '../../router/index'

export default {
  name: 'headerPart',
  // components: {Login},
  data: () => ({
    showNavigation: false,
    navbarOptions: {
      fixedTop: true
    }
  }),
  methods: {
    goToAccount () {
      router.push({path: '/account'})
      this.showNavigation = false
    },
    goToHome () {
      router.push({path: '/'})
      this.showNavigation = false
    },
    goToAPropos () {
      router.push({path: '/aPropos'})
      this.showNavigation = false
    },
    goToBasket () {
      router.push({path: '/basket'})
      this.showNavigation = false
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
