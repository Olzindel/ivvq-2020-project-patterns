<template>
  <div id="app">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
import Vue from 'vue'
import VueApollo from 'vue-apollo'

Vue.use(VueApollo)
Vue.use(Buefy, {

})

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
