<template>
    <div id="app" class="margin-bottom">
        <b-navbar toggleable="lg">
            <b-navbar-brand>NavBar</b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"/>

            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav>
                    <b-nav-item v-if="currentUser" to="/home">
                        <b-icon icon="house"></b-icon>
                        Home
                    </b-nav-item>
                    <b-nav-item-dropdown v-if="currentUser" right>
                        <template v-slot:button-content>
                            <b-icon icon="person-plus"></b-icon>
                            Cadastro
                        </template>
                        <b-dropdown-item v-if="currentUser && hasRole('ROLE_ADMIN')" to="/userRegister">Usuário</b-dropdown-item>
                        <b-dropdown-item to="/clientRegister">Cliente</b-dropdown-item>
                    </b-nav-item-dropdown>
                </b-navbar-nav>

                <!-- Right aligned nav items -->
                <b-navbar-nav v-if="currentUser" class="ml-auto">
                    <b-nav-item-dropdown right>
                        <!-- Using 'button-content' slot -->
                        <template v-slot:button-content>
                            <b-icon icon="gear"></b-icon>
                            {{ currentUser.name ? currentUser.name : currentUser.username }}
                        </template>
                        <b-dropdown-item to="/profile">
                            <b-icon icon="person-fill"></b-icon>
                            Profile
                        </b-dropdown-item>
                        <b-dropdown-item @click.prevent="logOut">
                            <b-icon icon="box-arrow-right"></b-icon>
                            Sign Out
                        </b-dropdown-item>
                    </b-nav-item-dropdown>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
    </div>
</template>

<script>
    export default {
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

<style scoped>
    .margin-bottom {
        margin-bottom: 25px;
    }
</style>