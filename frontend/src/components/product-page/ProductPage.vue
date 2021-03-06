<template>
  <div>
    <div v-if="product.id === -1">
      Erreur du chargement du produit...
    </div>

    <div v-else-if="product === {}">
      <b-icon class="icon loading-icon"></b-icon>
    </div>

    <div class="columns main-layout is-mobile" v-else>
      <div class="column is-5 productImage">
        <vueper-slides :arrows="carousel.options.useArrow"
                       :bullets="carousel.options.useBulletPoints"
                       :fixed-height="true" :touchable="false"
                       fade lazy
                       lazy-load-on-drag>
          <template v-slot:arrow-left>
            <div class="icon is-medium icon-arrow-left">
              <img style="object-fit: contain" src='../../assets/product-page/pointingLeft.svg'/>
            </div>
          </template>
          <template v-slot:arrow-right>
            <div class="icon is-medium icon-arrow-right">
              <img style="object-fit: contain" src='../../assets/product-page/pointingRight.svg'/>
            </div>
          </template>
          <vueper-slide v-for="image in product.imageLinks"
                        :image="image.imageLink"
                        :key="image.id"
                        v-if="product.imageLinks.length">
            <template v-slot:loader>
              <b-icon class="icon icon-loader spinning"/>
            </template>
          </vueper-slide>

          <vueper-slide image="../../assets/product-page/no-image-icon.png" v-else>
            <template v-slot:loader>
              <i class="icon icon-loader spinning"/>
              <b-icon class="icon loading-icon">
                <span>Loading...</span>
              </b-icon>
            </template>
          </vueper-slide>
        </vueper-slides>
      </div>

      <div class="column productInfos is-vcentered is-centered">
        <div class="columns is-multiline">
          <div class="column is-12 section box">
            <h1 class="title">{{product.name}}</h1>
            <h2 class="subtitle">{{product.price}} €</h2>
            <article class="description" v-if="product.description">
              {{product.description}}
            </article>
            <div v-else>
              Aucune description pour ce produit.
            </div>
          </div>
          <div class="column add-to-basket">
            <b-button @click="getUserOrder()">Ajouter au panier</b-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from 'vue'
import Router from 'vue-router'
import {VueperSlide, VueperSlides} from 'vueperslides'

import gql from 'graphql-tag'

Vue.use(Router)

export default {
  name: 'ProductPage',
  components: {VueperSlides, VueperSlide},
  props: ['productId'],
  data () {
    return {
      product: {
        id: -1,
        name: '',
        imageLinks: [],
        price: '',
        description: ''
      },
      carousel: {
        options: {
          useArrow: true,
          useBulletPoints: true
        }
      },
      order: -1,
      quantity: 1,
      test: -1
    }
  },
  methods: {
    getProductInfo () {
      this.$apollo.query({
        query: gql`query ProductInfos($productId: ID!) {
          productInfos: product(productId: $productId){
            id,
            name,
             imageLinks{
              id,
              imageLink
             },
            price,
            description
          }
        }`,
        variables: {
          productId: this.productId
        }
      }).then(data => {
        this.product = data.data.productInfos
      })
    },
    addABasket () {
      this.$apollo.mutate({
        mutation: gql`mutation createOrder($input: OrderInput) {
          createOrder: createOrder(
            input: $input){
            id
          }}`,
        variables: {
          input: {
            userId: this.$store.getters.user.id,
            status: 'BASKET'
          }
        }
      }).then(data => {
        this.addThisProduct(data.data.createOrder.id)
      })
    },
    addThisProduct (orderId) {
      this.$apollo.mutate({
        mutation: gql`mutation createOrderItem($input: OrderItemInput) {
          createOrderItem: createOrderItem(
            input: $input){
            id
          }}`,
        variables: {
          input: {
            quantity: 1,
            productId: this.product.id,
            orderId: orderId
          }
        }
      }).then(data => this.success()).catch((error) => this.danger(error))
    },
    getUserOrder () {
      if (localStorage.getItem('connection-token')) {
        this.$apollo.query({
          query: gql`query user($id: ID!){
            getuser:user(userId: $id){
               orders{
                  id,
                  status
                 }
            }
        }`,
          variables: {
            id: this.$store.getters.user.id
          },
          fetchPolicy: 'no-cache'
        }
        ).then(data => {
          const basket = data.data.getuser.orders.filter(function (order) {
            if (order.status === 'BASKET') {
              return order
            }
          })
          if (basket.length === 0) {
            this.addABasket()
          } else {
            this.addThisProduct(basket[0].id)
          }
        }).catch(() => {
          this.notifyError()
        })
      } else {
        this.danger()
      }
    },
    danger () {
      this.$buefy.toast.open({
        duration: 5000,
        message: 'Connectez vous pour ajouter cette waifu à votre panier',
        position: 'is-bottom',
        type: 'is-danger'
      })
    },
    notifyError () {
      this.$buefy.toast.open({
        duration: 5000,
        message: 'Une erreur est survenue. Réessayez plus tard',
        position: 'is-bottom',
        type: 'is-danger'
      })
    },
    success () {
      this.$buefy.toast.open({
        message: this.product.name + ' a été ajouté à votre panier',
        type: 'is-success'
      })
    }
  },
  mounted () {
    this.getProductInfo()
  }
}
</script>

<style scoped>
  .icon-arrow-left {
    background: white;
    border-radius: 32px;
  }

  .icon-arrow-right {
    background: white;
    border-radius: 32px;
  }

  .productInfos {
    text-align: left;
  }

  .productImage {
    max-width: 100%;
    height: auto;
  }

  .vueperslide--loading .vueperslide__content-wrapper {
    display: none !important;
  }

  .vueperslides--fixed-height {
    height: 500px;
  }

  .main-layout {
    padding: 15px 15px 15px 15px;
  }

  .add-to-basket {
    display: flex;
    align-items: end;
  }
</style>
