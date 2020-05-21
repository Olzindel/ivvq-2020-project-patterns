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
          <vueper-slide :image="image.imageLink"
                        :key="i"
                        v-for="(image, i) in productInfos.imageLinks"
                        v-if="productInfos.imageLinks.length">
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
            <h1 class="title">{{productInfos.name}}</h1>
            <h2 class="subtitle">{{productInfos.price}} €</h2>
            <article class="description" v-if="productInfos.description">
              {{productInfos.description}}
            </article>
            <div v-else>
              Aucune description pour ce produit.
            </div>
          </div>
          <div class="column add-to-basket">
            <b-button @click="addToBasket()">Ajouter au panier</b-button>
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
            name
             imageLinks{
              imageLink
             }
            price
            status
            description
          }
        }`,
        variables () {
          return {
            productId: this.productId
          }
        }
      }
    }
  },
  data () {
    return {
      productInfos: {
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
      test: -1,
      user: 1
    }
  },
  methods: {
    addToBasket () {
      this.$apollo.mutate({
        mutation: gql`mutation createOrder($userId: ID!) {
          createOrder: createOrder(
            orderStatus: BASKET,
            userId: $userId){
            id
          }}`,
        variables: {
          userId: this.user
        }
      }).then(data =>
        console.log('my test' + data))
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
