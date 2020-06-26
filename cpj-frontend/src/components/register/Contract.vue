<template>
    <b-row align-h="center">
        <b-card :header="title" header-class="header-title" class="col-md-12">
            <b-card-body>
                <ValidationObserver v-slot="{ invalid }">
                    <b-form name="form" @submit.prevent="handleRegister">
                        <b-row>
                            <c-input-text v-model="contract.number" roles-value="required" label-value="Número"
                                          bs-col-value="col-md-3"/>
                            <c-input-text v-model="contract.description" roles-value="required" label-value="Descrição"
                                          bs-col-value="col-md-6"/>
                            <c-input-date v-model="contract.signatureDate" roles-value="required"
                                          label-value="Data Assinatura" bs-col-value="col-md-3"/>
                        </b-row>

                        <b-row>
                            <c-input-text v-model="contract.duration" roles-value="required|numeric"
                                          label-value="Duração" bs-col-value="col-md-2"/>
                            <b-form-group label="Tipo Duração *" class="col-md-2">
                                <b-form-radio-group v-model="contract.durationType" :options="durationTypes"/>
                            </b-form-group>
                            <b-form-group :label="paymentValueLabel" class=" col-md-3">
                                <money v-model="contract.paymentValue" class="form-control"/>
                            </b-form-group>
                            <b-form-group label="Entrada *" class=" col-md-3">
                                <money v-model="contract.entryValue" class="form-control"/>
                            </b-form-group>

                            <b-form-group label="Percentual" class="col-md-2">
                                <money v-model="contract.endPercentValue" v-bind="mask" class="form-control"/>
                            </b-form-group>
                        </b-row>

                        <b-row>
                            <b-form-group label="Clientes" class="col-md-6">
                                <b-input v-model="clientFilter"/>
                                <b-form-select v-model="selectedContractors" value-field="cpf" text-field="clientName"
                                               :options="filteredClientList" multiple :select-size="4"/>
                            </b-form-group>
                            <b-form-group label="Advogados" class="col-md-6">
                                <b-input v-model="lawyerFilter"/>
                                <b-form-select v-model="selectedLawyers" value-field="oabNumber" text-field="name"
                                               :options="filteredLawyerList" multiple :select-size="4"/>
                            </b-form-group>
                        </b-row>

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
    import Contract from '../../models/contract';
    import durationType from '../../models/durationType';
    import ClientService from '../../services/client.service';
    import LawyerService from '../../services/lawyer.service';

    export default {
        name: 'contractRegister',
        data() {
            return {
                title: 'Cadastro de Contrato',
                durationTypes: durationType,
                contract: new Contract(new Date(), durationType[0].value),
                clientFilter: '',
                clientList: [],
                selectedContractors: [],
                lawyerFilter: '',
                lawyerList: [],
                selectedLawyers: [],
                mask: {
                    decimal: ',',
                    thousands: '.',
                    prefix: '',
                    suffix: ' %',
                    precision: 2,
                    masked: false
                }
            }
        },
        created() {
            ClientService.list().then(
                result => this.clientList = result.data,
                error => this.$swal({icon: 'error', title: error.response.data.message})
            );
            LawyerService.list().then(
                result => this.lawyerList = result.data,
                error => this.$swal({icon: 'error', title: error.response.data.message})
            );
        },
        computed: {
            paymentValueLabel() {
                const label = durationType.filter(dt => dt.value === this.contract.durationType)[0];
                return 'Valor ' + label.text + ' *';
            },
            filteredClientList() {
                if (this.clientFilter) {
                    const exp = new RegExp(this.clientFilter, 'i');
                    return this.clientList.filter(c => exp.test(c.clientName));
                } else {
                    return this.clientList;
                }
            },
            filteredLawyerList() {
                if (this.lawyerFilter) {
                    const exp = new RegExp(this.lawyerFilter, 'i');
                    return this.lawyerList.filter(l => exp.test(l.name));
                } else {
                    return this.lawyerList;
                }
            }
        },
        methods: {
            handleRegister(event) {
                this.contract.contractors = this.selectedContractors;
                this.contract.hired = this.selectedLawyers;
                console.log(this.contract);
                event.target.reset();
                this.contract.signatureDate = new Date();
            }
        }
    }

</script>

<style scoped>
    .header-title {
        font-size: 25px !important;
        text-align: center;
    }

    .card {
        padding: 0px !important;
    }
</style>