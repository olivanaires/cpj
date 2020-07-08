<template>
    <b-row align-h="center">
        <b-card :header="title" header-class="header-title" class="col-md-6">
            <b-card-body>
                <ValidationObserver v-slot="{ invalid }">
                    <b-form name="form" @submit.prevent="handleRegister">

                        <b-form-row>
                            <c-input-text label-value="Descrição" v-model="expense.description" roles-value="required"
                                          :capitalize="true"/>
                        </b-form-row>

                        <b-form-row>
                            <c-input-date label-value="Data" v-model="expense.date" roles-value="required"
                                          bs-col-value="col-md-6"/>
                            <b-form-group label="Valor *" class=" col-md-6">
                                <money v-model="expense.paymentValue" class="form-control"/>
                            </b-form-group>
                        </b-form-row>

                        <b-form-row>
                            <c-input-select label-value="Contrato" v-model="expense.contract.id"
                                            :option-values="contractOptions" roles-value="required"/>
                        </b-form-row>

                        <b-form-row class="check-box-row">
                            <b-form-checkbox v-model="expense.refundable">
                                Contabilizar nos gastos do contrado?
                            </b-form-checkbox>
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

    import ExpenseService from '../../services/expense.service.js';
    import ContractService from '../../services/contract.service.js';
    import Expense from "../../models/expense";

    export default {
        name: 'expenseRegister',
        data() {
            return {
                title: 'Cadastro de Despesas',
                expense: new Expense(),
                contractOptions: []
            }
        },
        created() {
            ContractService.list()
                .then(response => {
                    this.contractOptions = response.data.map(c => {
                        return {item: String(c.id), name: `${c.number} - ${c.description}`};
                    });
                })
                .catch(error => this.$swal({icon: 'error', title: error.response.data.message}));
        },
        methods: {
            handleRegister(event) {
                ExpenseService.create(this.expense).then(
                    response => {
                        event.target.reset();
                        this.$swal({icon: 'success', title: response.data.message});
                        if (this.id) {
                            this.$router.push({name: 'lawyerList'})
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