<template>
  <div>
    <div class="title">
      Mon panier
    </div>
   <div class="columns is-multiline is-centered is-flex " v-if="order.length !== 0" >
      <div v-for='orderItem in order[0].OrderItems' :key="orderItem.id">
        <basket-card class="cardSize" :orderItem='orderItem' @deleteItem="deleteItem"/>
      </div>
    </div>
    <div>
      montant de votre commande {{this.price}} €
    <b-button @Click="validateBasket()">je valide mon panier</b-button>
    </div>
  </div>
</template>

<script>
import BasketCard from './basketCard'
import gql from 'graphql-tag'

export default {
  name: 'basketPage',
  components: {BasketCard},
  data () {
    return {
      order: []
    }
  },
  computed: {
    price: function () {
      let sum = 0
      if (this.order.length !== 0) {
        this.order[0].OrderItems.forEach(function (OrderItem) {
          sum += OrderItem.quantity * OrderItem.product.price
        })
      }
      return sum
    }
  },
  methods: {
    deleteItem (idItem) {
      console.log(idItem)
      this.$apollo.mutate({
        mutation: gql`mutation deleteOrderItem ($orderItemId: ID!){
        deleteOrderItem : deleteOrderItem(orderItemId: $orderItemId)
      }`,
        variables: {
          orderItemId: idItem
        }
      }).then(data => {
        this.order[0].OrderItems = this.order[0].OrderItems.filter(function (orderItem) {
          if (orderItem.id !== data.data.deleteOrderItem) {
            return orderItem
          }
        })
      })
    }
  },
  apollo: {
    BasketInfo () {
      return {
        query: gql`query user($id: ID!){
            getuser:user(userId: $id){
               orders{
                  id,
                  status,
                  OrderItems{
                    id,
                    quantity,
                    product{
                      id,
                      name,
                      price,
                      stock,
                      description,
                      imageLinks{
                        imageLink
                      }
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
        update: data => {
          this.order = data.getuser.orders.filter(function (order) {
            if (order.status === 'BASKET') {
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
  .cardSize {
    width: 300px;
    padding: 12px;
  }
  .title {
    font-size: 50px;
    justify-content: center;
    padding: 24px;
  }
</style>
