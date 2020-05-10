<template>
  <div>
    <div class="title">
      <h1>Mes informations</h1>
    </div>
    <div class="block" >
      <b-field grouped position="is-centered">
        <b-field label="Nom et prenom" label-position='inside' expanded>
          <b-input v-model="user.fullName"/>
        </b-field>
      </b-field>
        <b-field position="is-left" label="date d'anniversaire" label-position='inside' style="margin-right: 50%">
        <b-datepicker
          ref="datepicker"
          expanded
          placeholder="Select a date"
        v-model="user.dateOfBirth">
        </b-datepicker>
        </b-field>
      <b-field grouped  position="is-centered">
        <b-field label="Rue" label-position='inside' expanded>
          <b-input v-model="user.street"/>
        </b-field>
        <b-field label="Code Postal" label-position='inside' expanded>
          <b-input v-model="user.postalCode"/>
        </b-field>
      </b-field>
        <b-field position="is-left" label="Ville" label-position='inside' style="margin-right: 50%">
          <b-input v-model="user.town"/>
        </b-field>
      <b-field label="email" label-position='inside' expanded>
        <b-input type="email" v-model="user.email"></b-input>
      </b-field>
      <b-field label="password" label-position='inside' expanded>
        <b-input type="password"/>
      </b-field>
      <b-field label="password" label-position='inside' expanded>
        <b-input type="password" />
      </b-field>
    </div>
    <b-button type="is-primary" class="buttonValidation" size="is-Large" @click="updateInfo">Je change mes informations</b-button>
  </div>
</template>

<script>
export default {
  name: 'UserAccount',
  data () {
    return {
      user: {
        fullName: 'Jean Martin',
        email: 'waifu@market-desu.com',
        dateOfBirth: new Date(),
        street: '118 route de Narbonne',
        postalCode: '31500',
        town: 'Toulouse'
      }
    }
  },
  methods: {
    updateInfo () {
      console.log(this.user)
    }
  }
}
</script>

<style scoped>
.title {
  display: flex;
  justify-content: center;
  size: 25px;
}
.block{
  padding: 24px;
  margin: 0
}
  .buttonValidation{
    margin-bottom: 30px;
  }
</style>
