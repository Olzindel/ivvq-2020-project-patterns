<template>
  <div>
    <div>
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
      <md-toolbar >
        <md-button @click="showNavigation = true">
          <md-icon class="fa fa-bars"></md-icon>
        </md-button>
      </md-toolbar>
      <md-drawer :md-active.sync="showNavigation" md-swipeable>
        <md-toolbar class="md-transparent" md-elevation="0">
          <span class="md-title">My App name</span>
        </md-toolbar>

        <md-list>
          <md-list-item>
            <span class="md-list-item-text">Inbox</span>
          </md-list-item>

          <md-list-item>
            <span class="md-list-item-text">Sent Mail</span>
          </md-list-item>

          <md-list-item>
            <span class="md-list-item-text">Trash</span>
          </md-list-item>

          <md-list-item>
            <span class="md-list-item-text">Spam</span>
          </md-list-item>
        </md-list>
      </md-drawer>
    </div>
  <div class="div">
  <img class="img"  src="../assets/titreBis.png" >
    </div>
  </div>
</template>

<script>
import Vue from 'vue'
import {MdDrawer, MdList, MdButton, MdIcon} from 'vue-material/dist/components'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'
Vue.use(MdButton)
Vue.use(MdDrawer)
Vue.use(MdList)
Vue.use(MdIcon)

export default {name: 'bandeauTitre',
  data: () => ({
    showNavigation: false
  })}
</script>

<style scoped>
  .div {
    margin: 0 0;
    display: inline-block;
    height: 100%;
    max-width: 100%;
  }

  .img {
    display: block;
    height: auto;
    max-width: 100%;
  }

</style>
