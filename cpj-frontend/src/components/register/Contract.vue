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
                            <b-form-group label="Tipo Duração *" class="col-md-3">
                                <b-form-radio-group v-model="contract.durationType" :options="durationTypeOptions"/>
                            </b-form-group>
                            <b-form-group :label="paymentValueLabel" class=" col-md-2">
                                <money v-model="contract.paymentValue" class="form-control"/>
                            </b-form-group>
                            <b-form-group label="Entrada *" class=" col-md-2">
                                <money v-model="contract.entryValue" class="form-control"/>
                            </b-form-group>

                            <b-form-group label="Percentual" class="col-md-2">
                                <money v-model="contract.endPercentValue" v-bind="mask" class="form-control"/>
                            </b-form-group>
                        </b-row>

                        <b-row>
                            <b-form-group label="Tipo Pagamento *" class="col-md-12">
                                <b-form-checkbox-group v-model="contract.paymentTypes" :options="paymentTypeOptions">
                                </b-form-checkbox-group>
                            </b-form-group>
                        </b-row>

                        <b-row>
                            <b-form-group label="Clientes" class="col-md-6">
                                <b-form-select v-model="contract.contractors" value-field="cpfCnpj" text-field="clientName"
                                               :options="filteredClientList" multiple :select-size="6"/>
                            </b-form-group>
                            <b-form-group label="Advogados" class="col-md-6">
                                <b-form-select v-model="contract.hired" value-field="oabNumber" text-field="name"
                                               :options="filteredLawyerList" multiple :select-size="6"/>
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
    import durationTypes from '../../models/durationType';
    import paymentTypes from '../../models/paymentType';
    import ClientService from '../../services/client.service';
    import LawyerService from '../../services/lawyer.service';
    import ContractService from '../../services/contract.service';

    export default {
        name: 'contractRegister',
        data() {
            return {
                title: 'Cadastro de Contrato',
                id: this.$route.params.id,
                durationTypeOptions: durationTypes,
                paymentTypeOptions: paymentTypes,
                contract: new Contract(new Date(), durationTypes[0].value),
                clientList: [],
                selectedContractors: [],
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

            if (this.id) {
                ContractService.load(this.id)
                    .then(response => {
                        this.contract = response.data;
                        this.contract.signatureDate = new Date(response.data.signatureDate);
                        this.contract.duration = String(response.data.duration);
                        this.contract.hired = response.data.hired.map(h => h.oabNumber);
                        this.contract.contractors = response.data.contractors.map(h => h.cpfCnpj);
                    })
                    .catch(error => this.$swal({icon: 'error', title: error.response.data.message}));
            }
        },
        computed: {
            paymentValueLabel() {
                const label = durationTypes.filter(dt => dt.value === this.contract.durationType)[0];
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
                ContractService.create(this.contract).then(
                    response => {
                        event.target.reset();
                        this.contract.signatureDate = new Date();
                        this.$swal({icon: 'success', title: response.data.message});
                        if (this.id) {
                            this.$router.push({name: 'contractList'})
                        }
                    },
                    error => {
                        this.$swal({icon: 'error', title: error.response.data.message});
                    }
                );

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