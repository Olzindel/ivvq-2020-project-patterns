<template>
  <!-- style="display: grid; grid-template-columns: 25% 25% 25% 25%"  -->
  <div class="columns is-multiline is-centered is-flex " >
    <WaifuCard class="column " />
    <WaifuCard class="column" />
    <WaifuCard class="column" />
    <WaifuCard class="column" />
    <WaifuCard class="column" />
    <WaifuCard class="column" />
    <WaifuCard class="column" />
  </div>
</template>

<script>
import WaifuCard from './WaifuCard'
export default {name: 'HomePage',
  components: {WaifuCard}
}
</script>

<style scoped>

</style>
