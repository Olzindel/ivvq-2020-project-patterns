<template>
  <div>I am the page for id {{productId}}
    <div class="columns">
      <div class="column is-two-fifths">
        <b-carousel :autoplay="carouselOptions.autoPlay"
                    :arrow-hover="carouselOptions.arrowHover"
                    :arrow="carouselOptions.arrow"
                    :arrow-both="carouselOptions.arrowBoth"
                    :icon-pack="carouselOptions.iconPack"
                    :icon-size="carouselOptions.iconSize"
                    :icon-prev="carouselOptions.iconPrev"
                    :icon-next="carouselOptions.iconNext">
          <b-carousel-item v-for="(carousel, i) in carousels" :key="i">
            <section :class="`hero is-medium is-${carousel.color}`">
              <div class="hero-body has-text-centered">
                <h1 class="title">{{carousel.text}}</h1>
              </div>
            </section>
          </b-carousel-item>
        </b-carousel>
      </div>
      <div class="column">
        Description will come there
        <i class="leftAndRightCustomIcons point-to-right is-small"></i>
        <b-icon class="machin">
        </b-icon>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const customIconConfig = {
  customIconPacks: {
    'leftAndRightCustomIcons': {
      sizes: {
        'default': 'is-small',
        'is-small': '',
        'is-medium': 'is-size-3',
        'is-large': 'is-size-1'
      },
      iconPrefix: '',
      internalIcons: {
        'point-right': 'point-to-right',
        'point-left': 'point-to-left'
      }
    },
    'ionicons': {
      sizes: {
        'default': 'is-size-5',
        'is-small': '',
        'is-medium': 'is-size-3',
        'is-large': 'is-size-1'
      },
      iconPrefix: 'ion-md-',
      internalIcons: {
        'check': 'checkmark',
        'information': 'information',
        'check-circle': 'checkmark-circle-outline',
        'alert': 'alert',
        'alert-circle': 'alert',
        'arrow-up': 'arrow-up',
        'chevron-right': 'arrow-forward',
        'chevron-left': 'arrow-back',
        'chevron-down': 'arrow-down',
        'eye': 'eye',
        'eye-off': 'eye-off',
        'menu-down': 'arrow-dropdown',
        'menu-up': 'arrow-dropup'
      }
    }
  }
}

export default {
  name: 'ProductPage',
  props: ['productId'],
  created () {
    this.$buefy.config.setOptions(customIconConfig)
    console.log(this.$buefy.config.getOptions())
  },
  data () {
    return {
      carouselOptions: {
        autoPlay: false,
        arrow: true,
        arrowHover: false,
        arrowBoth: true,
        iconPack: 'leftAndRightCustomIcons',
        iconSize: 'is-large',
        iconPrev: 'chevron-left',
        iconNext: 'point-right'
      },
      carousels: [
        {text: 'Slide 1', color: 'primary'},
        {text: 'Slide 2', color: 'info'},
        {text: 'Slide 3', color: 'success'},
        {text: 'Slide 4', color: 'warning'},
        {text: 'Slide 5', color: 'danger'}
      ]
    }
  }
}
</script>

<style scoped>
  @import "https://unpkg.com/ionicons@4.5.10-0/dist/css/ionicons.min.css";
  .point-to-right:before {
    content: url("../../assets/product-page/pointingLeftLittle.png");
  }

  .point-to-left:before {
    content: url("../../assets/product-page/pointingRight.png");
  }
</style>
