<template>
  <div class="div">
    <img class="img" src="../../assets/headerPart/title2.png" alt="website title">
  </div>
</template>

<script>
export default {
  name: 'Banner',
  mounted () {
    localStorage.setItem('user', 1)
  }
}
</script>

<style scoped>

  .div {
    margin: 0 0;
    display: inline-block;
    height: 100%;
    max-width: 100%;
  }

  .img {
    display: block;
    height: auto;
    max-width: 100%;
  }
</style>
