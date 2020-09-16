<template>
    <div class="container">
        <main>
            <section class="container flex">
                <b-card :header="thisMonthPayments" header-class="header-title">
                    <b-card-body>
                        <c-contract-payments-this-month></c-contract-payments-this-month>
                    </b-card-body>
                </b-card>
                <b-card :header="thisMonthEndDate" header-class="header-title">
                    <b-card-body>
                        <c-contract-end-date-this-month></c-contract-end-date-this-month>
                    </b-card-body>
                </b-card>
            </section>
        </main>
    </div>
</template>

<script>
    import UserService from '../services/user.service';
    import CContractPaymentsThisMonth from "../components/report/ContractPaymentsThisMonth";
    import CContractEndDateThisMonth from "../components/report/ContractEndDateThisMonth";

    export default {
        name: 'Home',
        components: {CContractEndDateThisMonth, CContractPaymentsThisMonth},
        data() {
            return {
                thisMonthPayments: 'Pagamentos a Receber',
                thisMonthEndDate: 'Contratos a Vencer',
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

<style scoped>
    .flex {
        display: flex;
        justify-content: space-between;
    }

    .card {
        padding: 0px !important;
        width: 49%;
    }

    .header-title {
        font-size: 25px !important;
        text-align: center;
    }
</style>