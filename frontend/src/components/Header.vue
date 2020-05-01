<template>
  <div>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
      <md-toolbar >
        <div style="display:flex;justify-content: space-between">
        <md-button @click="showNavigation = true">
          <md-icon class="fa fa-bars"></md-icon>
        </md-button>
        <md-button>
          connexion
        </md-button>
        </div>
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

export default {name: 'header',
  data: () => ({
    showNavigation: false
  })}
</script>

<style scoped>

</style>
