<template>
  <div>
  <md-card  md-with-hover style="margin: 24px" onclick="test()">
    <md-card-header-text>
      <div class="md-title">Poster avec les meilleurs Waifu</div>
    </md-card-header-text>
    <md-card-media>
      <img src="../assets/imageDefault.png" alt="People">
    </md-card-media>

    <md-card-content style="padding:0">
        <div>{{price}}€</div>
    </md-card-content>
    <md-card-actions style="padding:0">
      <md-button>
        <md-icon class="fa fa-shopping-basket"></md-icon>
      </md-button>
    </md-card-actions>
  </md-card>
  </div>
</template>

<script>
export default {
  name: 'WaifuCard',
  data () {
    return {
      price: Math.floor(Math.random() * (100 - 0 + 1) + 5)
    }
  }
}
</script>

<style scoped>

</style>
