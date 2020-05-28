<template>
  <div>
    <div v-if="$apollo.queries.productInfos.error">
      Erreur du chargement du produit...
    </div>

    <div v-else-if="$apollo.queries.productInfos.loading">
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
            <i class="icon icon-arrow-left"/>
          </template>
          <template v-slot:arrow-right>
            <i class="icon icon-arrow-right"/>
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
              <i class="icon icon-loader spinning"></i>
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
            <b-button @click="getUser()">Ajouter au panier</b-button>
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
import 'vueperslides/dist/vueperslides.css'

import gql from 'graphql-tag'

Vue.use(Router)

export default {
  name: 'ProductPage',
  components: {VueperSlides, VueperSlide},
  props: ['productId'],
  apollo: {
    productInfos () {
      return {
        query: gql`query ProductInfos($productId: ID!) {
          productInfos: product(productId: $productId){
            id,
            name,
             imageLinks{
              id,
              imageLink
             },
            price,
            status,
            description
          }
        }`,
        variables () {
          return {
            productId: this.productId
          }
        },
        update: data => {
          this.product = data.productInfos
        }
      }
    }
  },
  data () {
    return {
      product: {
        id: -1,
        name: '',
        imageLinks: [],
        price: '',
        status: '',
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
    addABasket () {
      this.$apollo.mutate({
        mutation: gql`mutation createOrder($input: OrderInput) {
          createOrder: createOrder(
            input: $input){
            id
          }}`,
        variables: {
          input: {
            userId: localStorage.getItem('user'),
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
    getUser () {
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
          id: localStorage.getItem('user')
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
      }).catch((error) => { this.danger(error) })
    },
    danger (text) {
      this.$buefy.toast.open({
        duration: 5000,
        message: 'Vous devez vous connecter',
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
  }
}
</script>

<style scoped>
  .icon-arrow-left:before {
    content: url('../../assets/product-page/pointingLeft.svg');
    display: inline-flex;
    width: 32px;
    height: 32px;
    background: white;
    border-radius: 32px;
  }

  .icon-arrow-right:before {
    content: url('../../assets/product-page/pointingRight.svg');
    display: inline-flex;
    width: 32px;
    height: 32px;
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
