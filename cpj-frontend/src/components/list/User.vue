<template>
    <b-row align-h="center">
        <b-card :header="title" header-class="header-title" class="col-md-12">
            <b-card-body>
                <b-card header="Filtros" class="col-md-12">
                    <b-input type="search" class="filtro" @input="filter = $event"
                             placeholder="Filtre por parte do usuário"/>
                </b-card>

                <b-table striped hover bordered :items="filteredUsers" :fields="fields">
                    <template v-slot:cell(index)="data">
                        {{ data.index + 1 }}
                    </template>
                    <template v-slot:cell(roles)="data">
                        {{ toStringRole(data.item.roles) }}
                    </template>
                    <template v-slot:cell(options)="data">
                        <b-link :to="`/userUpdate/${data.item.id}`" class="option-item"
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
    import UserService from '../../services/user.service';
    import role from '../../models/role'

    export default {
        name: 'userList',
        data() {
            return {
                title: 'Listagem de Usuários',
                filter: '',
                users: [],
                fields: [
                    {
                        key: 'index',
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'username',
                        label: 'Usuário',
                        sortable: true,
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'email',
                        label: 'E-Mail',
                        thClass: 'bg-dark text-white'
                    },
                    {
                        key: 'roles',
                        label: 'Função',
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
            UserService.list().then(
                response => {
                    this.users = response.data;
                });
        },
        computed: {
            filteredUsers() {
                if (this.filter) {
                    let exp = new RegExp(this.filter.trim(), 'i');
                    return this.users.filter(u => exp.test(u.username));
                } else {
                    return this.users;
                }
            }
        },
        methods: {
            toStringRole(roles) {
                if (roles) {
                    return role[roles[0].name];
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