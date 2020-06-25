<template>
    <b-row align-h="center">
        <b-card :header="title" header-class="header-title" class="col-md-12">
            <b-card-body>

                <b-card header="Filtros" header-class="header-title" class="col-md-12">
                    <b-input type="search" class="filtro" @input="filter = $event"
                             placeholder="Filtre por parte do nome"/>
                </b-card>

                <b-table striped hover bordered
                         :items="filteredCustomers" :fields="fields">
                    <template v-slot:cell(index)="data">
                        {{ data.index + 1 }}
                    </template>
                    <template v-slot:cell(document)="data">
                        {{ data.item.cpf ? data.item.cpf : data.item.cnpj }}
                    </template>
                    <template v-slot:cell(contactPhone)="data">
                        {{ data.item.contactPhoneOne ? data.item.contactPhoneOne : data.item.contactPhoneTwo }}
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
                customers: Array,
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
                        key: 'document',
                        label: 'Documento',
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'contactPhone',
                        label: 'Contato',
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
        }
    }
</script>

<style scoped>
    .card {
        padding: 0px !important;
        margin-bottom: 10px;
    }
</style>