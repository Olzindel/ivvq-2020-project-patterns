<template>
  <div id="app">
    <BandeauTitre/>
    <router-view/>
  </div>
</template>

<script>
import BandeauTitre from './components/bandeauTitre'
export default {
  name: 'App',
  components: {BandeauTitre}
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
