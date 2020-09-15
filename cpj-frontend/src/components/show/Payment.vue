<template>
    <b-row align-h="center">
        <b-card class="col-md-12">
            <b-card-body>
                <b-row>
                    <b-form-group label="Pagamentos Contrato" class="col-md-12">
                        <b-table :fields="paymentFields" :items="payments"
                                 show-empty empty-text="Sem aditivos">

                            <template v-slot:cell(date)="data">
                                {{ formatDateValue(data.item.date) }}
                            </template>

                            <template v-slot:cell(paymentValue)="data">
                                {{ formatCurrency(data.item.paymentValue) }}
                            </template>

                            <template v-slot:cell(payed)="data">
                                {{ data.item.payed ? 'Sim' : 'Não'}}
                            </template>

                            <template v-slot:cell(options)="data" >
                                <b-link v-on:click="pay(data.item)" v-if="!data.item.payed" class="option-item"
                                        v-b-tooltip.hover title="Abrir PDF">
                                    <b-icon icon="check-square"></b-icon>
                                </b-link>
                            </template>
                        </b-table>
                    </b-form-group>
                </b-row>
            </b-card-body>
        </b-card>
    </b-row>
</template>

<script>
    import moment from 'moment';

    export default {
        name: 'c-payment-show',
        props: {
            payments: {required: true},
        },
        data() {
            return {
                paymentFields: [
                    {
                        key: 'description',
                        label: 'Descrição',
                    },
                    {
                        key: 'date',
                        label: 'Data',
                    },
                    {
                        key: 'paymentValue',
                        label: 'Valor',
                    },
                    {
                        key: 'payed',
                        label: 'Pago?',
                    },
                    {
                        key: 'options',
                        label: 'Opções',
                        tdClass: 'center',
                        thClass: 'center'
                    }
                ],
            }
        },
        methods: {
            formatDateValue(value) {
                return moment(String(value), "YYYY-MM-DD HH:mm:ss").format('DD/MM/YYYY');
            },
            formatCurrency(value) {
                let formatter = new Intl.NumberFormat('pt-BR', {
                    style: 'currency',
                    currency: 'BRL',
                });
                return formatter.format(value);
            },
            pay(value) {
                console.log(value);
                this.$swal({icon: 'warning', title: 'Funcionalidade ainda não foi implementada.'});
            }
        }
    }

</script>