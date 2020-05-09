<template>
  <div >
    <b-sidebar  :open.sync="showNavigation" :fullheight="true" :overlay="true">
      <img src="../../assets/imageDefault.png" alt="People">
      <b-menu style="padding:12px">
        <b-menu-list label="Mon compte">
          <b-menu-item icon-pack="fa" icon="info-circle" label="Info"></b-menu-item>
          <b-menu-item icon-pack="fa"  icon="settings" label="Mon panier"></b-menu-item>
          <b-menu-item icon="account" label="historique d'achat">
          </b-menu-item>
        </b-menu-list>
        <b-menu-list label="Actions">
          <b-menu-item label="Logout"></b-menu-item>
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
          <Login/>
        </b-navbar-item>
      </template>
    </b-navbar>
  </div>
</template>

<script>
import Login from './Login'
export default {name: 'headerPart',
  components: {Login},
  data: () => ({
    showNavigation: false,
    navbarOptions: {
      fixedTop: true
    }
  })}
</script>

<style scoped>
.toolbar{
  min-height: 56px;
  max-height: 56px;
}

</style>
