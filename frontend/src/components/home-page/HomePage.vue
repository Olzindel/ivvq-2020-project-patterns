<template>
  <div class="columns is-multiline is-centered is-flex ">
    <div v-for='product in products' :key="product.id">
      <WaifuCard :product='product'/>
    </div>
  </div>
</template>

<script>
import WaifuCard from './WaifuCard'
import gql from 'graphql-tag'
export default {
  name: 'HomePage',
  data () {
    return {
      products: []
    }
  },
  apollo: {
    products: {
      query: gql`query ProductInfos($count: Int!) {
            products(count: $count){
            id
            name
            price
            imageLinks{
            imageLink
            }
            status
         }
        }`,
      variables: {
        count: 10
      }
    }
  },
  components: {WaifuCard}
}
</script>

<style scoped>

</style>
