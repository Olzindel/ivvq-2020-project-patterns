<template>
  <div>
    <div class="title">
      <h1>Mes informations</h1>
    </div>
    <div class="block">
      <b-field grouped position="is-centered">
        <b-field label="Titre" label-position='inside'>
          <b-select v-model="user.gender">
            <option value="F">Mme</option>
            <option value="M">Mr</option>
          </b-select>
        </b-field>
        <b-field label="Prénom" label-position='inside' expanded>
          <b-input v-model="user.firstName"/>
        </b-field>
        <b-field label="Nom" label-position='inside' expanded>
          <b-input v-model="user.lastName"/>
        </b-field>
      </b-field>
      <b-field grouped position="is-centered">
        <b-field label="Adresse" label-position='inside' expanded>
          <b-input v-model="user.street"/>
        </b-field>
        <b-field label="Code Postal" label-position='inside' expanded>
          <b-input minlength="5" maxlength="5" pattern="[0-9]*" v-model="user.postalCode"/>
        </b-field>
      </b-field>
      <b-field position="is-left" label="Ville" label-position='inside' style="margin-right: 50%">
        <b-input v-model="user.city"/>
      </b-field>
      <b-field label="Email" label-position='inside' expanded>
        <b-input placeholder="Email" type="email" v-model="user.email"></b-input>
      </b-field>
      <b-field label="Changer le mot de passe" label-position='inside' expanded>
        <b-input type="password" v-model="password1"/>
      </b-field>
      <b-field label="Confirmer le mot de passe" label-position='inside' expanded>
        <b-input type="password" v-model="password2"/>
      </b-field>
    </div>
    <b-button type="is-primary" class="buttonValidation" size="is-Large" @click="updateInfo">Je change mes
      informations
    </b-button>
  </div>
</template>

<script>
import gql from 'graphql-tag'
export default {
  name: 'UserAccount',
  data () {
    return {
      user: {
        firstName: '',
        lastName: '',
        gender: 'M',
        email: '',
        street: '',
        postalCode: '',
        city: ''
      },
      password1: '',
      password2: ''
    }
  },
  methods: {
    getUserInfos () {
      this.$apollo.query({
        query: gql`query user($id: ID!){
        getuser:user(userId: $id){
            id,
            username,
            firstName,
            lastName,
            email,
            street,
            postalCode,
            city,
            email,
            gender,
          }
        }
        `,
        variables: {
          id: this.$store.getters.user.id
        },
        fetchPolicy: 'no-cache'}).then(data => {
        this.user = data.data.getuser
      })
    },
    updateInfo () {
      let newPassword = this.password1

      if (!this.password1) {
        newPassword = null
      }
      if (this.password1 === this.password2) {
        if (this.user.firstName && this.user.lastName && this.user.postalCode.length === 5 &&
            this.user.street && this.user.email && this.user.city) {
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
                email: this.user.email,
                password: newPassword
              }
            }
          }).then(data => {
            this.$buefy.toast.open({
              duration: 3000,
              message: 'Informations mises à jour',
              position: 'is-bottom',
              type: 'is-success'
            })
          }).catch((error) => {
            this.$buefy.toast.open({
              duration: 3000,
              message: error.message,
              position: 'is-bottom',
              type: 'is-danger'
            })
          })
        } else {
          this.$buefy.toast.open({
            duration: 3000,
            message: 'Un champ est vide',
            position: 'is-bottom',
            type: 'is-danger'
          })
        }
      } else {
        this.$buefy.toast.open({
          duration: 3000,
          message: 'Les mots de passe doivent être identiques',
          position: 'is-bottom',
          type: 'is-danger'
        })
      }
    }
  },
  mounted () {
    this.getUserInfos()
  }
}
</script>

<style scoped>
  .title {
    display: flex;
    justify-content: center;
    size: 25px;
  }

  .block {
    padding: 24px;
    margin: 0
  }

  .buttonValidation {
    margin-bottom: 30px;
  }
</style>
