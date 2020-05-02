<template>
  <div class="menuBar">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
      <md-toolbar class="toolbar">
        <md-button @click="showNavigation = true">
          <md-icon class="fa fa-bars"></md-icon>
        </md-button>
        <md-button>
          connexion
        </md-button>
      </md-toolbar>
      <md-drawer :md-active.sync="showNavigation" md-swipeable>
        <md-toolbar class="md-transparent" md-elevation="0">
          <span class="md-title">My App name</span>
        </md-toolbar>

        <md-list>
          <!-- hidden="" -->
          <md-list-item>
            <span class="md-list-item-text">accueil</span>
          </md-list-item>

          <md-list-item>
            <span class="md-list-item-text">Mon compte</span>
          </md-list-item>

          <md-list-item>
            <span class="md-list-item-text">Mon panier</span>
          </md-list-item>

          <md-list-item>
            <span class="md-list-item-text">historique</span>
          </md-list-item>

          <md-list-item>
            <span class="md-list-item-text">deconnexion</span>
          </md-list-item>
        </md-list>
      </md-drawer>
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
  max-height: 56px
}
</style>
