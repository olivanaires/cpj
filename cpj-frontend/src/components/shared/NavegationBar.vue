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
                        {{home}}
                    </b-nav-item>

                    <b-nav-item-dropdown v-if="currentUser" left>
                        <template v-slot:button-content>
                            <b-icon icon="person"></b-icon>
                            {{user}}
                        </template>
                        <b-dropdown-item v-if="currentUser && hasRole('ROLE_ADMIN')" to="/userRegister">{{create}}</b-dropdown-item>
                        <b-dropdown-item v-if="currentUser && hasRole('ROLE_ADMIN')" to="/userList">{{list}}</b-dropdown-item>
                    </b-nav-item-dropdown>

                    <b-nav-item-dropdown v-if="currentUser" left>
                        <template v-slot:button-content>
                            <b-icon icon="person-circle"></b-icon>
                            {{lawyer}}
                        </template>
                        <b-dropdown-item v-if="currentUser && hasRole('ROLE_ADMIN')" to="/lawyerRegister">{{create}}</b-dropdown-item>
                        <b-dropdown-item v-if="currentUser" to="/lawyerList">{{list}}</b-dropdown-item>
                    </b-nav-item-dropdown>

                    <b-nav-item-dropdown v-if="currentUser" left>
                        <template v-slot:button-content>
                            <b-icon icon="people"></b-icon>
                            {{client}}
                        </template>
                        <b-dropdown-item to="/clientRegister">{{create}}</b-dropdown-item>
                        <b-dropdown-item to="/clientList">{{list}}</b-dropdown-item>
                    </b-nav-item-dropdown>

                    <b-nav-item-dropdown v-if="currentUser" left>
                        <template v-slot:button-content>
                            <b-icon icon="file-earmark-text"></b-icon>
                            {{contract}}
                        </template>
                        <b-dropdown-item to="/contractRegister">{{create}}</b-dropdown-item>
                        <b-dropdown-item to="/contractList">{{list}}</b-dropdown-item>
                        <b-dropdown-item to="/additiveRegister">{{additive}}</b-dropdown-item>
                    </b-nav-item-dropdown>

                    <b-nav-item-dropdown v-if="currentUser" left>
                        <template v-slot:button-content>
                            <b-icon icon="file-plus"></b-icon>
                            {{honorary}}
                        </template>
                        <b-dropdown-item to="/honoraryRegister">{{create}}</b-dropdown-item>
                        <b-dropdown-item to="/honoraryList">{{list}}</b-dropdown-item>
                    </b-nav-item-dropdown>

                    <b-nav-item-dropdown v-if="currentUser" left>
                        <template v-slot:button-content>
                            <b-icon icon="file-minus"></b-icon>
                            {{expense}}
                        </template>
                        <b-dropdown-item to="/expenseRegister">{{create}}</b-dropdown-item>
                        <b-dropdown-item to="/expenseList">{{list}}</b-dropdown-item>
                    </b-nav-item-dropdown>
                </b-navbar-nav>

                <!-- Right aligned nav items -->
                <b-navbar-nav v-if="currentUser" class="ml-auto nav-bar-text">
                    <b-nav-item-dropdown right>
                        <template v-slot:button-content>
                            <b-icon icon="gear"></b-icon>
                            {{ currentUser.name ? currentUser.name : currentUser.username }}
                        </template>
                        <b-dropdown-item to="/profile">
                            {{profile}}
                        </b-dropdown-item>
                        <b-dropdown-item @click.prevent="logOut">
                            {{exit}}
                        </b-dropdown-item>
                    </b-nav-item-dropdown>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                home: 'Início',
                user: 'Usuário',
                lawyer: 'Advogado',
                client: 'Cliente',
                contract: 'Contrato',
                honorary: 'Honorário',
                expense: 'Despesa',
                additive: 'Aditivo',
                create: 'Cadastro',
                list: 'Listagem',
                profile: 'Perfil',
                exit: 'Sair',
            }
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