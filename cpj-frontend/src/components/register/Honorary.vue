<template>
    <b-row align-h="center">
        <b-card :header="title" header-class="header-title" class="col-md-6">
            <b-card-body>
                <ValidationObserver v-slot="{ invalid }">
                    <b-form name="form" @submit.prevent="handleRegister">

                        <b-form-row>
                            <c-input-text label-value="Descrição" v-model="honorary.description" roles-value="required"
                                          :capitalize="true"/>
                        </b-form-row>

                        <b-form-row>
                            <c-input-date label-value="Data Pagamento" v-model="honorary.date" roles-value="required"
                                          bs-col-value="col-md-5"/>
                            <b-form-group label="Valor *" class=" col-md-5">
                                <money v-model="honorary.paymentValue" class="form-control"/>
                            </b-form-group>
                            <c-input-text v-model="honorary.parcels" roles-value="required|numeric" input-type="number"
                                          label-value="Parcelas" bs-col-value="col-md-2"/>
                        </b-form-row>

                        <b-form-row>
                            <c-input-select label-value="Contrato" v-model="honorary.contract.id"
                                            :option-values="contractOptions" roles-value="required"/>
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

    import HonoraryService from '../../services/honorary.service.js';
    import ContractService from '../../services/contract.service.js';
    import Honorary from "../../models/honorary";

    export default {
        name: 'honoraryRegister',
        data() {
            return {
                title: 'Cadastro de Honorários',
                id: this.$route.params.id,
                honorary: new Honorary(new Date()),
                contractOptions: []
            }
        },
        created() {
            ContractService.list()
                .then(response => {
                    this.contractOptions = response.data.map(c => {
                        return {item: String(c.id), name: `${c.number} - ${c.contractors[0].clientName}`};
                    });
                })
                .catch(error => this.$swal({icon: 'error', title: error.response.data.message}));

            if (this.id) {
                HonoraryService.load(this.id)
                    .then(response => {
                        this.honorary = response.data;
                    })
                    .catch(error => this.$swal({icon: 'error', title: error.response.data.message}));
            }
        },
        methods: {
            handleRegister(event) {
                HonoraryService.create(this.honorary).then(
                    response => {
                        event.target.reset();
                        this.$swal({icon: 'success', title: response.data.message});
                        if (this.id) {
                            this.$router.push({name: 'honoraryList'})
                        }
                    },
                    error => {
                        this.$swal({icon: 'error', title: error.response.data.message});
                    });
            }
        }
    }

</script>

<style scoped>
    .card {
        padding: 0px !important;
    }

    .header-title {
        font-size: 25px !important;
        text-align: center;
    }

    .check-box-row {
        margin-top: 15px;
        margin-bottom: 30px;
    }
</style>