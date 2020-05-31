<template>
  <div>
<h1 class="title">Votre stock de produits</h1>
    <div class="board">
      <div>
       Id du produit
      </div>
      <div>
        Nom du produit
      </div>
      <div>
        Stock disponible
      </div>
    </div>
  <div v-for='product in products' :key="product.id" class="board">
    <div class="item" >
      {{product.id}}
    </div>
    <div class="item">
    {{product.name}}
    </div>
    <div class="item">
      <b-field>
        <b-numberinput controls-position="compact" v-on:input="changeStockProduct(product.id,product.stock)"
                       controls-rounded v-model="product.stock">
        </b-numberinput>
      </b-field>
    </div>
  </div>
  </div>
</template>

<script>
import gql from 'graphql-tag'

export default {
  name: 'ProductStockPage',
  data () {
    return {
      products: []
    }
  },
  methods: {
    changeStockProduct (idProduct, stockProduct) {
      this.$apollo.mutate({
        mutation: gql`mutation updateProduct($productId : ID!, $input: ProductInput!){
        updateStockProduct : updateProduct(productId : $productId, input: $input){
        id
        }}`,
        variables: {
          productId: idProduct,
          input: {
            stock: stockProduct
          }
        }
      })
    },
    getAllProduct () {
      this.$apollo.query({
        query: gql`
      query products ( $count : Int! ){
        getAllProducts : products(count : $count){
          id,
          name,
          stock
      }}`,
        variables: {
          count: 50
        },
        fetchPolicy: 'no-cache'
      }).then(data => {
        console.log(data)
        this.products = data.data.getAllProducts
      })
    }
  },
  mounted () {
    this.getAllProduct()
  }
}

</script>

<style scoped>
.board{
  display: grid;
  grid-template-columns: 20% 40% 40%;
  margin: 8px 10%;
}
  .item{
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>
