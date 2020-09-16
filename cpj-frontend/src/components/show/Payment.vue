<template>
    <b-row align-h="center">
        <b-card class="col-md-12">
            <b-card-body>
                <b-row>
                    <b-form-group label="" class="col-md-12">
                        <b-table :fields="paymentFields" :items="payments"
                                 show-empty empty-text="Sem pagamentos">

                            <template v-slot:cell(date)="data">
                                {{ formatDateValue(data.item.date) }}
                            </template>

                            <template v-slot:cell(paymentValue)="data">
                                {{ formatCurrency(data.item.paymentValue) }}
                            </template>

                            <template v-slot:cell(payed)="data">
                                {{ data.item.payed ? 'Sim' : 'Não'}}
                            </template>

                            <template v-slot:cell(options)="data">
                                <b-link v-on:click="pay(data.item)" v-if="!data.item.payed" class="option-item"
                                        v-b-tooltip.hover title="Receber Pagamento">
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
    import Payment from "../../models/payment";
    import ContractService from '../../services/contract.service';

    export default {
        name: 'c-payment-show',
        props: {
            payments: {required: true},
            contractId: {required: true},
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
            pay(payment) {
                const indexOf = this.payments.findIndex(item => item.description === payment.description
                                                             && item.paymentType === payment.paymentType
                                                             && item.date === payment.date);

                payment = new Payment(payment, this.contractId);
                ContractService.receivePayment(payment)
                    .then(response => {
                        this.payments[indexOf].payed = true;
                        this.$swal({icon: 'success', title: response.data.message})
                    })
                    .catch(error => this.$swal({icon: 'error', title: error.response.data.message}));
            }
        }
    }

</script>