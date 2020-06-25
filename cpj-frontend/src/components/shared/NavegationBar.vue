<template>
    <div id="app" class="margin-bottom">
        <b-navbar toggleable="lg" class="nav-bar-color">
            <b-navbar-brand>
                <img id="menu-img" src="../../assets/logo_v2.png" class="nav-bar-img"/>
            </b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"/>

            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav class="nav-bar-text">
                    <b-nav-item v-if="currentUser" to="/home">
                        <b-icon icon="house"></b-icon>
                        Home
                    </b-nav-item>
                    <b-nav-item-dropdown v-if="currentUser" right>
                        <template v-slot:button-content>
                            <b-icon icon="person-plus"></b-icon>
                            Cadastro
                        </template>
                        <b-dropdown-item v-if="currentUser && hasRole('ROLE_ADMIN')" to="/userRegister">Usuário
                        </b-dropdown-item>
                        <b-dropdown-item to="/clientRegister">Cliente</b-dropdown-item>
                    </b-nav-item-dropdown>
                    <b-nav-item-dropdown v-if="currentUser" right>
                        <template v-slot:button-content>
                            <b-icon icon="card-list"></b-icon>
                            Listagem
                        </template>
                        <b-dropdown-item v-if="currentUser && hasRole('ROLE_ADMIN')" to="/userList">Usuário
                        </b-dropdown-item>
                        <b-dropdown-item to="/clientList">Cliente</b-dropdown-item>
                    </b-nav-item-dropdown>
                </b-navbar-nav>

                <!-- Right aligned nav items -->
                <b-navbar-nav v-if="currentUser" class="ml-auto nav-bar-text">
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

    .nav-bar-color {
        background-color: #234235;
    }

    .nav-bar-img {
        max-width: 85%;
        height: auto;
    }

    .nav-bar-text * {
        color: white !important;
    }

    ::v-deep .dropdown .nav-link {
        color: white !important;
    }
</style>