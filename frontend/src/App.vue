<template>
  <div id="app" >
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdn.materialdesignicons.com/2.5.94/css/materialdesignicons.min.css">
    <Header/>
    <Banner/>
    <router-view/>
  </div>
</template>

<script>
import Header from './components/header-part/HeaderPart'
import Banner from './components/header-part/Banner'
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'
import 'vueperslides/dist/vueperslides.css'
import Vue from 'vue'
import VueApollo from 'vue-apollo'

Vue.use(VueApollo)
Vue.use(Buefy)

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
