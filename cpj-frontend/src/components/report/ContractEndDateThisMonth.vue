<template>
    <div>
        <b-pagination v-model="currentPage" :total-rows="rows"
                      :per-page="perPage" aria-controls="my-table">
        </b-pagination>

        <b-table id="my-table" striped hover show-empty
                 empty-text="Não existe contratos a vencer esse mês"
                 :items="contracts"
                 :fields="fields"
                 :per-page="perPage"
                 :current-page="currentPage"
                 small>
            <template v-slot:cell(duration)="data">
                {{ data.item.duration + " " + toStringDuratioType(data.item.durationType) }}
            </template>
        </b-table>
    </div>
</template>

<script>
    import ContractService from '../../services/contract.service';
    import durationTypes from '../../models/durationType';
    import moment from 'moment';

    export default {
        name: 'c-contract-end-date-this-month',
        data() {
            return {
                perPage: 5,
                currentPage: 1,
                contracts: [],
                fields: [
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
                        formatter: "formatDate"
                    },
                    {
                        key: 'endDate',
                        label: 'Data Final',
                        sortable: true,
                        thClass: 'bg-dark text-white',
                        formatter: "formatDate"
                    },

                ]
            }
        },
        created() {
            ContractService.endDateThisMonth()
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
        },
        computed: {
            rows() {
                return this.contracts.length
            }
        }
    }
</script>