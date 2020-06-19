<template>
    <b-row align-h="center">
        <b-card :header="title" header-class="header-title" class="col-md-6">
            <b-card-body>

                <b-alert :show="dismissCountDown"
                         dismissible
                         :variant="alertType"
                         @dismissed="dismissCountDown=0"
                         @dismiss-count-down="countDownChanged">
                    {{message}}
                </b-alert>
                <ValidationObserver v-slot="{ invalid }">
                    <img id="profile-img"
                         src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                         class="profile-img-card"/>
                    <b-form name="form" @submit.prevent="handleRegister">

                        <b-form-row>
                            <c-input-text label-value="Username" v-model="user.username"
                                          bs-col-value="col-md-6" roles-value="required"/>
                            <c-input-select label-value="Papel" v-model="user.role" :option-values="roleOptions"
                                            bs-col-value="col-md-6" roles-value="required"/>
                        </b-form-row>

                        <b-form-row>
                            <c-input-text label-value="E-Mail" v-model="user.email"
                                          bs-col-value="col-md-12" roles-value="required|email"/>
                        </b-form-row>

                        <b-form-row>
                            <div class="col-md-6">
                                <ValidationProvider name="Senha *" rules="required|min:6|confirmed:passwordConfirmation"
                                                    v-slot="{ errors }">
                                    <b-form-group label-for="input-senha" label="Senha">
                                        <b-form-input id="input-senha" v-model="user.password" type="password"/>
                                        <span class="c-erro-msg">{{ errors[0] }}</span>
                                    </b-form-group>
                                </ValidationProvider>
                            </div>

                            <div class="col-md-6">
                                <ValidationProvider v-slot="{ errors }" vid="passwordConfirmation">
                                    <b-form-group label-for="input-senha-c" label="Confirmação Senha *">
                                        <b-form-input id="input-senha-passwordConfirmation"
                                                      v-model="passwordConfirmation"
                                                      type="password"/>
                                        <span class="c-erro-msg">{{ errors[0] }}</span>
                                    </b-form-group>
                                </ValidationProvider>
                            </div>
                        </b-form-row>

                        <b-row align-h="center">
                            <b-button class="col-md-2" type="submit" :disabled="invalid" variant="success">Salvar
                            </b-button>
                        </b-row>

                    </b-form>
                </ValidationObserver>
            </b-card-body>
        </b-card>
    </b-row>
</template>

<script>
    import UserService from '../../services/user.service'
    import User from '../../models/user';

    export default {
        name: 'Register',
        data() {
            return {
                title: 'Cadastrar Usuário',
                user: new User('ROLE_USER', '', '', ''),
                passwordConfirmation: '',
                alertType: '',
                message: '',
                dismissSecs: 5,
                dismissCountDown: 0,
                showDismissibleAlert: false,
                roleOptions: [
                    {item: 'ROLE_USER', name: 'Usuário'},
                    {item: 'ROLE_LAWYER', name: 'Advogado'}
                ]
            };
        },
        computed: {
            loggedIn() {
                return this.$store.state.auth.status.loggedIn;
            }
        },
        methods: {
            handleRegister() {
                this.message = '';
                UserService.create(this.user).then(
                    result => {
                        this.message = result.data.message;
                        this.user = new User('ROLE_USER', '', '', '');
                        this.passwordConfirmation = '';
                        this.alertType = 'success';
                        this.dismissCountDown = this.dismissSecs
                    },
                    error => {
                        this.message =
                            (error.response && error.response.data) ?
                                error.response.data.message :
                                error.toString();
                        this.alertType = 'danger';
                        this.dismissCountDown = this.dismissSecs;
                    });
            },
            countDownChanged(dismissCountDown) {
                this.dismissCountDown = dismissCountDown
            }
        }
    };
</script>

<style scoped>

    .card {
        padding: 0px !important;
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

    span.c-erro-msg {
        color: #cc0033;
        display: inline-block;
        font-size: 12px;
        line-height: 15px;
        margin: 0;
    }

    .header-title {
        font-size: 25px !important;
        text-align: center;
    }
</style>