<template>
    <b-card :header="title" header-class="header-title">
        <b-card-body>
            <ValidationObserver v-slot="{ invalid }">
                <b-form @submit.prevent="handleRegister">
                    <b-form-row>
                        <c-input-select label-value="Tipo" v-model="client.clientType" roles-value="required"
                                        :option-values="clientTypeOptions" bs-col-value="col-md-2"/>
                        <c-input-mask v-if="client.clientType === 'PF'" label-value="CPF" v-model="client.cpf"
                                      mask-value="###.###.###-##" bs-col-value="col-md-3" :required="true"/>
                        <c-input-mask v-if="client.clientType === 'PJ'" label-value="CNPJ" v-model="client.cnpj"
                                      mask-value="###.###.##/####-##" bs-col-value="col-md-3" :required="true"/>
                        <c-input-text label-value="Nome Cliente" v-model="client.clientName" roles-value="required"
                                      bs-col-value="col-md-7"/>
                    </b-form-row>

                    <b-form-row>
                        <c-input-text label-value="Número RG" v-model="client.identityNumber" roles-value="required"
                                      bs-col-value="col-md-4"/>
                        <c-input-select label-value="Situação Matrimonial" v-model="client.maritalStatus"
                                        roles-value="required" :option-values="maritalStatus" bs-col-value="col-md-4"/>
                        <c-input-text label-value="Cidadania" v-model="client.citizenship" roles-value="required"
                                      bs-col-value="col-md-4"/>
                    </b-form-row>

                    <b-form-row>
                        <c-input-text label-value="Nome Contato" v-model="client.contactName" bs-col-value="col-md-8"/>
                        <c-input-mask label-value="Celular" v-model="client.contactPhoneOne"
                                      mask-value="(##) #####-####" bs-col-value="col-md-2"/>
                        <c-input-mask label-value="Telefone Fixo" v-model="client.contactPhoneTwo"
                                      mask-value="(##) ####-####" bs-col-value="col-md-2"/>
                    </b-form-row>

                    <c-address v-model="client.address"></c-address>

                    <b-row align-h="center">
                        <b-button class="col-md-2" type="submit" :disabled="invalid" variant="success">Salvar</b-button>
                    </b-row>
                </b-form>
            </ValidationObserver>
        </b-card-body>
    </b-card>
</template>

<script>
    import ClientService from '../../services/client.service'
    import Client from '../../models/client';
    import Address from "../../models/address";

    export default {
        data() {
            return {
                title: 'Cadastrar Cliente',
                client: new Client(),
                clientTypeOptions: [
                    {item: 'PF', name: 'Pessoa Física'},
                    {item: 'PJ', name: 'Pessoa jurídica'}
                ],
                maritalStatus: [
                    {item: 'SINGLE', name: 'Solteiro (a)'},
                    {item: 'MERRIED', name: 'Casado (a)'},
                    {item: 'WIDOWED', name: 'Viúvo (a)'},
                    {item: 'DIVORCED', name: 'Divorciado (a)'}
                ]
            };
        },
        created() {
            this.client.clientType = 'PF';
            this.client.maritalStatus = 'SINGLE';
            this.client.address = new Address();
        },
        methods: {
            handleRegister() {
                ClientService.create(this.client).then(
                    data => {
                        console.log(data)
                    },
                    error => {
                        console.log(error.response.data)
                    });

            }
        }
    }
</script>

<style scoped>
    .header-title {
        font-size: 25px !important;
        text-align: center;
    }
</style>