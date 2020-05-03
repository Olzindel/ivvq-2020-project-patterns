<template>
  <div>
  <div  class="card" style="margin: 24px" onclick="test()">
    <div class="card-header">
      Poster avec les meilleurs Waifu
    </div>
    <div>
      <img src="../assets/imageDefault.png" alt="People">
    </div>

    <div class="card-content" style="padding:0">
        <div>{{price}}€</div>
      <b-button>
        <b-icon pack="fa" icon="shopping-basket"></b-icon>
      </b-button>
    </div>
  </div>
  </div>
</template>

<script>
export default {
  name: 'WaifuCard',
  data () {
    return {
      price: Math.floor(Math.random() * (50) + 5)
    }
  }
}
</script>

<style scoped>

</style>
