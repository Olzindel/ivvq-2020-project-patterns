<template>
  <div>
    <div class="title">
      <h1>Paiement</h1>
    </div>
    <div class="block">
      <b-field grouped position="is-centered">
        <b-field label="Personal title" label-position='inside'>
          <b-select v-model="user.gender">
            <option value="F">Mme</option>
            <option value="M">Mr</option>
          </b-select>
        </b-field>
        <b-field label="prenom" label-position='inside' expanded>
          <b-input v-model="user.firstName"/>
        </b-field>
        <b-field label="Nom" label-position='inside' expanded>
          <b-input v-model="user.name"/>
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
        <b-input v-model="user.town"/>
      </b-field>
    </div>
    <b-field label="Numero de carte" label-position='inside' expanded>
      <b-input minlength="16" maxlength="16" pattern="[0-9]*" v-model="card.num"/>
    </b-field>
    <b-field grouped position="is-centered">
    <b-field position="is-left" label="date" label-position='inside' style="margin-left: 20%">
      <b-datepicker
        type="month"
        expanded
        placeholder="Select a date"
        v-model="card.date">
      </b-datepicker>
    </b-field>
    <b-field label="code au dos de la carte" label-position='inside' expanded style="margin-right: 20%">
      <b-input minlength="3" maxlength="3" pattern="[0-9]*" v-model="card.code"/>
    </b-field>
    </b-field>
    <b-button type="is-primary" class="buttonValidation" size="is-Large" @click="valideInfo">Je paie
    </b-button>
  </div>
</template>

<script>
export default {
  name: 'paiementByCard',
  props: {
    user: {type: Object, required: true}
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
    valideInfo () {
      let date = new Date()
      if (this.card.date.getTime() >= date.setMonth(date.getMonth() - 1)) {
        if (this.card.code.length === 3) {
          if (this.valid_credit_card(this.card.num)) {
            this.$buefy.toast.open({
              duration: 5000,
              message: 'code bon',
              position: 'is-bottom',
              type: 'is-success'
            })
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
