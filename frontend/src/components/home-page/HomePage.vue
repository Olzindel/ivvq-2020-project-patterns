<template>
  <!-- style="display: grid; grid-template-columns: 25% 25% 25% 25%"  -->
  <div class="columns is-multiline is-centered is-flex ">
    <div v-for='product in products' :key="product.id">
      <WaifuCard :product='product'/>
    </div>
  </div>
</template>

<script>
import WaifuCard from './WaifuCard'
export default {
  name: 'HomePage',
  data () {
    return {
      products: [
        {
          id: 1,
          name: 'waifu 1',
          price: 15,
          imageLink: 'https://i.pinimg.com/originals/4b/9b/65/4b9b65f82c8bbd883c479bd47e7b5b57.png'
        },
        {
          id: 2,
          name: 'waifu 2',
          price: 98,
          imageLink: 'https://i.pinimg.com/originals/4b/9b/65/4b9b65f82c8bbd883c479bd47e7b5b57.png'
        },
        {
          id: 3,
          name: 'waifu 3',
          price: 65,
          imageLink: 'https://i.pinimg.com/originals/4b/9b/65/4b9b65f82c8bbd883c479bd47e7b5b57.png'
        },
        {
          id: 4,
          name: 'waifu 4',
          price: 75,
          imageLink: 'https://i.pinimg.com/originals/4b/9b/65/4b9b65f82c8bbd883c479bd47e7b5b57.png'
        },
        {
          id: 5,
          name: 'waifu 5',
          price: 13,
          imageLink: 'https://i.pinimg.com/originals/4b/9b/65/4b9b65f82c8bbd883c479bd47e7b5b57.png'
        },
        {
          id: 6,
          name: 'waifu 6',
          price: 45,
          imageLink: 'https://i.pinimg.com/originals/4b/9b/65/4b9b65f82c8bbd883c479bd47e7b5b57.png'
        }
      ]
    }
  },
  components: {WaifuCard}
}
</script>

<style scoped>

</style>
