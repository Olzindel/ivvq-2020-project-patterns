<template>
  <div>
    <b-sidebar :open.sync="showNavigation" :fullheight="true" :overlay="true" >
      <img src="../assets/imageDefault.png" alt="People">
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
    <b-navbar class="toolbar">
      <template slot="brand">
        <b-navbar-item tag="div">
          <b-button @click="showNavigation = true">Show</b-button>
        </b-navbar-item>
      </template>
      <template slot="end">
        <b-navbar-item tag="div">
          <div class="buttons">
            <a class="button is-primary">
              <strong>Sign up</strong>
            </a>
            <a class="button is-light">
              Log in
            </a>
          </div>
        </b-navbar-item>
      </template>
    </b-navbar>
  </div>
</template>

<script>
export default {name: 'header',
  data: () => ({
    showNavigation: false
  })}
</script>

<style scoped>
.toolbar{
  display:flex;
  position:fixed;
  justify-content: space-between;
  min-height: 56px;
  max-height: 56px;
  width: 100%;
}
</style>
