<template>
  <div>I am the page for id {{productId}}</div>
</template>

<script>
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default {
  name: 'ProductPage',
  props: ['productId']
}
</script>

<style scoped>

</style>
