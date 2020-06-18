<template>
    <div class="col-md-12">
        Cadastro Usuário
        <div class="card card-container">
            <img
                    id="profile-img"
                    src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                    class="profile-img-card"
            />
            <form name="form" @submit.prevent="handleRegister">
                <div v-if="!successful">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input id="username"
                               v-model="user.username"
                               type="text"
                               class="form-control"
                               name="username"
                        />
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input id="email"
                               v-model="user.email"
                               type="email"
                               class="form-control"
                               name="email"
                        />
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input id="password"
                               v-model="user.password"
                               type="password"
                               class="form-control"
                               name="password"
                        />
                    </div>
                    <div class="form-group">
                        <button class="btn btn-primary btn-block">Salvar</button>
                    </div>
                </div>
            </form>

            <div
                    v-if="message"
                    class="alert"
                    :class="successful ? 'alert-success' : 'alert-danger'"
            >{{message}}
            </div>
        </div>
    </div>
</template>

<script>
    import UserService from '../../services/client.service'
    import User from '../../models/user';

    export default {
        name: 'Register',
        data() {
            return {
                user: new User('', '', '', ''),
                submitted: false,
                successful: false,
                message: ''
            };
        },
        computed: {
            loggedIn() {
                return this.$store.state.auth.status.loggedIn;
            }
        },
        mounted() {
            // if (this.loggedIn) {
            //     this.$router.push('/profile');
            // }
        },
        methods: {
            handleRegister() {
                this.message = '';
                this.submitted = true;
                UserService.user(this.user).then(
                    data => {
                        this.message = data.message;
                        this.successful = true;
                    },
                    error => {
                        this.message =
                            (error.response && error.response.data) ?
                                error.response.data.message :
                                error.toString();
                        this.successful = false;
                    }
                );
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

    .card {
        background-color: #f7f7f7;
        padding: 20px 25px 30px;
        margin: 0 auto 25px;
        margin-top: 50px;
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
</style>