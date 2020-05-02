<template>
  <div id="app">
    <Header/>
    <banner style="margin-top: 56px"/>
    <router-view/>
  </div>
</template>

<script>
import Header from './components/Header'
import Banner from './components/Banner'
import Vue from 'vue'
import {MdDrawer, MdList, MdButton, MdIcon, MdToolbar, MdCard} from 'vue-material/dist/components'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'
Vue.use(MdButton)
Vue.use(MdDrawer)
Vue.use(MdList)
Vue.use(MdIcon)
Vue.use(MdToolbar)
Vue.use(MdCard)

export default {
  name: 'App',
  components: {Header, Banner}
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
