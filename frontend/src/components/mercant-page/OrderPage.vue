<template>
<div>
  <h1 class="title" style="margin-bottom: 24px">Commandes réalisées</h1>
  <div class="board">
    <div>
      Id de la commande
    </div>
    <div>
      Liste des produits
    </div>
    <div>
      Adresse de l'acheteur
    </div>
  </div>
  <div v-for='order in orders' :key="order.id" class="board">
    <div>
      {{order.id}}
    </div>
    <div>
      <div v-for='orderItem in order.orderItems' :key='orderItem.id'>
        {{orderItem.quantity}}  {{orderItem.product.name}}
      </div>
    </div>
    <div>
      <div>
        {{order.user.firstName}} {{order.user.lastName}}
      </div>
      <div>
        {{order.user.street}}
      </div>
      <div>
        {{order.user.postalCode}} {{order.user.city}}
      </div>
    </div>
  </div>
</div>
</template>

<script>
import gql from 'graphql-tag'

export default {
  name: 'OrderPage',
  data () {
    return {
      orders: []
    }
  },
  methods: {
    getAllProduct () {
      this.$apollo.query({
        query: gql`
      query orders ( $count : Int! ){
        getAllOrders : orders(count : $count){
          id,
          status
          orderItems{
            quantity,
            product{
              name
            }
          }
          user {
          firstName,
          lastName,
          street,
          postalCode,
          city
          }
      }}`,
        variables: {
          count: 50
        },
        fetchPolicy: 'no-cache'
      }).then(data => {
        this.orders = data.data.getAllOrders.filter((order) => {
          if (order.status === 'PAID') {
            return order
          }
        }
        )
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
    margin-left: 10%;
    margin-right: 10%;
    margin-bottom: 8px;
    border-bottom: solid black 1px;
  }
</style>
