<template>
    <div class="container">
        <main>
            <b-row>
                <b-card :header="thisMonthPayments" header-class="header-title" class="col-md-6">
                    <b-card-body>
                        <c-contract-payments-this-month></c-contract-payments-this-month>
                    </b-card-body>
                </b-card>
            </b-row>
        </main>
    </div>
</template>

<script>
    import UserService from '../services/user.service';
    import CContractPaymentsThisMonth from "../components/report/ContractPaymentsThisMonth";

    export default {
        name: 'Home',
        components: {CContractPaymentsThisMonth},
        data() {
            return {
                thisMonthPayments: 'Pagamentos Mês Atual',
                content: '',
            };
        },
        mounted() {
            UserService.getPublicContent().then(
                response => {
                    this.content = response.data;
                },
                error => {
                    this.content =
                        (error.response && error.response.data) ? error.response.data.message : error.message;
                }
            );
        }
    };
</script>

<style>
    .card {
        padding: 0px !important;
        margin-bottom: 10px;
    }

    .header-title {
        font-size: 25px !important;
        text-align: center;
    }
</style>