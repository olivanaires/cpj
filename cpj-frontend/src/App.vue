<template>
  <div id="app">
    <navegation-bar></navegation-bar>

    <div class="container">
      <router-view :key="$route.path" />
    </div>
  </div>
</template>

<script>
  import NavegationBar from "./components/shared/NavegationBar";

  export default {
    components: {
      'navegation-bar': NavegationBar
    },
    computed: {
      currentUser() {
        return this.$store.state.auth.user;
      }
    },
    methods: {
      logOut() {
        this.$store.dispatch('auth/logout');
        this.$router.push('/login');
      },
      hasRole(value) {
        if (this.currentUser && this.currentUser.roles) {
          return this.currentUser.roles.some(role => role.name === value);
        }
        return false;
      }
    }
  };
</script>

<style>
  .option-item svg {
    width: 1.5em;
  }

  .center {
    text-align: center;
  }
</style>