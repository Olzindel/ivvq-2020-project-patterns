<template>
  <div>
    <h1 style="margin-bottom: 24px">Commande Réalisée</h1>
    <div class="board">
      <div>
        id de la commande
      </div>
      <div>
        Liste des produits
      </div>
      <div>
        Prix de la commande
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
        {{calculPrice(order)}}€
      </div>
    </div>
  </div>
</template>

<script>
import gql from 'graphql-tag'

export default {
  name: 'UserOrder',
  data () {
    return {
      user: {},
      orders: {}
    }
  },
  methods: {
    calculPrice (order) {
      let sum = 0
      order.orderItems.forEach((item) => {
        sum += item.quantity * item.product.price
      })
      return sum
    }
  },
  apollo: {
    BasketInfo () {
      return {
        query: gql`query user($id: ID!){
            getuser:user(userId: $id){
               id,
               orders{
                  id,
                  status,
                  orderItems{
                    id,
                    quantity,
                    product{
                      id,
                      name,
                      price,
                    }
                  }
               }
            }
        }`,
        variables () {
          return {
            id: localStorage.getItem('user')
          }
        },
        fetchPolicy: 'no-cache',
        update: data => {
          this.user = data.getuser
          this.orders = data.getuser.orders.filter(function (order) {
            if (order.status === 'PAID') {
              return order
            }
          })
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
