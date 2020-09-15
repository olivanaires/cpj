<template>
    <div>
        <b-row align-h="center">
            <b-card class="col-md-12">
                <b-card-body>
                    <b-row>
                        <c-input-text v-model="contract.number" label-value="Número" bs-col-value="col-md-3"
                                      :disabled="true"/>
                        <c-input-text v-model="description" label-value="Descrição" bs-col-value="col-md-3"
                                      :disabled="true"/>
                        <c-input-date v-model="contract.signatureDate" label-value="Data Assinatura" bs-col-value="col-md-2"
                                      :disabled="true"/>
                        <c-input-text v-model="contract.duration" input-type="number" label-value="Duração Meses"
                                      bs-col-value="col-md-2" :disabled="true"/>
                        <c-input-date v-if="contract.signatureEndDate" v-model="contract.signatureEndDate"
                                      label-value="Data Final" bs-col-value="col-md-2"
                                      :disabled="true"/>
                        <c-input-text v-if="!contract.signatureEndDate" v-model="undefinedValue" label-value="Data Final"
                                      bs-col-value="col-md-2" disabled/>
                    </b-row>

                    <b-row>
                        <c-input-text v-model="paymentType" label-value="Tipo Pagamento" bs-col-value="col-md-3"
                                      :disabled="true"/>
                        <b-form-group label="Valor" class=" col-md-3">
                            <money v-model="contract.paymentSignatureValue" class="form-control" :disabled="true"></money>
                        </b-form-group>
                        <b-form-group label="Entrada" class=" col-md-3">
                            <money v-model="contract.entryValue" class="form-control" :disabled="true"></money>
                        </b-form-group>
                        <c-input-date v-model="contract.paymentDate" label-value="Data Pagamento" bs-col-value="col-md-3"
                                      :disabled="true"/>
                    </b-row>

                    <b-row>
                        <b-form-group label="Cliente(s)" class="col-md-6">
                            <b-table :fields="clientFields" :items="contract.contractors"
                                     show-empty empty-text="Informe um ou mais clientes"></b-table>
                        </b-form-group>
                        <b-form-group label="Adivodado(s)" class="col-md-6">
                            <b-table :fields="lawyerFields" :items="contract.hired"
                                     show-empty empty-text="Informe um ou mais adivogados"></b-table>
                        </b-form-group>
                    </b-row>

                    <b-row>
                        <b-form-group label="Observações" class="col-md-12">
                            <b-textarea v-model="contract.observations" rows="3" :disabled="true"></b-textarea>
                        </b-form-group>
                    </b-row>

                    <b-row>
                        <b-form-group label="Aditivo(s)" class="col-md-6">
                            <b-table :fields="additiveFields" :items="additives"
                                     show-empty empty-text="Sem aditivos"></b-table>
                        </b-form-group>
                        <b-form-group label="Arquivo(s)" class="col-md-6">
                            <b-table :fields="fileFields" :items="filesToShow"
                                     show-empty empty-text="Sem arquivos">
                                <template v-slot:cell(options)="data">
                                    <b-link v-on:click="download(data.item.id)" v-if="data.item.id" class="option-item"
                                            v-b-tooltip.hover title="Abrir PDF">
                                        <b-icon icon="file-text"></b-icon>
                                    </b-link>
                                    <b-link v-on:click="remove(data.item.id)" class="option-item"
                                            v-b-tooltip.hover title="Apagar">
                                        <b-icon icon="trash"></b-icon>
                                    </b-link>
                                </template>
                            </b-table>
                        </b-form-group>
                    </b-row>

                    <b-button v-b-modal.show-payments v-on:click="showPayments" variant="primary">Visualizar Pagamentos</b-button>

                </b-card-body>
            </b-card>
        </b-row>

        <b-modal id="show-payments" title="Visualizar Pagamentos" centered size="xl" hide-footer scrollable>
            <c-payment-show :payments="payments" />
        </b-modal>
    </div>
</template>

<script>
    import moment from 'moment';
    import paymentTypes from '../../models/paymentType';
    import FileService from '../../services/file.service';
    import ContractService from '../../services/contract.service';
    import CPaymentShow from "./Payment";
    import AddtiveService from '../../services/additive.service';

    export default {
        name: 'c-contract-show',
        components: {CPaymentShow},
        props: {
            contract: {required: true},
            contractId: {required: true}
        },
        data() {
            return {
                undefinedValue: 'Indefinido',
                filesToShow: [],
                additives: [],
                payments: [],
                clientFields: [
                    {
                        key: 'cpfCnpj',
                        label: 'CPF/CNPJ',
                    },
                    {
                        key: 'clientName',
                        label: 'Nome',
                    },
                ],
                lawyerFields: [
                    {
                        key: 'oabNumber',
                        label: 'OAB',
                    },
                    {
                        key: 'name',
                        label: 'Nome',
                    },
                ],
                additiveFields: [
                    {
                        key: 'signatureDate',
                        label: 'Data Assinatura',
                        formatter: "formatDateValue",
                    },
                    {
                        key: 'duration',
                        label: 'Duração',
                    },
                    {
                        key: 'signatureEndDate',
                        label: 'Data Final',
                        formatter: "formatDateValue",
                    },
                    {
                        key: 'paymentValue',
                        label: 'Valor',
                    },
                ],
                fileFields: [
                    {
                        key: 'name',
                        label: 'Nome',
                    },
                    {
                        key: 'options',
                        label: 'Opções',
                        tdClass: 'center',
                        thClass: 'center'
                    }
                ],
            }
        },
        created() {
            FileService.listByContract(this.contractId).then(response => {
                this.filesToShow.push(...response.data);
            });
            AddtiveService.loadByContract(this.contractId).then(response => {
                this.additives.push(...response.data);
            });
        },
        computed: {
            paymentType: {
                get() {
                    return this.contract.paymentType ? paymentTypes.find(pt => pt.item === this.contract.paymentType).name : '';
                },
                set(value) {
                    return value
                }
            },
            description: {
                get() {
                    return this.contract.description === 'HONORARY' ? 'Honorário' : 'Acessoria'
                },
                set(value) {
                    return value
                }
            }
        },
        methods: {
            download(value) {
                FileService.download(value).then(
                    response => {
                        const file = new Blob([response.data], {type: 'application/pdf'});
                        const fileURL = URL.createObjectURL(file);
                        window.open(fileURL);
                    }
                )
            },
            remove(id) {
                FileService.remove(id)
                    .then(response => {
                        this.$swal({icon: 'success', title: response.data.message})
                    })
                    .catch(error => this.$swal({icon: 'error', title: error.response.data.message}))
                    .finally(() => FileService.listByContract(this.contractId).then(response => {
                        this.filesToShow = [];
                        this.filesToShow.push(...response.data);
                    }));
            },
            formatDateValue(value) {
                return moment(String(value), "YYYY-MM-DD HH:mm:ss").format('DD/MM/YYYY');
            },
            formatCurrency(value) {
                let formatter = new Intl.NumberFormat('pt-BR', {
                    style: 'currency',
                    currency: 'BRL',
                });
                return formatter.format(value);
            },
            showPayments() {
                ContractService.loadPayments(this.contractId).then(response => {
                    this.payments.push(...response.data);
                });
            }
        }
    }

</script>