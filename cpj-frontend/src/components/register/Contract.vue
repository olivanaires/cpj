<template>
    <b-row align-h="center">
        <b-card :header="title" header-class="header-title" class="col-md-12">
            <b-card-body>
                <ValidationObserver v-slot="{ invalid }">
                    <b-form name="form" @submit.prevent="handleRegister">
                        <b-row>
                            <c-input-text v-model="contract.number" roles-value="required" label-value="Número"
                                          bs-col-value="col-md-3"/>
                            <c-input-select v-model="contract.description" roles-value="required"
                                            label-value="Descrição" :option-values="contractType"
                                            bs-col-value="col-md-3"/>
                            <c-input-date v-model="contract.signatureDate" roles-value="required" @input="updateEndDate"
                                          label-value="Data Assinatura" bs-col-value="col-md-2"/>
                            <c-input-text v-model="contract.duration" roles-value="required|numeric" input-type="number"
                                          label-value="Duração Meses" bs-col-value="col-md-2"
                                          @input="updateEndDate"/>
                            <c-input-date v-model="contract.endDate" roles-value="required" label-value="Data Final"
                                          bs-col-value="col-md-2" :disabled="true"/>
                        </b-row>

                        <b-row>
                            <c-input-select v-model="contract.paymentType" :option-values="paymentTypeOptions"
                                            label-value="Tipo Pagamento" roles-value="required"
                                            bs-col-value="col-md-3"/>
                            <b-form-group :label="paymentValueLabel" class=" col-md-3">
                                <money v-model="contract.paymentValue" class="form-control"/>
                            </b-form-group>
                            <b-form-group label="Entrada" class=" col-md-3">
                                <money v-model="contract.entryValue" class="form-control"/>
                            </b-form-group>
                            <c-input-date v-model="contract.paymentDate" roles-value="required" @input="updateEndDate"
                                          label-value="Data Pagamento" bs-col-value="col-md-3"/>
                        </b-row>

                        <b-row>
                            <b-form-group label="Clientes" class="col-md-6">
                                <b-form-select v-model="contract.contractors" value-field="cpfCnpj"
                                               text-field="clientName"
                                               :options="filteredClientList" multiple :select-size="6"/>
                            </b-form-group>
                            <b-form-group label="Advogados" class="col-md-6">
                                <b-form-select v-model="contract.hired" value-field="oabNumber" text-field="name"
                                               :options="filteredLawyerList" multiple :select-size="6"/>
                            </b-form-group>
                        </b-row>

                        <b-row>
                            <b-form-group label="Observações" class="col-md-12">
                                <b-textarea v-model="contract.observations" rows="3"></b-textarea>
                            </b-form-group>
                            <b-form-group label="Contrato (PDF)" class="col-md-12">
                                <b-form-file v-model="file" accept="application/pdf" browse-text="Buscar"
                                             placeholder="Selecionar arquivo..."
                                             drop-placeholder="Soltar arquivo aqui..."></b-form-file>
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
    import FileService from '../../services/file.service';

    export default {
        name: 'contractRegister',
        data() {
            return {
                title: 'Cadastro de Contrato',
                id: this.$route.params.id,
                durationTypeOptions: durationTypes,
                paymentTypeOptions: paymentTypes,
                contract: new Contract(new Date(), durationTypes[0].value, paymentTypes[0].item),
                clientList: [],
                selectedContractors: [],
                lawyerList: [],
                selectedLawyers: [],
                file: null,
                contractType: [
                    {item: 'HONORARY', name: 'Honorário'},
                    {item: 'LEGAL_ADVICE', name: 'Assessoria '}
                ],
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
                        this.contract.hired = response.data.hired.map(h => h.oabNumber);
                        this.contract.contractors = response.data.contractors.map(h => h.cpfCnpj);
                    })
                    .catch(error => this.$swal({icon: 'error', title: error.response.data.message}));
            }
        },
        computed: {
            paymentValueLabel() {
                if (this.contract.paymentType && this.contract.paymentType.includes('MONTHLY')) {
                    return 'Valor Mês *';
                } else if (this.contract.paymentType && this.contract.paymentType.includes('YEARLY')) {
                    return 'Valor Ano *';
                } else {
                    return 'Valor';
                }
            },
            filteredClientList() {
                return this.clientList;
            },
            filteredLawyerList() {
                return this.lawyerList;
            }
        },
        methods: {
            handleRegister(event) {
                ContractService.create(this.contract)
                    .then(
                        response => {
                            event.target.reset();
                            this.contract.signatureDate = new Date();
                            this.$swal({icon: 'success', title: response.data.message});

                            if (this.file) {
                                let formData = new FormData();
                                formData.append("file", this.file);
                                formData.append("contractId", response.data.id);
                                FileService.upload(formData)
                                    .then(result => {
                                        console.log(result.data.message);
                                        this.file = null;
                                        if (this.id) {
                                            this.$router.push({name: 'contractList'})
                                        }
                                    }, error => {
                                        this.$swal({icon: 'error', title: error.response.data.message});
                                    });
                            }


                        }
                    )
                    .then()
                    .catch(error => this.$swal({icon: 'error', title: error.response.data.message}));


            },
            updateEndDate() {
                if (this.contract.duration > 0) {
                    let date = new Date(this.contract.signatureDate);

                    if (this.contract.durationType === 'YEAR') {
                        date.setFullYear(date.getFullYear() + Number(this.contract.duration));
                    } else {
                        date.setMonth(date.getMonth() + Number(this.contract.duration));
                    }

                    this.contract.endDate = date;
                }
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