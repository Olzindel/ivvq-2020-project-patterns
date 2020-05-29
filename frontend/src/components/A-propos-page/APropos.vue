<template>
<div>
  <h1 class="title">
    Bienvenue sur notre site
  </h1>
  <div class="board">
    <img class="img" src="./../../assets/imageDefault.png" />
    <div>
      <h1 class="title">
        le site
      </h1>
    <p class="text">
       {{presentation}}
    </p>
    </div>
    <div>
    <h1 class="title">
      L'équipe
    </h1>
    <p class="text">
      {{equipe}}
    </p>
    </div>
    <img class="img" src="https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png"/>
    <img class="img" src="https://external-preview.redd.it/_0Ox2VUId9_i-TG17gLuxtWJPvtUC_3jD0phktl_7K0.jpg?width=640&crop=smart&auto=webp&s=8372d6b151170cdd51e734322460a2e15dfead8f"/>
    <div>
    <h1 class="title">
      Remerciement
    </h1>
    <p class="text">
      {{remerciement}}
    </p>
    </div>
  </div>
</div>
</template>

<script>
export default {
  name: 'APropos',
  data () {
    return {
      presentation: ' Bonjour,' +
        'Sur Waifu Market-desu vous pouvez acheter de magnifique waifu à petit prix.' +
        ' Après votre achat vous recevrez directement chez vous un superbe livret contenant' +
        ' de nombreux dessin de votre waifu ainsi que des posters. Nous mettons tout en œuvre' +
        ' pour que ce livret soit le plus beau possible. C\'est pourquoi nous choisissons que' +
        ' les meilleures images et que la qualité des photos est la plus haute possible.' +
        ' Nous espérons que vous trouverez votre bonheur sur ' +
        'notre site.',
      equipe: 'Ce site a été entièrement réalisé par une équipe de 4 développeurs dans ' +
        'le cadre de leur formation du master développement logiciel. Le but est de réaliser un site marchant' +
        ' en utilisant toute une chaîne DevOps. Les 4 développeurs sont : ' +
        '- Nathan ROCHE ' +
        '- Alexandre ELLERO' +
        '- Timothée THIBAULT' +
        '- Pierre SELEBRAN',
      remerciement: 'Nous tenons a remercier l’équipe Captain-Hadoc ainsi que les professeurs pour leur aide durant ce projet'
    }
  }
}
</script>

<style scoped>
.board{
  display: grid;
  grid-template-columns: 50% 50%;
  margin: 12px 10%;
  justify-content: center;
  align-items: center;
}
  .img{
    object-fit: contain;
  }
  .text {
    font-size: 20px;
  }
  .title{
    font-size: 30px;
    font-weight: bold;
  }
</style>
