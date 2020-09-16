<template>
    <div>
        <b-pagination v-model="currentPage" :total-rows="rows"
                      :per-page="perPage" aria-controls="my-table">
        </b-pagination>

        <b-table id="my-table" striped hover show-empty
                 empty-text="Não existe pagamentos a receber esse mês"
                 :items="contracts"
                 :fields="fields"
                 :per-page="perPage"
                 :current-page="currentPage"
                 small>
            <template v-slot:cell(number)="data">
                <b-link v-b-modal.show-contract v-on:click="show(data.item.id)" class="option-item"
                        v-b-tooltip.hover title="Visualizar">
                    {{ data.item.number }}
                </b-link>
            </template>
            <template v-slot:cell(duration)="data">
                {{ data.item.duration + " " + toStringDuratioType(data.item.durationType) }}
            </template>
        </b-table>

        <b-modal id="show-contract" title="Visualizar Contrato" centered size="xl" scrollable hide-footer @hidden="closeModal" >
            <c-contract-show :contract="contractToShow" :contract-id="contractIdToShow"/>
        </b-modal>
    </div>
</template>

<script>
    import ContractService from '../../services/contract.service';
    import durationTypes from '../../models/durationType';
    import moment from 'moment';
    import Contract from "../../models/contract";
    import CContractShow from "../show/Contract";

    export default {
        name: 'c-contract-payments-this-month',
        components: {CContractShow},
        data() {
            return {
                perPage: 5,
                currentPage: 1,
                contracts: [],
                contractToShow: new Contract(),
                contractIdToShow: null,
                fields: [
                    {
                        key: 'number',
                        label: 'Número',
                        sortable: true,
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'paymentDate',
                        label: 'Data Pagamento',
                        sortable: true,
                        thClass: 'bg-dark text-white',
                        formatter: "formatDate"
                    },
                    {
                        key: 'paymentValue',
                        label: 'Valor',
                        sortable: true,
                        thClass: 'bg-dark text-white',
                        formatter: "formatValue"
                    }
                ]
            }
        },
        created() {
            ContractService.paymentsThisMonth()
                .then(response => {
                    this.contracts = response.data;
                })
        },
        methods: {
            formatDate(date) {
                return moment(String(date), "YYYY-MM-DD HH:mm:ss").set('month', moment().month()).format('DD/MM/YYYY');
            },
            formatValue(value) {
                return value.toLocaleString('pt-BR', {style: 'currency', currency: 'BRL'});
            },
            toStringDuratioType(value) {
                return durationTypes.filter(dt => dt.value === value)[0].text;
            },
            show(id) {
                this.contractIdToShow = id;
                ContractService.load(id).then(response => {
                    this.contractToShow = response.data;
                })
                    .catch(error => this.$swal({icon: 'error', title: error.response.data.message}));
            },
            closeModal() {
                ContractService.paymentsThisMonth()
                    .then(response => {
                        this.contracts = response.data;
                    })
            }
        },
        computed: {
            rows() {
                return this.contracts.length
            }
        }
    }
</script>