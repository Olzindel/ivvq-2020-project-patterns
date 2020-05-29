<template>
  <div>
    <div class="title">
      <h1>Paiement</h1>
    </div>
    <div class="block">
      <b-field grouped position="is-centered">
        <b-field label-position='inside'>
          <b-select v-model="user.gender">
            <option value="F">Mme</option>
            <option value="M">Mr</option>
          </b-select>
        </b-field>
        <b-field label="prenom" label-position='inside' expanded>
          <b-input v-model="user.firstName"/>
        </b-field>
        <b-field label="Nom" label-position='inside' expanded>
          <b-input v-model="user.lastName"/>
        </b-field>
      </b-field>
      <b-field grouped position="is-centered">
        <b-field label="Rue" label-position='inside' expanded>
          <b-input v-model="user.street"/>
        </b-field>
        <b-field label="Code Postal" label-position='inside' expanded>
          <b-input minlength="5" maxlength="5" pattern="[0-9]*" v-model="user.postalCode"/>
        </b-field>
      </b-field>
      <b-field position="is-left" label="Ville" label-position='inside' style="margin-right: 50%">
        <b-input v-model="user.city"/>
      </b-field>
    </div>
    <b-field label="Numéro de carte" label-position='inside' expanded>
      <b-input minlength="16" maxlength="16" pattern="[0-9]*" v-model="card.num"/>
    </b-field>
    <b-field grouped position="is-centered">
    <b-field position="is-left" label="date" label-position='inside' style="margin-left: 20%">
      <b-datepicker
        type="month"
        expanded
        placeholder="Selectionner une date"
        v-model="card.date">
      </b-datepicker>
    </b-field>
    <b-field label="Code au dos de la carte" label-position='inside' expanded style="margin-right: 20%">
      <b-input minlength="3" maxlength="3" pattern="[0-9]*" v-model="card.code"/>
    </b-field>
    </b-field>
    <b-button type="is-primary" class="buttonValidation" size="is-Large" @click="updateUser()">Je valide mes informations et je paie
    </b-button>
  </div>
</template>

<script>
import gql from 'graphql-tag'
import router from '../../router/index'

export default {
  name: 'paiementByCard',
  props: {
    user: {type: Object, required: true},
    order: {type: Object, required: true}
  },
  data () {
    return {
      card: {
        num: '',
        date: new Date(),
        code: ''
      }
    }
  },
  methods: {
    updateUser () {
      if (this.user.postalCode.length !== 5) {
        this.$buefy.toast.open({
          duration: 5000,
          message: 'votre code postal n\'est pas correct',
          position: 'is-bottom',
          type: 'is-danger'
        })
        return
      }
      if (this.user.firstName === '' || this.user.lastName === '' || this.user.street === '' || this.user.city === '') {
        this.$buefy.toast.open({
          duration: 5000,
          message: 'un champ est vide',
          position: 'is-bottom',
          type: 'is-danger'
        })
        return
      }
      this.$apollo.mutate({
        mutation: gql`mutation updateUser ($userId: ID!, $input: UserInput!){
        updateUserAdress : updateUser(userId: $userId,input: $input){
        id
         }}`,
        variables: {
          userId: this.user.id,
          input: {
            gender: this.user.gender,
            firstName: this.user.firstName,
            lastName: this.user.lastName,
            street: this.user.street,
            postalCode: this.user.postalCode,
            city: this.user.city,
            email: this.user.email
          }
        }
      }).then(data => {
        this.valideInfos()
      }).catch((error) => this.$buefy.toast.open({
        duration: 5000,
        message: error.message,
        position: 'is-bottom',
        type: 'is-danger'
      }))
    },
    valideInfos () {
      let date = new Date()
      if (this.card.date.getTime() >= date.setMonth(date.getMonth() - 1)) {
        if (this.card.code.length === 3) {
          if (this.valid_credit_card(this.card.num)) {
            this.$buefy.toast.open({
              duration: 5000,
              message: 'Commande réussite',
              position: 'is-bottom',
              type: 'is-success'
            })
            console.log('panier validé')
            this.paidBasket()
          } else {
            this.$buefy.toast.open({
              duration: 5000,
              message: 'code mauvais',
              position: 'is-bottom',
              type: 'is-danger'
            })
          }
        } else {
          this.$buefy.toast.open({
            duration: 5000,
            message: 'code invalide',
            position: 'is-bottom',
            type: 'is-danger'
          })
        }
      } else {
        this.$buefy.toast.open({
          duration: 5000,
          message: 'date invalide',
          position: 'is-bottom',
          type: 'is-danger'
        })
      }
    },
    paidBasket () {
      this.$apollo.mutate({
        mutation: gql`mutation updateOrder ($orderId: ID!, $input: OrderInput!){
        updateOrderToPaid: updateOrder( orderId: $orderId, input: $input){
          id
        }
        }`,
        variables: {
          orderId: parseInt(this.order.id),
          input: {
            status: 'PAID'
          }
        },
        fetchPolicy: 'no-cache'
      }).then(data => {
        console.log('commande en preparation')
        router.push({path: '/home'})
      }).catch((error) => {
        console.log(error)
        this.$buefy.toast.open({
          duration: 5000,
          message: 'une erreur est apparu, paiement invalidé retour au panier' + '<br/>' + 'veuillez valider votre commande a nouveau',
          position: 'is-bottom',
          type: 'is-danger'
        })
        router.push({path: '/basket'})
      })
    },
    valid_credit_card (value) {
      // source :https://gist.github.com/DiegoSalazar/4075533
      // Accept only digits, dashes or spaces
      if (/[^0-9-\s]+/.test(value)) return false

      // The Luhn Algorithm. It's so pretty.
      let nCheck = 0
      let bEven = false
      value = value.replace(/\D/g, '')

      for (var n = value.length - 1; n >= 0; n--) {
        var cDigit = value.charAt(n)
        var nDigit = parseInt(cDigit, 10)

        if (bEven && (nDigit *= 2) > 9) nDigit -= 9

        nCheck += nDigit
        bEven = !bEven
      }

      return (nCheck % 10) === 0
    }
  }
}
</script>

<style scoped>

</style>
