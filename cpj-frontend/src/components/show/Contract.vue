<template>
    <b-row align-h="center">
        <b-card class="col-md-12">
            <b-card-body>
                <b-row>
                    <c-input-text v-model="contract.number" label-value="Número" bs-col-value="col-md-3"
                                  :disabled="true"/>
                    <c-input-text v-model="description" label-value="Descrição" bs-col-value="col-md-3"
                                  :disabled="true"/>
                    <c-input-date v-model="contract.signatureDate" label-value="Data Assinatura" bs-col-value="col-md-2"
                                  :disabled="true"/>
                    <c-input-text v-model="contract.duration" input-type="number" label-value="Duração Meses"
                                  bs-col-value="col-md-2" :disabled="true"/>
                    <c-input-date v-model="contract.endDate" label-value="Data Final" bs-col-value="col-md-2"
                                  :disabled="true"/>
                </b-row>

                <b-row>
                    <c-input-text v-model="paymentType" label-value="Tipo Pagamento" bs-col-value="col-md-3"
                                  :disabled="true"/>
                    <b-form-group label="Valor" class=" col-md-3">
                        <money v-model="contract.paymentValue" class="form-control" :disabled="true"></money>
                    </b-form-group>
                    <b-form-group label="Entrada" class=" col-md-3">
                        <money v-model="contract.entryValue" class="form-control" :disabled="true"></money>
                    </b-form-group>
                    <c-input-date v-model="contract.paymentDate" label-value="Data Pagamento" bs-col-value="col-md-3"
                                  :disabled="true"/>
                </b-row>

                <b-row>
                    <b-form-group label="Cliente(s)" class="col-md-6">
                        <b-table :fields="clientFields" :items="contract.contractors"></b-table>
                    </b-form-group>
                    <b-form-group label="Adivodado(s)" class="col-md-6">
                        <b-table :fields="lawyerFields" :items="contract.hired"></b-table>
                    </b-form-group>
                </b-row>

                <b-row>
                    <b-form-group label="Observações" class="col-md-12">
                        <b-textarea v-model="contract.observations" rows="3" :disabled="true"></b-textarea>
                    </b-form-group>
                </b-row>

            </b-card-body>
        </b-card>
    </b-row>
</template>

<script>
    import Contract from "../../models/contract";
    import paymentTypes from '../../models/paymentType';

    export default {
        name: 'c-contract-show',
        props: {
            contract: {required: true, default: new Contract()}
        },
        data() {
            return {
                paymentType: '',
                description: '',
                clientFields: [
                    {
                        key: 'cpfCnpj',
                        label: 'CPF/CNPJ',
                        thClass: 'teste'
                    },
                    {
                        key: 'clientName',
                        label: 'Nome',
                        thClass: 'teste'
                    },
                ],
                lawyerFields: [
                    {
                        key: 'oabNumber',
                        label: 'OAB',
                        thClass: 'teste'
                    },
                    {
                        key: 'name',
                        label: 'Nome',
                        thClass: 'teste'
                    },
                ]
            }
        },
        created() {
            if (this.contract) {
                this.description = this.contract.description === 'HONORARY' ? 'Honorário' : 'Acessoria';
                this.paymentType = paymentTypes.find(pt => pt.item === this.contract.paymentType).name;
            }
        }
    }

</script>

<style scoped>

</style>