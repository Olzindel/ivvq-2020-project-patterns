<template>
  <div class="cardSize">
    <div class="card" style="margin: 24px">
      <div >
        <div class="card-header-title is-centered">
          {{product.name}}
        </div>
        <div>
          <img :src=product.imageLink alt="People">
        </div>
        <div class="card-content" style="padding:0">
          <div>{{product.price}}€</div>
          <div v-if="test !== undefined">{{test.data.users[0].fullName}}</div>
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
      price: Math.floor(Math.random() * (50) + 5),
      test: undefined
    }
  },
  props: {
    product: {type: Object, required: true}
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
