<template>
  <div>
  <div v-if="basket">
    <div class="title">
      Mon panier
    </div>
   <div class="columns is-multiline is-centered is-flex " v-if="!basketIsEmpty" >
      <div v-for='orderItem in order[0].orderItems' :key="orderItem.id">
        <basket-card class="cardSize" :orderItem='orderItem' @deleteItem="deleteItem"/>
      </div>
    </div>
    <div v-if="!basketIsEmpty">
      Montant de votre commande {{this.price}} €
    <b-button @click="validateBasket">Je valide mon panier</b-button>
    </div>
    <div v-else>
      <h2> Votre panier est vide </h2>
      <img class="img"  src="https://vignette.wikia.nocookie.net/fireemblem/images/3/3f/Anna_FE13_Artwork.png/revision/latest?cb=20160719200512"/>
    </div>
  </div>
    <div v-if="!basket">
      <paiement-by-card :user="user" :order="this.order[0]" />
    </div>
  </div>
</template>

<script>
import BasketCard from './BasketCard'
import gql from 'graphql-tag'
import PaiementByCard from './PaiementByCard'

export default {
  name: 'basketPage',
  components: {PaiementByCard, BasketCard},
  data () {
    return {
      order: [],
      basket: true,
      user: null
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
    },
    basketIsEmpty () {
      if (this.order.length === 0) {
        return true
      } else if (this.order[0].orderItems.length === 0) {
        return true
      } else {
        return false
      }
    }
  },
  methods: {
    deleteItem (idItem) {
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
      this.$apollo.query({
        query: gql`
      query enoughStock ( $orderId : ID! ){
        checkStockForAnOrder : enoughStock(orderId : $orderId){
        id
      }}`,
        variables: {
          orderId: parseInt(this.order[0].id)
        },
        fetchPolicy: 'no-cache'
      }).then(data => {
        if (data.data.checkStockForAnOrder.length > 0) { this.errorMessage(data.data.checkStockForAnOrder) } else {
          this.basket = false
        }
      })
    },
    errorMessage (error) {
      let message = ''
      error.forEach((error) => {
        let item = this.order[0].orderItems.filter(function (item) {
          if (error.id === item.product.id) {
            return item
          }
        })[0]
        message += 'pas assez de stock pour ' + item.product.name + ' juste ' + item.product.stock + ' produit(s) disponible' + '<br/>'
      })
      this.$buefy.toast.open({
        duration: 5000,
        message: message,
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
               id,
               firstName,
               lastName,
               email,
               gender,
               street,
               postalCode,
               city,
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
        fetchPolicy: 'no-cache',
        update: data => {
          this.user = data.getuser
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
  .img{
    height: 300px;
    width: auto;
  }
</style>
