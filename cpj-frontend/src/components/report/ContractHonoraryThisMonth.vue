<template>
    <div>
        <b-pagination v-model="currentPage" :total-rows="rows"
                      :per-page="perPage" aria-controls="my-table">
        </b-pagination>

        <b-table id="my-table" striped hover show-empty
                 empty-text="Não existe honorários a vencer esse mês"
                 :items="honoraries"
                 :fields="fields"
                 :per-page="perPage"
                 :current-page="currentPage"
                 small>
        </b-table>
    </div>
</template>

<script>
    import HonoraryService from '../../services/honorary.service';
    import durationTypes from '../../models/durationType';
    import moment from 'moment';

    export default {
        name: 'c-contract-honoraries-this-month',
        data() {
            return {
                perPage: 5,
                currentPage: 1,
                honoraries: [],
                fields: [
                    {
                        key: 'contract.number',
                        label: 'Contrato',
                        sortable: true,
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'date',
                        label: 'Data',
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
                    },

                ]
            }
        },
        created() {
            HonoraryService.listThisMonth()
                .then(response => {
                    this.honoraries = response.data;
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
                return this.honoraries.length
            }
        }
    }
</script>