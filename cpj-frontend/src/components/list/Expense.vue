<template>
    <b-row align-h="center">
        <b-card :header="title" header-class="header-title" class="col-md-12">
            <b-card-body>
                <b-card header="Filtros" class="col-md-12">
                    <b-form-group label="Descrição" class="col-md-12">
                        <b-input type="search" class="filtro" @input="filter = $event" />
                    </b-form-group>

                    <c-input-select label-value="Contrato" v-model="contractFilter" @change="contractFilter = $event"
                                    :option-values="contractOptions"/>
                </b-card>

                <b-table striped hover bordered :items="filteredExpenses" :fields="fields">
                    <template v-slot:cell(index)="data">
                        {{ data.index + 1 }}
                    </template>
                    <template v-slot:cell(contract)="data">
                        {{ data.item.contract.number + ' - ' + data.item.contract.description }}
                    </template>
                    <template v-slot:cell(options)="data">
                        <b-link :to="`/expenseUpdate/${data.item.id}`" class="option-item"
                                v-b-tooltip.hover title="Editar">
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
    import ExpenseService from '../../services/expense.service';
    import ContractService from '../../services/contract.service';

    export default {
        name: 'expenseList',
        data() {
            return {
                title: 'Listagem de Despesas',
                filter: '',
                contractFilter: '',
                list: [],
                contractOptions: [],
                fields: [
                    {
                        key: 'index',
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'description',
                        label: 'Descrição',
                        sortable: true,
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'date',
                        label: 'Data',
                        sortable: true,
                        thClass: 'bg-dark text-white',
                        formatter: "formatDateAssigned"
                    },
                    {
                        key: 'paymentValue',
                        label: 'Valor',
                        sortable: true,
                        thClass: 'bg-dark text-white',
                        formatter: "formatCurrency"
                    },
                    {
                        key: 'contract',
                        label: 'Contrato',
                        sortable: true,
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'options',
                        label: 'Opções',
                        thClass: 'center bg-dark text-white',
                        tdClass: 'center'
                    }
                ]
            }
        },
        created() {
            ExpenseService.list().then(
                response => {
                    this.list = response.data;
                });
            ContractService.list()
                .then(response => {
                    this.contractOptions.push({item: '', name: 'Selecione um filtro...'})
                    this.contractOptions = this.contractOptions.concat(
                        response.data.map(c => {
                            return {item: String(c.id), name: `${c.number} - ${c.description}`};
                        }));
                })
                .catch(error => this.$swal({icon: 'error', title: error.response.data.message}));
        },
        computed: {
            filteredExpenses() {
                let result = this.list;
                if (this.contractFilter !== '') {
                    result = result.filter(e => String(e.contract.id) === this.contractFilter);
                }

                if (this.filter !== '') {
                    console.log('passou')
                    let exp = new RegExp(this.filter.trim(), 'i');
                    result = result.filter(e => exp.test(e.description));
                }

                if (this.contractFilter === '' && this.filter === '') {
                    result = this.list;
                }
                return result;
            }
        },
        methods: {
            formatDateAssigned(value) {
                return moment(String(value), "YYYY-MM-DD HH:mm:ss").format('DD/MM/YYYY');
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