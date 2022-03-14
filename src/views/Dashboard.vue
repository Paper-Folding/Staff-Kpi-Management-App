<template>
  <div>hello, I'm bogo</div>
  <button @click="out()">登出</button>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  methods: {
    ...mapActions({ logOff: "Login/logOff" }),
    out() {
      this.logOff();
      this.$router.push("/");
    }
  }
}
</script>

<style>
</style>