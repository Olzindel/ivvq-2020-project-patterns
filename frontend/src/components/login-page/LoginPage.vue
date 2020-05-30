<template>
  <div class="main-div columns is-centered">
    <div class="column is-one-third">
      <h1 class="title">Connexion</h1>
      <div v-if="!isConnected">
        <section>
          <form>
            <b-field label="Nom d'utilisateur" label-position="inside">
              <b-input v-model="username" placeholder="Mon nom d'utilisateur" required></b-input>
            </b-field>
            <b-field label="Mot de passe" label-position="inside">
              <b-input v-model="password" type="password" placeholder="Mon mot de passe" required></b-input>
            </b-field>
            <b-button type="button is-primary submit" @click="login()">Connexion</b-button>
          </form>
        </section>
        <section>
          <b-button type="button is-text" @click="goToSignUp()">Pas encore inscrit ?</b-button>
        </section>
      </div>
      <div v-else>
        Vous êtes déjà connecté...
      </div>
    </div>
  </div>
</template>

<script>
import store from '../../store'
import axios from 'axios'
import gql from 'graphql-tag'

export default {
  name: 'LoginPage',
  data () {
    return {
      username: '',
      password: '',
      isConnected: localStorage.getItem('connection-token')
    }
  },
  methods: {
    login () {
      // var that = this
      axios.post(process.env.VUE_APP_LOGIN_HTTP, {
        username: this.username,
        password: this.password
      }).then(value => {
        const connectionToken = value.headers['authorization']
        this.$apollo.query({
          query: gql`query UserFromToken($token: String!){
              user: userFromToken(token: $token) {
                id,
                role
                }
              }`,
          variables: {
            token: connectionToken
          }
        }).then(result => {
          localStorage.setItem('connection-token', connectionToken)
          store.commit('connect', result.data.user)
          this.$router.push('/home')
        }, reason => this.$buefy.toast.open({
          duration: 5000,
          message: 'Unexpected error',
          position: 'is-bottom',
          type: 'is-danger'
        }))
      },
      error => {
        if (error.response.status === 401) {
          this.$buefy.toast.open({
            duration: 5000,
            message: 'Nom d\'utilisateur ou mot de passe incorrect',
            position: 'is-bottom',
            type: 'is-danger'
          })
        } else {
          this.$buefy.toast.open({
            duration: 5000,
            message: 'Unexpected error',
            position: 'is-bottom',
            type: 'is-danger'
          })
        }
      })
    },
    goToSignUp () {
      this.$router.push('/signup')
    }
  }
}
</script>

<style scoped>
  .main-div {
    flex: content;
    align-items: center;
  }
</style>
