<template>
  <section>
    <button class="button is-primary is-medium"
            @click="isComponentModalActive = true">
      Connexion
    </button>

    <b-modal :active.sync="isComponentModalActive"
             has-modal-card
             trap-focus
             :destroy-on-hide="false"
             aria-role="dialog"
             aria-modal>
      <modal-form v-bind="formProps"></modal-form>
    </b-modal>
  </section>
</template>

<script>
const ModalForm = {
  props: ['email', 'password'],
  template: `
      <form action="">
        <div class="modal-card" style="width: auto">
          <header class="modal-card-head">
            <p class="modal-card-title">Login</p>
          </header>
          <section class="modal-card-body">
            <b-field label="Email">
              <b-input
                type="email"
                :value="email"
                placeholder="Your email"
                required>
              </b-input>
            </b-field>

            <b-field label="Password">
              <b-input
                type="password"
                :value="password"
                password-reveal
                placeholder="Your password"
                required>
              </b-input>
            </b-field>
          </section>
          <footer class="modal-card-foot">
            <button class="button" type="button" @click="$parent.close()">Close</button>
            <button class="button is-primary">Login</button>
          </footer>
        </div>
      </form>
    `
}

export default {
  name: 'login',
  components: {
    ModalForm
  },
  data () {
    return {
      isComponentModalActive: false,
      formProps: {
        email: 'waifu@market-desu.com',
        password: 'testing'
      }
    }
  }
}
</script>

<style scoped>

</style>
