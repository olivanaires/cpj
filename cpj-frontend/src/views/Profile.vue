<template>
    <div class="container">
        <header class="jumbotron">
            <h3>
                <strong>{{currentUser.username}}</strong> Profile
            </h3>
        </header>
        <p>
            <strong>Email:</strong>
            {{currentUser.email}}
        </p>
        <strong>Authorities:</strong>
        <ul>
            <li v-for="(role,index) in currentUser.roles" :key="index">{{role}}</li>
        </ul>

        <b-row align-h="center">
            <b-card :header="title" header-class="header-title" class="col-md-6">
                <b-card-body>
                    <ValidationObserver v-slot="{ invalid }">
                        <b-form name="form" @submit.prevent="handleForm">
                            <b-form-row>
                                <div class="col-md-6">
                                    <ValidationProvider name="Senha *"
                                                        rules="required|min:6|confirmed:passwordConfirmation"
                                                        v-slot="{ errors }">
                                        <b-form-group label-for="input-senha" label="Senha">
                                            <b-form-input id="input-senha" v-model="password" type="password"/>
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
                                <b-button class="col-md-3" type="submit" :disabled="invalid" variant="success">
                                    Atualizar
                                </b-button>
                            </b-row>
                        </b-form>
                    </ValidationObserver>
                </b-card-body>
            </b-card>
        </b-row>
    </div>
</template>

<script>
    import UserService from '../services/user.service';

    export default {
        name: 'Profile',
        data() {
            return {
                title: 'Atualizar Senha',
                password: '',
                passwordConfirmation: ''
            }
        },
        computed: {
            currentUser() {
                return this.$store.state.auth.user;
            }
        },
        methods: {
            handleForm() {
                UserService.update(this.$store.state.auth.user.username, this.password).then(
                    result => {
                        this.$swal({icon: 'success', title: result.data.message});
                    },
                    error => {
                        this.$swal({icon: 'error', title: error.response.data.message});
                    }
                );
            }
        },
        mounted() {
            if (!this.currentUser) {
                this.$router.push('/login');
            }
        }
    };
</script>

<style scoped>

    .card {
        padding: 0px !important;
    }

    span.c-erro-msg {
        color: #cc0033;
        display: inline-block;
        font-size: 12px;
        line-height: 15px;
        margin: 0;
    }

    .header-title {
        font-size: 20px !important;
        text-align: center;
    }
</style>