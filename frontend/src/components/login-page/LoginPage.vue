<template>
  <div class="main-div columns is-centered">
    <div class="column is-one-third">
      <h1 class="title">Connexion</h1>
      <div v-if="!isConnected">
        <section>
          <b-field label="Nom d'utilisateur" label-position="inside">
            <b-input v-model="username" placeholder="Mon nom d'utilisateur"></b-input>
          </b-field>
          <b-field label="Mot de passe" label-position="inside">
            <b-input v-model="password" type="password" placeholder="Mon mot de passe"></b-input>
          </b-field>
          <b-button type="button is-primary submit" @click="login()">Connexion</b-button>
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
import axios from 'axios'

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
      axios.post(process.env.VUE_APP_LOGIN_HTTP, {
        username: this.username,
        password: this.password
      }).then(function (value) {
        localStorage.setItem('connection-token', value.headers['authorization'])
        this.$router.push('/home')
      }, error => {
        if (error.response.status === 401) {
          this.$buefy.toast.open({
            duration: 5000,
            message: 'Nom d\'utilisateur ou mot de passe incorrect',
            position: 'is-bottom',
            type: 'is-danger'
          })
        } else {
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
