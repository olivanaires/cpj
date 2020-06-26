<template>
    <b-row align-h="center">
        <b-card :header="title" header-class="header-title" class="col-md-12">
            <b-card-body>
                <b-card header="Filtros" class="col-md-12">
                    <b-input type="search" class="filtro" @input="filter = $event"
                             placeholder="Filtre por parte do nome"/>
                </b-card>

                <b-table striped hover bordered :items="filteredLawyers" :fields="fields">
                    <template v-slot:cell(index)="data">
                        {{ data.index + 1 }}
                    </template>
                </b-table>
            </b-card-body>
        </b-card>
    </b-row>
</template>

<script>
    import LawyerService from '../../services/lawyer.service';

    export default {
        name: 'lawyerList',
        data() {
            return {
                title: 'Listagem de Advogado',
                filter: '',
                lawyers: [],
                fields: [
                    {
                        key: 'index',
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'name',
                        label: 'Nome',
                        sortable: true,
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'oabNumber',
                        label: 'Nº OAB',
                        sortable: true,
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'user.username',
                        label: 'Usuário',
                        sortable: true,
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'user.email',
                        label: 'E-Mail',
                        thClass: 'bg-dark text-white'
                    }
                ]
            }
        },
        created() {
            LawyerService.list().then(
                response => {
                    this.lawyers = response.data;
                });
        },
        computed: {
            filteredLawyers() {
                if (this.filter) {
                    let exp = new RegExp(this.filter.trim(), 'i');
                    return this.lawyers.filter(l => exp.test(l.name));
                } else {
                    return this.lawyers;
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
    .header-title {
        font-size: 25px !important;
        text-align: center;
    }
</style>