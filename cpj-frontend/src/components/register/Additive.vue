<template>
    <b-row align-h="center">
        <b-card :header="title" header-class="header-title" class="col-md-6">
            <b-card-body>
                <ValidationObserver v-slot="{ invalid }">
                    <b-form name="form" @submit.prevent="handleRegister">

                        <b-form-row>
                            <b-form-group label="Valor *" class=" col-md-6">
                                <money v-model="additive.paymentValue" class="form-control"></money>
                            </b-form-group>
                        </b-form-row>

                        <b-form-row>
                            <c-input-date label-value="Data Pagamento" v-model="additive.signatureDate"
                                          roles-value="required" @input="updateEndDate"
                                          bs-col-value="col-md-5"/>

                            <c-input-text v-model="additive.duration" roles-value="required|numeric" input-type="number"
                                          label-value="Duração" bs-col-value="col-md-2"
                                          @input="updateEndDate"/>

                            <c-input-date v-model="additive.endDate" roles-value="required" label-value="Data Final"
                                          bs-col-value="col-md-5" :disabled="true"/>
                        </b-form-row>

                        <b-form-row>
                            <c-input-select label-value="Contrato" v-model="additive.contractId"
                                            :option-values="contractOptions" roles-value="required"/>
                        </b-form-row>

                        <b-row align-h="center">
                            <b-button class="col-md-2" type="submit" :disabled="invalidForm(invalid)" variant="success">Salvar
                            </b-button>
                        </b-row>

                    </b-form>
                </ValidationObserver>
            </b-card-body>
        </b-card>
    </b-row>
</template>

<script>

    import AdditiveService from '../../services/additive.service.js';
    import ContractService from '../../services/contract.service.js';
    import Additive from "../../models/additive";

    export default {
        name: 'additiveRegister',
        data() {
            return {
                title: 'Cadastro de Aditivo',
                id: this.$route.params.id,
                additive: new Additive(new Date()),
                contractOptions: []
            }
        },
        created() {
            ContractService.list()
                .then(response => {
                    this.contractOptions = response.data.map(c => {
                        const clientName = c.contractors.map(item => item.clientName).join(", ");
                        return {item: String(c.id), name: `${c.number} - ${clientName}`};
                    });
                })
                .catch(error => this.$swal({icon: 'error', title: error.response.data.message}));

            if (this.id) {
                AdditiveService.load(this.id)
                    .then(response => {
                        this.additive = response.data;
                    })
                    .catch(error => this.$swal({icon: 'error', title: error.response.data.message}));
            }
        },
        methods: {
            handleRegister(event) {
                AdditiveService.create(this.additive).then(
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
            },
            updateEndDate() {
                if (this.additive.duration > 0) {
                    let date = new Date(this.additive.signatureDate);
                    date.setMonth(date.getMonth() + Number(this.additive.duration));
                    this.additive.endDate = date;
                } else {
                    this.additive.endDate = this.additive.signatureDate
                }
            },
            invalidForm(invalid) {
                return invalid
                    || this.additive.paymentValue <= 0;
            }
        },
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