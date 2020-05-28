<template>
<div>
  <h1 style="margin-bottom: 24px">Commande Réalisée</h1>
  <div class="board">
    <div>
      id de la commande
    </div>
    <div>
      liste des produits
    </div>
    <div>
      adresse de l'acheteur
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
        {{order.User.firstName}} {{order.User.lastName}}
      </div>
      <div>
        {{order.User.street}}
      </div>
      <div>
        {{order.User.postalCode}} {{order.User.city}}
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
  apollo: {
    getAllProduct () {
      return {
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
          User {
          firstName,
          lastName,
          street,
          postalCode,
          city
          }
      }}`,
        variables () {
          return {
            count: 50
          }
        },
        fetchPolicy: 'no-cache',
        update: data => {
          this.orders = data.getAllOrders.filter((order) => {
            if (order.status === 'PAID') {
              return order
            }
          }
          )
        }
      }
    }
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
