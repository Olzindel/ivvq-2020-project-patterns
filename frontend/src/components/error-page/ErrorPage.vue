<template>
  <div class="backdiv">
    <img class="img" src="https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/f9a688ae-8900-41df-86e5-7c9e5668d724/dcwrv0p-ad32476e-ddbf-4162-b65a-bbcd0f8337ab.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOiIsImlzcyI6InVybjphcHA6Iiwib2JqIjpbW3sicGF0aCI6IlwvZlwvZjlhNjg4YWUtODkwMC00MWRmLTg2ZTUtN2M5ZTU2NjhkNzI0XC9kY3dydjBwLWFkMzI0NzZlLWRkYmYtNDE2Mi1iNjVhLWJiY2QwZjgzMzdhYi5wbmcifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6ZmlsZS5kb3dubG9hZCJdfQ.BAPuk7r8MAGtiIgseJPcAQ0RefiH9Siwb0D9-9x6dqg"/>
    <div class="texte">
      ERROR 404
      vous semblez perdu
    </div>
    <b-button @click="goToHome()">Retour a la page d'accueil</b-button>
  </div>
</template>

<script>
import router from '../../router/index'
export default {
  name: 'ErrorPage',
  methods: {
    goToHome () {
      router.push({path: '/'})
      this.showNavigation = false
    }
  }
}
</script>

<style scoped>
  @keyframes anime {
    0% {
      transform: translateX(-200px)
      rotateY(180deg);
    }
    49%{
      transform: translateX(200px)
      rotateY(180deg);
    }
    50%{
      transform: translateX(200px)
    rotateY(0deg);
    }
    99%{
      transform: translateX(-200px)
      rotateY(0deg);
    }
    100%{
      transform: translateX(-200px)
      rotateY(180deg);
    }
  }
  .img{
    height: 250px;
    width: auto;
    animation: anime 5s infinite linear;
  }
  .texte{
    font-size: 50px;
  }
</style>
