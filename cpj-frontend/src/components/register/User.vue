<template>
    <b-row align-h="center">
        <b-card :header="title" header-class="header-title" class="col-md-6">
            <b-card-body>
                <ValidationObserver v-slot="{ invalid }">
                    <img id="profile-img"
                         src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                         class="profile-img-card"/>
                    <b-form name="form" @submit.prevent="handleRegister">

                        <b-form-row>
                            <c-input-text label-value="Username" v-model="user.username"
                                          bs-col-value="col-md-6" roles-value="required"/>
                            <c-input-select label-value="Papel" v-model="user.role" :option-values="roleOptions"
                                            bs-col-value="col-md-6" roles-value="required"
                                            :disabled="Boolean(user.id)"/>
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
                            <b-button class="col-md-3" type="submit" :disabled="invalid" variant="success">Salvar</b-button>
                            <div class="space"/>
                            <b-button class="col-md-3" type="button" @click="refresh" variant="success">Cancelar</b-button>
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
        name: 'userRegister',
        data() {
            return {
                title: 'Cadastrar Usuário',
                id: this.$route.params.id,
                user: new User('ROLE_USER', '', '', ''),
                passwordConfirmation: '',
                roleOptions: [
                    {item: 'ROLE_USER', name: 'Usuário'},
                    {item: 'ROLE_LAWYER', name: 'Advogado'}
                ]
            };
        },
        created() {
            if (this.id) {
                UserService.load(this.id).then(
                    response => {
                        this.user = response.data;
                    },
                    error => {
                        this.$swal({icon: 'error', title: error.response.data.message});
                    }
                );
            }
        },
        methods: {
            handleRegister(event) {
                UserService.create(this.user).then(
                    response => {
                        event.target.reset();
                        this.$swal({icon: 'success', title: response.data.message});
                        if (this.id) {
                            this.$router.push({name: 'userList'})
                        }
                    },
                    error => {
                        this.$swal({icon: 'error', title: error.response.data.message});
                    });
            },
            refresh() {
                this.$el.getElementsByTagName("form").namedItem('form').reset()
                if (this.id) {
                    this.$router.push({name: 'userList'});
                }
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

    .space {
        margin: 10px;
    }
</style>