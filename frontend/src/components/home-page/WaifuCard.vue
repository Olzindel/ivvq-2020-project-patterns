<template>
  <div class="cardSize">
    <div class="card" style="margin: 24px" onclick="test()">
      <div >
        <div class="card-header-title is-centered">
          Poster avec les meilleurs Waifu
        </div>
        <div>
          <img src="../../assets/imageDefault.png" alt="People">
        </div>
        <div class="card-content" style="padding:0">
          <div>{{price}}€</div>
        </div>
      </div>
      <b-button>
        <b-icon pack="fa" icon="shopping-basket"></b-icon>
      </b-button>
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
   .cardSize{
  max-width: 400px;
  min-width: 400px;
}

  .card{
    box-shadow: 6px 6px 1px 1px rgba(10,10,10, .2);
    border-radius : 2em;
    border : rgba(50,50,50,.2) solid 1px
  }
</style>
