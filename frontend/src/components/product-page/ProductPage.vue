<template>
  <div>I am the page for id {{productId}}
    <div class="columns">
      <ApolloQuery
        :query="require('/graphql/HelloWorld.gql')"
        :variables="{ name }">
        <template slot-scope="{ result: { loading, error, data } }">
          <!-- Loading -->
          <div v-if="loading" class="loading apollo">Loading...</div>

          <!-- Error -->
          <div v-else-if="error" class="error apollo">An error occured</div>

          <!-- Result -->
          <div v-else-if="data" class="result apollo">{{ data.hello }}</div>

          <!-- No result -->
          <div v-else class="no-result apollo">No result :(</div>
        </template>
      </ApolloQuery>
      <div class="column is-two-fifths">
        <vueper-slides :bullets="carousel.options.useBulletPoints" :arrows="carousel.options.useArrow">
          <template v-slot:arrow-left>
            <i class="icon icon-arrow-left"/>
          </template>

          <template v-slot:arrow-right>
            <i class="icon icon-arrow-right"/>
          </template>

          <vueper-slide v-for="(slide, i) in carousel.slides" :key="i" :title="slide.title" :content="slide.content"/>
        </vueper-slides>
      </div>
      <div class="column">
        Description will come there
      </div>
    </div>
  </div>
</template>

<script>
import Vue from 'vue'
import Router from 'vue-router'

import {VueperSlide, VueperSlides} from 'vueperslides'
import 'vueperslides/dist/vueperslides.css'

Vue.use(Router)

export default {
  name: 'ProductPage',
  props: ['productId'],
  components: {VueperSlides, VueperSlide},

  data () {
    return {
      carousel: {
        options: {
          useArrow: true,
          useBulletPoints: true
        },
        slides: [
          {
            title: 'Slide <b style="font-size: 1.3em;color: yellow">#1</b>',
            content: 'Slide title can be HTML.<br>And so does the slide content, <span style="font-size: 1.2em;color: yellow">why not?</span>'
          },
          {
            title: 'Slide <b style="font-size: 1.3em;color: yellow">#2</b>',
            content: 'Slide title can be HTML.<br>And so does the slide content, <span style="font-size: 1.2em;color: yellow">why not?</span>'
          }
        ]
      }
    }
  }
}
</script>

<style scoped>
  .icon-arrow-right:before {
    background-image: url('../../assets/product-page/pointingRight.svg');
    background-size: 32px 32px;
    display: block;
    width: 32px;
    height: 32px;
    content: "";
  }

  .icon-arrow-left:before {
    background-image: url("../../assets/product-page/pointingLeft.svg");
    background-size: 32px 32px;
    display: block;
    width: 32px;
    height: 32px;
    content: "";
  }
</style>
