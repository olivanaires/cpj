<template>
    <div class="col-md-12">
        <div class="card card-container">
            <img
                    id="profile-img"
                    src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                    class="profile-img-card"
            />
            <form name="form" @submit.prevent="handleLogin">
                <div class="form-group" v-if="!isRefreshView">
                    <label for="username">Usuário *</label>
                    <input id="username"
                           v-model="user.username"
                           type="text"
                           required
                           class="form-control"
                           name="username"
                    />
                </div>
                <div class="form-group" v-if="isRefreshView">
                    <p>Informe seu e-mail de cadastro para que uma nova senha seja enviada!</p>
                    <label for="email">E-Mail *</label>
                    <input id="email"
                           v-model="user.email"
                           type="text"
                           required
                           class="form-control"
                           name="username"
                    />
                </div>
                <div class="form-group" v-if="!isRefreshView">
                    <label for="password">Password *</label>
                    <input id="password"
                           v-model="user.password"
                           type="password"
                           required
                           class="form-control"
                           name="password"
                    />
                </div>
                <div class="form-group">
                    <button v-if="!isRefreshView" class="btn btn-primary btn-block" :disabled="loading">
                        <span v-show="loading" class="spinner-border spinner-border-sm"></span>
                        <span>Login</span>
                    </button>
                    <div class="forgot-password">
                        <span v-if="!isRefreshView" @click="changeView">Esqueceu o Usuário ou Senha?</span>
                    </div>
                    <button v-if="isRefreshView" type="button" class="btn btn-primary btn-block" @click="resetPassword">
                        <span>Resetar Senha</span>
                    </button>

                    <button v-if="isRefreshView" type="button" class="btn btn-primary btn-block" @click.prevent="isRefreshView = false">
                        <span>Voltar</span>
                    </button>
                </div>
                <div class="form-group">
                    <div v-if="message" class="alert alert-danger" role="alert">{{message}}</div>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
    import User from '../models/user';
    import UserService from '../services/user.service'

    export default {
        name: 'Login',
        data() {
            return {
                user: new User('', ''),
                loading: false,
                message: '',
                isRefreshView: false
            };
        },
        computed: {
            loggedIn() {
                return this.$store.state.auth.status.loggedIn;
            }
        },
        created() {
            if (this.loggedIn) {
                this.$router.push('/profile');
            }
        },
        methods: {
            handleLogin() {
                this.loading = true;
                if (this.user.username && this.user.password) {
                    this.$store.dispatch('auth/login', this.user).then(
                        response => {
                            this.$cookies.set('token', response);
                            this.$router.push('/home');
                        },
                        error => {
                            this.loading = false;
                            this.message = error.response.data.message;
                        }
                    );
                }
            },
            changeView() {
                this.isRefreshView = true;
            },
            resetPassword() {
                if (this.user.email) {
                    UserService.resetPassword(this.user.email).then(
                        response => {
                            this.$swal({icon: 'success', title: response.data.message});
                        },
                        error => {
                            this.$swal({icon: 'error', title: error.response.data.message});
                        }
                    );
                } else {
                    this.$swal({icon: 'error', title: "E-Mail deve ser informado."});
                }
                this.isRefreshView = false;
            }
        }
    };
</script>

<style scoped>
    label {
        display: block;
        margin-top: 10px;
    }

    .card-container.card {
        max-width: 350px !important;
        padding: 40px 40px;
    }

    div.card {
        background-color: #f7f7f7;
        padding: 20px 25px 30px !important;
        margin: 50px auto 25px;
        -moz-border-radius: 2px;
        -webkit-border-radius: 2px;
        border-radius: 2px;
        -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    }

    .profile-img-card {
        width: 96px;
        height: 96px;
        margin: 0 auto 10px;
        display: block;
        -moz-border-radius: 50%;
        -webkit-border-radius: 50%;
        border-radius: 50%;
    }

    .forgot-password {
        color: blue;
        text-align: center;
        font-size: 13px;
        margin-top: 10px;
        height: 20px;
    }

    .forgot-password:hover {
        cursor: pointer;
        font-size: 14px;
    }
</style>