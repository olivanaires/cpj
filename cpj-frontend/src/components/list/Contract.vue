<template>
    <b-row align-h="center">
        <b-card :header="title" header-class="header-title" class="col-md-12">
            <b-card-body>
                <b-card header="Filtros" class="col-md-12">
                    <b-input type="search" class="filtro" @input="filter = $event"
                             placeholder="Filtre por parte do número"/>
                </b-card>

                <b-table striped hover bordered :items="filteredLawyers" :fields="fields">
                    <template v-slot:cell(index)="data">
                        {{ data.index + 1 }}
                    </template>
                    <template v-slot:cell(duration)="data">
                        {{ data.item.duration + " " + toStringDuratioType(data.item.durationType) }}
                    </template>
                    <template v-slot:cell(options)="data">
                        <b-link :to="`/contractUpdate/${data.item.id}`">
                            <b-icon icon="pencil"></b-icon>
                        </b-link>
                    </template>
                </b-table>
            </b-card-body>
        </b-card>
    </b-row>
</template>

<script>
    import moment from 'moment';
    import ContractService from '../../services/contract.service';
    import durationTypes from '../../models/durationType';

    export default {
        name: 'contractList',
        data() {
            return {
                title: 'Listagem de Contratos',
                filter: '',
                listResult: [],
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
            ContractService.list().then(
                response => {
                    this.listResult = response.data;
                });
        },
        computed: {
            filteredLawyers() {
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