<template>
  <!--  -->
  <div style="display: grid; grid-template-columns: 25% 25% 25% 25%" >
    <WaifuCard />
    <WaifuCard />
    <WaifuCard />
    <WaifuCard />
    <WaifuCard />
    <WaifuCard />
    <WaifuCard />
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
