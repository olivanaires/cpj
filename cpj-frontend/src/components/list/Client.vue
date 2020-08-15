<template>
    <b-row align-h="center">
        <b-card :header="title" header-class="header-title" class="col-md-12">
            <b-card-body>

                <b-card header="Filtros" class="col-md-12">
                    <b-input type="search" class="filtro" @input="filter = $event"
                             placeholder="Filtre por parte do nome"/>
                </b-card>

                <b-table striped hover bordered
                         :items="filteredCustomers" :fields="fields">
                    <template v-slot:cell(index)="data">
                        {{ data.index + 1 }}
                    </template>
                    <template v-slot:cell(contactPhone)="data">
                        {{ data.item.contactPhoneOne ? data.item.contactPhoneOne : data.item.contactPhoneTwo }}
                    </template>
                    <template v-slot:cell(options)="data">
                        <b-link :to="`/clientUpdate/${data.item.id}`"
                                v-b-tooltip.hover title="Editar">
                            <b-icon icon="pencil"></b-icon>
                        </b-link>
                        <b-link v-on:click="remove(data.item.id)"
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
    import ClientService from '../../services/client.service';

    export default {
        name: 'clientList',
        data() {
            return {
                title: 'Listagem de Clientes',
                filter: '',
                customers: [],
                fields: [
                    {
                        key: 'index',
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'clientName',
                        label: 'Nome',
                        sortable: true,
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'clientType',
                        label: 'Tipo',
                        sortable: true,
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'cpfCnpj',
                        label: 'CPF/CNPJ',
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'contactPhone',
                        label: 'Contato',
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
            ClientService.list().then(
                response => {
                    this.customers = response.data;
                });
        },
        computed: {
            filteredCustomers() {
                if (this.filter) {
                    let exp = new RegExp(this.filter.trim(), 'i');
                    return this.customers.filter(c => exp.test(c.clientName));
                } else {
                    return this.customers;
                }
            }
        },
        methods: {
            remove(id) {
                ClientService.remove(id)
                    .then(response => this.$swal({icon: 'success', title: response.data.message}))
                    .catch(error => this.$swal({icon: 'error', title: error.response.data.message}))
                    .finally(() => ClientService.list().then( response => this.customers = response.data ));
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