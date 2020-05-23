<template>
  <div class="main-div columns is-centered">
    <div class="column is-one-third">
      <section>
        <h1 class="title">Connexion</h1>
        <b-field label="Nom d'utilisateur" label-position="inside">
          <b-input v-model="username" placeholder="Mon nom d'utilisateur"></b-input>
        </b-field>
        <b-field label="Mot de passe" label-position="inside">
          <b-input v-model="password" type="password" placeholder="Mon mot de passe"></b-input>
        </b-field>
        <b-button type="button is-primary submit" @click="login()">Connexion</b-button>
      </section>
      <section>
        <b-button type="button is-text" @click="clickMe()">Pas encore inscrit ?</b-button>
      </section>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoginPage',
  methods: {
    login () {
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
