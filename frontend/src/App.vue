<template>
  <div id="app">
    <Header/>
    <banner/>
    <router-view/>
  </div>
</template>

<script>
import Header from './components/Header'
import Banner from './components/Banner'
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
