<template>
  <div>
    <div class="title">
      Mon panier
    </div>
   <div class="columns is-multiline is-centered is-flex " v-if="order.length !== 0" >
      <div v-for='orderItem in order[0].orderItems' :key="orderItem.id">
        <basket-card class="cardSize" :orderItem='orderItem' @deleteItem="deleteItem"/>
      </div>
    </div>
    <div>
      montant de votre commande {{this.price}} €
    <b-button @click="validateBasket">je valide mon panier</b-button>
    </div>
  </div>
</template>

<script>
import BasketCard from './basketCard'
import gql from 'graphql-tag'
import router from '../../router/index'

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
        this.order[0].orderItems.forEach(function (orderItem) {
          sum += orderItem.quantity * orderItem.product.price
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
        this.order[0].orderItems = this.order[0].orderItems.filter(function (orderItem) {
          if (orderItem.id !== data.data.deleteOrderItem) {
            return orderItem
          }
        })
      })
    },
    validateBasket () {
      console.log('coucou')
      this.$apollo.mutate({
        mutation: gql`mutation updateOrder ($orderId: ID!, $input: OrderInput!){
        updateOrderToPaid: updateOrder( orderId: $orderId, input: $input){
          id
        }
        }`,
        variables: {
          orderId: this.order[0].id,
          input: {
            status: 'PAID'
          }
        }
      }).then(data => {
        router.push({path: '/paiement'})
      }).catch((error) => {
        this.errorMessage(error.message)
      })
    },
    errorMessage (error) {
      let message = error.split(':')
      let id = message[3].toString().split('|')
      this.$buefy.toast.open({
        duration: 5000,
        message: 'pas assez de stock pour ' + id[0] + 'juste ' + this.order[0].orderItems.filter(function (item) {
          if (id[1] === item.product.id) {
            return item
          }
        })[0].product.stock + ' produit(s) disponible',
        position: 'is-bottom',
        type: 'is-danger'
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
                  orderItems{
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
