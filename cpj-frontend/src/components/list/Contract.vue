<template>
    <div>
        <b-row align-h="center">
            <b-card :header="title" header-class="header-title" class="col-md-12">
                <b-card-body>
                    <b-card header="Filtros" class="col-md-12">
                        <b-input type="search" class="filtro" @input="filter = $event"
                                 placeholder="Filtre por parte do número"/>
                    </b-card>

                    <b-table striped hover bordered :items="filtered" :fields="fields">
                        <template v-slot:cell(index)="data">
                            {{ data.index + 1 }}
                        </template>
                        <template v-slot:cell(duration)="data">
                            {{ data.item.duration + " " + toStringDuratioType(data.item.durationType) }}
                        </template>
                        <template v-slot:cell(options)="data">
                            <b-link :to="`/contractUpdate/${data.item.id}`" class="option-item"
                                    v-b-tooltip.hover title="Editar">
                                <b-icon icon="pencil"></b-icon>
                            </b-link>
                            <b-link v-on:click="remove(data.item.id)" class="option-item"
                                    v-b-tooltip.hover title="Apagar">
                                <b-icon icon="trash"></b-icon>
                            </b-link>
                            <b-link v-b-modal.show-contract v-on:click="show(data.item.id)" class="option-item"
                                    v-b-tooltip.hover title="Visualizar">
                                <b-icon icon="search"></b-icon>
                            </b-link>
                        </template>
                    </b-table>
                </b-card-body>
            </b-card>
        </b-row>

        <b-modal id="show-contract" title="Visualizar Contrato" centered size="xl" hide-footer>
            <c-contract-show :contract="contractToShow" :contract-id="contractIdToShow"/>
        </b-modal>
    </div>
</template>

<script>
    import axios from 'axios';
    import moment from 'moment';
    import ContractService from '../../services/contract.service';
    import durationTypes from '../../models/durationType';
    import FileService from '../../services/file.service';
    import CContractShow from "../show/Contract";
    import Contract from "../../models/contract";
    import AddtiveService from '../../services/additive.service';

    export default {
        name: 'contractList',
        components: {CContractShow},
        data() {
            return {
                title: 'Listagem de Contratos',
                filter: '',
                listResult: [],
                contractToShow: new Contract(),
                contractIdToShow: null,
                fields: [
                    {
                        key: 'index',
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'number',
                        label: 'Número',
                        sortable: true,
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'signatureDate',
                        label: 'Data Assinatura',
                        sortable: true,
                        thClass: 'bg-dark text-white',
                        formatter: "formatDateAssigned"
                    },
                    {
                        key: 'endDate',
                        label: 'Data Final',
                        sortable: true,
                        thClass: 'bg-dark text-white',
                        formatter: "formatDateAssigned"
                    },
                    {
                        key: 'duration',
                        label: 'Duração',
                        sortable: true,
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'options',
                        label: 'Opções',
                        thClass: 'bg-dark text-white'
                    }
                ]
            }
        },
        created() {
            ContractService.list().then(response => this.listResult = response.data);
        },
        computed: {
            filtered() {
                if (this.filter) {
                    let exp = new RegExp(this.filter.trim(), 'i');
                    return this.listResult.filter(c => exp.test(c.number));
                } else {
                    return this.listResult;
                }
            }
        },
        methods: {
            formatDateAssigned(value) {
                return moment(String(value), "YYYY-MM-DD HH:mm:ss").format('DD/MM/YYYY');
            },
            toStringDuratioType(value) {
                return durationTypes.filter(dt => dt.value === value)[0].text;
            },
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
                ContractService.remove(id)
                    .then(response => {
                        this.$swal({icon: 'success', title: response.data.message})
                    })
                    .catch(error => this.$swal({icon: 'error', title: error.response.data.message}))
                    .finally(() => ContractService.list().then(response => this.listResult = response.data));
            },
            show(id) {
                this.contractIdToShow = id;
                const requests = [
                    ContractService.load(id),
                    AddtiveService.loadByContract(id),
                ];

                axios.all(requests)
                    .then(result => {
                        this.contractToShow = result[0].data;

                        const additives = result[1].data.map(item => ({
                            signatureDate: moment(String(item.signatureDate), "YYYY-MM-DD HH:mm:ss").format('DD/MM/YYYY'),
                            duration: item.duration,
                            signatureEndDate: moment(String(item.signatureEndDate), "YYYY-MM-DD HH:mm:ss").format('DD/MM/YYYY'),
                            paymentValue: this.formatCurrency(item.paymentValue)
                        }));
                        this.contractToShow.additives.push(...additives);

                    })
                    .catch(error => this.$swal({icon: 'error', title: error.response.data.message}));
            },
            formatCurrency(value) {
                let formatter = new Intl.NumberFormat('pt-BR', {
                    style: 'currency',
                    currency: 'BRL',
                });
                return formatter.format(value);
            }
        }
    }
</script>

<style scoped>
    .card {
        padding: 0px !important;
        margin-bottom: 10px;
    }

    .header-title {
        font-size: 25px !important;
        text-align: center;
    }
</style>