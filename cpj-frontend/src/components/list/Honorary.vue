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

                <b-table striped hover bordered :items="filteredHonorarys" :fields="fields">
                    <template v-slot:cell(index)="data">
                        {{ data.index + 1 }}
                    </template>
                    <template v-slot:cell(contract)="data">
                        {{ data.item.contract.number }}
                    </template>
                    <template v-slot:cell(options)="data">
                        <b-link :to="`/honoraryUpdate/${data.item.id}`" v-if="!data.item.payed" class="option-item"
                                v-b-tooltip.hover title="Editar">
                            <b-icon icon="pencil"></b-icon>
                        </b-link>
                        <b-link v-on:click="remove(data.item.id)" class="option-item"
                                v-b-tooltip.hover title="Apagar">
                            <b-icon icon="trash"></b-icon>
                        </b-link>
                    </template>
                </b-table>
            </b-card-body>
        </b-card>
    </b-row>
</template>

<script>
    import moment from 'moment';
    import HonoraryService from '../../services/honorary.service';
    import ContractService from '../../services/contract.service';

    export default {
        name: 'honoraryList',
        data() {
            return {
                title: 'Listagem de Honorários',
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
                        label: 'Data Pagamento',
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
                        key: 'parcels',
                        label: 'Parcelas',
                        sortable: true,
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'payed',
                        label: 'Pago?',
                        sortable: true,
                        thClass: 'bg-dark text-white',
                        formatter: "formatBoolean"
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
            HonoraryService.list().then(
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
            filteredHonorarys() {
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
            formatBoolean(value) {
                return value ? 'Sim' : 'Não';
            },
            formatCurrency(value) {
                let formatter = new Intl.NumberFormat('pt-BR', {
                    style: 'currency',
                    currency: 'BRL',
                });
                return formatter.format(value);
            },
            remove(id) {
                HonoraryService.remove(id)
                    .then(response => {
                        this.$swal({icon: 'success', title: response.data.message})
                    })
                    .catch(error => this.$swal({icon: 'error', title: error.response.data.message}))
                    .finally(() => HonoraryService.list().then(response => this.list = response.data));
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