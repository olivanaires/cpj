<template>
    <b-row align-h="center">
        <b-card :header="title" header-class="header-title" class="col-md-6">
            <b-card-body>
                <ValidationObserver v-slot="{ invalid }">
                    <b-form name="form" @submit.prevent="handleRegister">

                        <b-form-row>
                            <c-input-text label-value="Nome" v-model="name"
                                          bs-col-value="col-md-12" roles-value="required"/>
                        </b-form-row>

                        <b-form-row>
                            <c-input-text label-value="Nº OAB" v-model="oabNumber"
                                          bs-col-value="col-md-6" roles-value="required"/>
                            <c-input-text label-value="Username" v-model="user.username"
                                          bs-col-value="col-md-6" roles-value="required"/>
                        </b-form-row>

                        <b-form-row>
                            <c-input-text label-value="E-Mail" v-model="user.email"
                                          bs-col-value="col-md-12" roles-value="required|email"/>
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
    import User from '../../models/user';

    export default {
        name: 'lawyerRegister',
        data() {
            return {
                title: 'Cadastrar Advogado',
                name: '',
                oabNumber: '',
                user: new User('ROLE_LAWYER', '', '', '')
            };
        },
        methods: {
            handleRegister(event) {
                LawyerService.create({oabNumber: this.oabNumber, name: this.name, user: this.user}).then(
                    result => {
                        event.target.reset();
                        this.$swal({icon: 'success', title: result.data.message});
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