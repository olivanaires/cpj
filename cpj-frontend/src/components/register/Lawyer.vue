<template>
    <b-row align-h="center">
        <b-card :header="title" header-class="header-title" class="col-md-6">
            <b-card-body>
                <ValidationObserver v-slot="{ invalid }">
                    <b-form name="form" @submit.prevent="handleRegister">

                        <b-form-row>
                            <c-input-text label-value="Nome" v-model="lawyer.name"
                                          bs-col-value="col-md-12" roles-value="required"/>
                        </b-form-row>

                        <b-form-row>
                            <c-input-text label-value="Nº OAB" v-model="lawyer.oabNumber"
                                          bs-col-value="col-md-6" roles-value="required"/>
                            <c-input-text label-value="Username" v-model="lawyer.user.username"
                                          bs-col-value="col-md-6" roles-value="required"/>
                        </b-form-row>

                        <b-form-row>
                            <c-input-text label-value="E-Mail" v-model="lawyer.user.email"
                                          bs-col-value="col-md-12" roles-value="required|email"/>
                        </b-form-row>

                        <b-form-row>
                            <div class="col-md-6">
                                <ValidationProvider name="Senha *" rules="required|min:6|confirmed:passwordConfirmation"
                                                    v-slot="{ errors }">
                                    <b-form-group label-for="input-senha" label="Senha">
                                        <b-form-input id="input-senha" v-model="lawyer.user.password" type="password"/>
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
    import LawyerService from '../../services/lawyer.service';
    import Lawyer from "../../models/lawyer";

    export default {
        name: 'lawyerRegister',
        data() {
            return {
                title: 'Cadastrar Advogado',
                id: this.$route.params.id,
                lawyer: new Lawyer(),
                passwordConfirmation: '',
            };
        },
        created() {
            if (this.id) {
                LawyerService.load(this.id)
                    .then(response => {
                        this.lawyer = response.data;
                    })
                    .catch(error => this.$swal({icon: 'error', title: error.response.data.message}));
            }
        },
        methods: {
            handleRegister(event) {
                LawyerService.create(this.lawyer).then(
                    result => {
                        event.target.reset();
                        this.$swal({icon: 'success', title: result.data.message});
                        if (this.id) {
                            this.$router.push({name: 'contractList'})
                        }
                    },
                    error => {
                        this.$swal({icon: 'error', title: error.response.data.message});
                    });
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