<template>
    <div class="cardSize">
      <div class="card" >
        <div >
          <div class="card-header-title is-centered">
            {{orderItem.product.name}}
            <b-button style="margin-left: 0" type="is-danger" @click="deleteMe()">
              X
            </b-button>
          </div>
          <div class="img">
            <img class="img1" :src=orderItem.product.imageLinks[0].imageLink alt="People">
          </div>
          <div class="card-content" style="padding:0">
            <div>{{orderItem.product.price}}€</div>
          </div>
        </div>
        <b-field @="changeProductNumber()" style="display: flex;justify-content: center">
        <b-numberinput controls-position="compact" v-on:input="changeProductNumber"
                       controls-rounded icon-pack="mdi" min="1" :editable="false" :max="orderItem.product.stock" style="width: 180px" v-model="this.orderItem.quantity">
        </b-numberinput>
      </b-field>
      </div>
    </div>
</template>

<script>
import gql from 'graphql-tag'

export default {
  name: 'basketCard',
  props: {
    orderItem: {type: Object, required: true}
  },
  methods: {
    changeProductNumber () {
      this.$apollo.mutate({
        mutation: gql`mutation updateOrderItem ($orderItemId:ID!, $input: OrderItemInput!){
          updateOrderItem : updateOrderItem(
          orderItemId: $orderItemId,
          input: $input){
            id,
            quantity
          }
          }
        `,
        variables: {
          input: {
            quantity: this.orderItem.quantity
          },
          orderItemId: this.orderItem.id
        }
      }).then(data => {
        console.log(data)
      })
    },
    deleteMe () {
      this.$emit('deleteItem', this.orderItem.id)
    }
  }
}
</script>

<style scoped>
  .img {
    display: flex;
    justify-content: center;
    align-content: center;
  }

  .img1 {
    display: flex;
    height: 200px;
    width: 250px;
    object-fit: cover;
  }

  .card {
    box-shadow: 4px 4px 1px 1px rgba(100, 100, 100, .5);
    border-radius: 2em;
    border: rgba(50, 50, 50, .2) solid 1px
  }
</style>
