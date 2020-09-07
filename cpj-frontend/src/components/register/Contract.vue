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
                                <money v-model="contract.paymentValue" class="form-control"></money>
                            </b-form-group>
                            <b-form-group label="Entrada" class=" col-md-3">
                                <money v-model="contract.entryValue" class="form-control"></money>
                            </b-form-group>
                            <c-input-date v-model="contract.paymentDate" roles-value="required" @input="updateEndDate"
                                          label-value="Data Pagamento" bs-col-value="col-md-3"/>
                        </b-row>

                        <b-row>
                            <b-form-group label="Clientes" class="col-md-6">
                                <b-form-select class="col-md-10" v-model="contractor" value-field="cpfCnpj"
                                               text-field="clientName"
                                               :options="clientList"/>
                                <b-button class="col-md-2" type="button" @click="addClient" variant="primary">Add
                                </b-button>
                            </b-form-group>

                            <b-form-group label="Advogados" class="col-md-6">
                                <b-form-select class="col-md-10" v-model="employee" value-field="oabNumber"
                                               text-field="name"
                                               :options="lawyerList"/>
                                <b-button class="col-md-2" type="button" @click="addLawyer" variant="primary">Add
                                </b-button>
                            </b-form-group>
                        </b-row>

                        <b-row>
                            <b-form-group label="" class="col-md-6">
                                <b-table class="testeTable" :items="selectedContractors" :fields="clientFields">
                                    <template v-slot:cell(options)="data">
                                        <b-link v-on:click="removeClient(data.item.cpfCnpj)"
                                                v-b-tooltip.hover title="Apagar">
                                            <b-icon icon="trash"></b-icon>
                                        </b-link>
                                    </template>
                                </b-table>
                            </b-form-group>
                            <b-form-group label="" class="col-md-6">
                                <b-table :items="selectedLawyers" :fields="lawyerFields">
                                    <template v-slot:cell(options)="data">
                                        <b-link v-on:click="removeLawyer(data.item.oabNumber)"
                                                v-b-tooltip.hover title="Apagar">
                                            <b-icon icon="trash"></b-icon>
                                        </b-link>
                                    </template>
                                </b-table>
                            </b-form-group>
                        </b-row>

                        <b-row>
                            <b-form-group label="Observações" class="col-md-12">
                                <b-textarea v-model="contract.observations" rows="3"></b-textarea>
                            </b-form-group>
                            <b-form-group label="Contrato (PDF)" class="col-md-12">
                                <b-form-file v-model="files" accept="application/pdf" browse-text="Buscar"
                                             placeholder="Selecionar arquivo..." multiple
                                             drop-placeholder="Soltar arquivo aqui..."></b-form-file>
                            </b-form-group>
                        </b-row>

                        <b-row align-h="center">
                            <b-button class="col-md-2" type="submit" :disabled="invalidForm(invalid)" variant="success">
                                Salvar
                            </b-button>
                        </b-row>
                    </b-form>

                </ValidationObserver>
            </b-card-body>
        </b-card>
    </b-row>
</template>

<script>
    import axios from 'axios';
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
                files: null,
                contractor: null,
                employee: null,
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
                },
                clientFields: [
                    {
                        key: 'cpfCnpj',
                        label: 'CPF/CNPJ',
                        thClass: 'teste'
                    },
                    {
                        key: 'clientName',
                        label: 'Nome',
                        thClass: 'teste'
                    },
                    {
                        key: 'options',
                        label: '',
                    }
                ],
                lawyerFields: [
                    {
                        key: 'oabNumber',
                        label: 'OAB',
                        thClass: 'teste'
                    },
                    {
                        key: 'name',
                        label: 'Nome',
                        thClass: 'teste'
                    },
                    {
                        key: 'options',
                        label: '',
                    },
                ]
            }
        },
        methods: {
            handleRegister(event) {
                ContractService.create(this.contract)
                    .then(
                        response => {
                            event.target.reset();
                            this.contract.signatureDate = new Date();
                            this.selectedLawyers = [];
                            this.selectedContractors = [];
                            this.$swal({icon: 'success', title: response.data.message});

                            if (this.files && this.files.length > 0) {
                                this.files.forEach(file => {
                                    let formData = new FormData();
                                    formData.append("file", file);
                                    formData.append("contractId", response.data.id);
                                    FileService.upload(formData)
                                        .then(() => {
                                            this.files = null;
                                        }, error => {
                                            this.$swal({icon: 'error', title: error.response.data.message});
                                        });
                                });
                            }
                        }
                    )
                    .then(() => {
                        if (this.id) {
                            this.$router.push({name: 'contractList'})
                        }
                    })
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
                    this.contract.signatureEndDate = date;
                }
            },
            addClient() {
                const client = this.clientList.find(c => c.cpfCnpj === this.contractor);
                if (client.cpfCnpj && !this.contract.contractors.includes(client.cpfCnpj)) {
                    this.contract.contractors.push(client.cpfCnpj);
                    this.selectedContractors.push(client);
                    this.contractor = this.clientList[0].cpfCnpj;
                }
            },
            removeClient(cpfCnpj) {
                let index = this.contract.contractors.findIndex(val => val === cpfCnpj);
                this.contract.contractors.splice(cpfCnpj, 1);

                index = this.selectedContractors.findIndex(c => c.cpfCnpj === cpfCnpj);
                this.selectedContractors.splice(index, 1);
            },
            addLawyer() {
                const lawyer = this.lawyerList.find(l => l.oabNumber === this.employee);
                if (lawyer.oabNumber && !this.contract.hired.includes(lawyer.oabNumber)) {
                    this.contract.hired.push(lawyer.oabNumber);
                    this.selectedLawyers.push(lawyer);
                }
            },
            removeLawyer(oabNumber) {
                let index = this.contract.hired.findIndex(val => val === oabNumber);
                this.contract.hired.splice(index, 1);

                index = this.selectedLawyers.findIndex(l => l.oabNumber === oabNumber);
                this.selectedLawyers.splice(index, 1);
            },
            invalidForm(invalid) {
                return invalid
                    || this.selectedContractors.length === 0 || this.selectedLawyers.length === 0
                    || this.contract.paymentValue <= 0;
            }
        },
        created() {
            let requests;
            if (this.id) {
                requests = [ClientService.list(), LawyerService.list(), ContractService.load(this.id)];
            } else {
                requests = [ClientService.list(), LawyerService.list()];
            }

            axios.all(requests)
                .then(result => {
                    this.clientList.push({cpfCnpj: '', clientName: 'Selecione um cliente...'});
                    this.clientList.push(...result[0].data);
                    if (this.clientList[0]){
                        this.contractor = this.clientList[0].cpfCnpj;
                    }

                    this.lawyerList = result[1].data;
                    if (this.lawyerList[0]){
                        this.employee = this.lawyerList[0].oabNumber;
                    }

                    if (this.id && result[2]) {
                        this.contract = result[2].data;
                        this.contract.hired = result[2].data.hired.map(h => h.oabNumber);
                        this.contract.contractors = result[2].data.contractors.map(h => h.cpfCnpj);

                        this.selectedLawyers = this.lawyerList.filter(l => this.contract.hired.includes(l.oabNumber));
                        this.selectedContractors = this.clientList.filter(c => this.contract.contractors.includes(c.cpfCnpj));
                    }
                });
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