<template>
  <div class="cardSize" style="margin: 20px">
    <div class="card">
      <div>
        <div class="card-header-title is-centered">
          {{product.name}}
        </div>
        <div class="img">
          <img :src=product.imageLinks[0].imageLink alt="People" class="img1">
        </div>
        <div class="card-content" style="padding:0">
          <div>{{product.price}}€</div>
        </div>
      </div>
      <b-button @click="changeToProductInfo">
        <b-icon icon="shopping-basket" pack="fa"></b-icon>
      </b-button>
    </div>
  </div>
</template>

<script>
import router from '../../router/index'

export default {
  name: 'WaifuCard',
  data () {
    return {
      price: Math.floor(Math.random() * (50) + 5)
    }
  },
  props: {
    product: {type: Object, required: true}
  },
  methods: {
    changeToProductInfo () {
      router.push({path: '/product/' + this.product.id})
    }
  }
</script>

<style scoped>
  .cardSize {
    max-width: 250px;
    min-width: 250px;
  }

  .img {
    display: flex;
    justify-content: center;
    align-content: center;
  }

  .img1 {
    display: flex;
    height: 200px;
    width: 250px;
    object-fit: cover;
  }

  .card {
    box-shadow: 4px 4px 1px 1px rgba(100, 100, 100, .5);
    border-radius: 2em;
    border: rgba(50, 50, 50, .2) solid 1px
  }
</style>
