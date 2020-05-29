<template>
  <div class="main-div columns is-centered">
    <link rel="stylesheet" href="https://cdn.materialdesignicons.com/2.5.94/css/materialdesignicons.min.css">
    <div class="column is-three-fifths">
      <h1 class="title">Inscription</h1>
      <div v-if="!isConnected">
        <section>
          <form id="signupForm">
            <b-steps size="is-medium">
              <b-step-item step="1" label="Compte" icon="account-key">
                <b-field label="Nom d'utilisateur" label-position="inside">
                  <b-input v-model="user.account.username" placeholder="Mon nom d'utilisateur" required></b-input>
                </b-field>
                <b-field label="Mot de passe" label-position="inside">
                  <b-input v-model="user.account.password" type="password" placeholder="Mon mot de passe"
                           required></b-input>
                </b-field>
                <b-field label="Confirmer le mot de passe" label-position="inside">
                  <b-input type="password" v-model="user.account.passwordConfirm" placeholder="Mon mot de passe"
                           required></b-input>
                </b-field>
              </b-step-item>
              <b-step-item step="2" label="Informations complémentaires" icon="account-plus" disabled>
                <div class="block">
                  <b-field label="Prénom" label-position="inside">
                    <b-input v-model="user.infos.firstname" placeholder="Mon prénom"></b-input>
                  </b-field>
                  <b-field label="Nom" label-position="inside">
                    <b-input v-model="user.infos.lastname" placeholder="Mon mom"></b-input>
                  </b-field>
                  <b-field label="Email" label-position="inside">
                    <b-input v-model="user.infos.email" type="email" placeholder="Mon email"></b-input>
                  </b-field>
                  <div class="block">
                    <b-radio name="genre" v-model="user.infos.gender" native-value="H">
                      Homme
                    </b-radio>
                    <b-radio name="genre" v-model="user.infos.gender" native-value="F">
                      Femme
                    </b-radio>
                  </div>
                  <b-field label="Ville" label-position="inside">
                    <b-input v-model="user.infos.city" placeholder="Ma ville"></b-input>
                  </b-field>
                  <b-field label="Adresse" label-position="inside">
                    <b-input v-model="user.infos.addresse" placeholder="Mon adresse"></b-input>
                  </b-field>
                  <b-field label="Code postal" label-position="inside">
                    <b-input v-model="user.infos.zipCode" type="number" placeholder="Mon code postal" min="1000"
                             max="99999"></b-input>
                  </b-field>
                </div>
              </b-step-item>
            </b-steps>
            <b-button type="is-primary" @click="submit()" :disabled="!isValid()">Valider</b-button>
          </form>
        </section>
      </div>
      <div v-else>
        Vous êtes déjà connecté, vous n'avez pas besoin de vous inscrire...
      </div>
    </div>
  </div>
</template>

<script>
import gql from 'graphql-tag'

export default {
  name: 'SignupPage',

  data () {
    return {
      isConnected: localStorage.getItem('connection-token'),
      user: {
        account: {
          username: '',
          password: '',
          passwordConfirm: ''
        },
        infos: {
          firstname: '',
          lastname: '',
          email: '',
          gender: 'H',
          addresse: '',
          city: '',
          zipCode: null
        }
      }
    }
  },

  methods: {
    isValid () {
      return this.user.account.username && this.user.account.password && this.user.account.password === this.user.account.passwordConfirm
    },
    submit () {
      this.$apollo.mutate({
        // Query
        mutation: gql`mutation ($username: String!, $password: String!, $firstName: String, $lastName: String, $email: String, $gender: String, $street: String, $postalCode: String, $city: String) {
          createUser(
            input:{
              username: $username,
              password: $password,
              firstName: $firstName,
              lastName: $lastName,
              email: $email,
              gender: $gender,
              street: $street,
              city: $city,
              postalCode: $postalCode
              role: USER
            }) {
              id
             }
          }`,
        // Parameters
        variables: {
          username: this.user.account.username,
          password: this.user.account.password,
          firstName: this.user.infos.firstname,
          lastName: this.user.infos.lastname,
          email: this.user.infos.email,
          gender: this.user.infos.gender,
          street: this.user.infos.addresse,
          city: this.user.infos.city,
          postalCode: this.user.infos.zipCode
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
