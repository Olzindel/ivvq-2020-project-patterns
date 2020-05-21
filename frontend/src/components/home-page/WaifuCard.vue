<template>
  <div class="media-card">
    <div @click="toggleDropdown" class="cover">
      <div class="background-color" data-v-09928904=""></div>
      <a class="img">
        <img :src=product.imageLinks[0].imageLink alt="People" class="media-img loaded">
      </a>
      <div class="overlay">
        <a class="product">{{product.name}}</a>
        <div class="merchant">test</div>
      </div>
    </div>
    <div class="data">
      <div class="scroller vb vb-invisible" style="position: relative; overflow: hidden">
        <div class="scroller-wrap vb-content"
             style="display: block; overflow: hidden scroll; height: 100%; width: 100%; box-sizing: content-box; padding-right: 20px">
          <div class="body">
            <span class="description">
              je suis un texte pour vérifier que ce que j'écris n'est pas trop coupé donc ne prété pas attention à moi.
            </span>
          </div>
        </div>
      </div>
      <div class="footer">
        <div class="infos">
          <div class="info">14.0</div>
          <div class="info">available</div>
        </div>
        <b-button @click="changeToProductInfo" class="basket">
          <b-icon icon="shopping-basket" pack="fa"></b-icon>
        </b-button>
      </div>
    </div>
  </div>
</template>

<script>
  import router from '../../router/index'
  import Vue from 'vue'

  export default {
    name: 'WaifuCard',
    data() {
      return {
        price: Math.floor(Math.random() * (50) + 5)
      }
    },
    props: {
      product: {type: Object, required: true}
    },
    methods: {
      changeToProductInfo() {
        router.push({path: '/product/' + this.product.id})
      }
    }
  }

  Vue({
    el: '#data',
    data() {
      return {
        display: false
      }
    },
    methods: {
      toggleDropdown() {
        this.display = !this.display
      }
    }
  })

</script>

<style scoped>

  .media-card {
    font-family: Overpass, -apple-system, BlinkMacSystemFont, Segoe UI, Oxygen, Ubuntu, Cantarell, Fira Sans, Droid Sans, Helvetica Neue, sans-serif;
    background: rgb(250, 252, 252);
    border-radius: 3px;
    box-shadow: 0 4px 6px rgba(49, 54, 68, .05), 0 5px 20px rgba(49, 54, 68, .08);
    display: inline-grid;
    grid-template-columns: 185px auto;
    height: 265px;
    min-width: 370px;
    overflow: hidden;
    position: relative;
    text-align: left;
    transition: box-shadow .6s ease;
  }

  .media-card:hover {
    box-shadow: 0 4px 6px rgba(49, 54, 68, .09), 0 10px 40px rgba(49, 54, 68, .3);
  }

  .cover {
    cursor: pointer;
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    position: relative;
  }

  body {
    color: #4a4a4a;
    font-size: 1em;
    font-weight: 400;
    line-height: 1.5;
  }

  .media-img {
    opacity: 1;
  }

  a {
    color: rgb(42, 42, 42);
    text-decoration: none;
    transition: color .3s;
    transition-property: color;
    transition-duration: 0.3s;
    transition-timing-function: ease;
    transition-delay: 0s;
    outline: 0;
  }

  .cover .media-img {
    position: absolute;
    width: 100%;
    height: 100%;
    object-fit: cover;
    object-position: center;
    opacity: 0;
    transition: opacity .3s;
    top: 0;
  }

  *, :after, :before {
    box-sizing: border-box;
  }

  .cover .media-img.loaded {
    opacity: 1;
  }

  .cover .overlay {
    background-color: rgba(41, 41, 41, .7);
    padding: 12px;
    position: relative;
    width: 100%;
    font-weight: 600;
  }

  a.product {
    font-size: 1.4rem;
    color: rgb(255, 255, 255);
  }

  .merchant {
    font-size: 1.4rem;
    color: rgb(255, 206, 33);
  }

  .cover .overlay a {
    position: relative;
  }

  div span {
    display: block;
  }

  .scroller {
    min-height: 0;
    overflow: hidden;
  }

  .body {
    display: flex;
    flex-direction: column;
    min-height: 0;
    padding-bottom: 10px;
    position: relative;
  }

  .description {
    color: rgb(110, 133, 158);
    display: block;
    word-wrap: break-word;
    line-height: 1;
    font-size: .6em;
  }

  .data {
    display: grid;
    grid-template-columns: 100%;
    grid-template-rows: auto 44px;
    min-height: 0;
    min-width: 0;
  }

  .cover .background-color {
    background-color: hsl(215, 81%, 56%);
  }

  .cover .background-color, .cover .media-img {
    position: absolute;
    width: 100%;
    height: 100%;
  }

  .info {
    background: hsl(43, 83%, 55%);
    border-radius: 10px;
    color: hsl(14, 80%, 30%);
    display: inline-block;
    text-align: center;
    vertical-align: middle;
    font-size: 0.7rem;
    font-weight: 700;
    height: 20px;
    line-height: 1.4rem;
    margin-right: 11px;
    padding: 0 12px;
    text-transform: lowercase;
  }

  .footer {
    background: rgb(239, 247, 251);
    display: flex;
    align-items: center;
    padding: 0 17px;
    padding-right: 14px;
  }

  .infos {
    align-items: center;
    display: flex;
    flex-wrap: wrap;
    height: 20px;
    overflow: hidden;
  }

  .basket {
    margin-left: auto;
  }
</style>
