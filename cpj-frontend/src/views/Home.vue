<template>
    <div class="container">
        <main>
            <section class="container flex">
                <b-card :header="thisMonthEndDate" header-class="header-title">
                    <b-card-body>
                        <c-contract-end-date-this-month></c-contract-end-date-this-month>
                    </b-card-body>
                </b-card>
                <b-card :header="thisMonthPayments" header-class="header-title">
                    <b-card-body>
                        <c-contract-payments-this-month></c-contract-payments-this-month>
                    </b-card-body>
                </b-card>
            </section>
            <br/>
            <section class="container flex">
                <b-card :header="thisMonthHonoraries" header-class="header-title">
                    <b-card-body>
                        <c-contract-honoraries-this-month></c-contract-honoraries-this-month>
                    </b-card-body>
                </b-card>
                <b-card :header="thisMonthExpenses" header-class="header-title">
                    <b-card-body>
                        <c-contract-expenses-this-month>    </c-contract-expenses-this-month>
                    </b-card-body>
                </b-card>
            </section>
            <br/>
            <section class="container flex">
                <b-card :header="thisMonthOthersPayments" header-class="header-title">
                    <b-card-body>
                        Para Implementar
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
    import CContractHonorariesThisMonth from "../components/report/ContractHonoraryThisMonth";
    import CContractExpensesThisMonth from "../components/report/ContractExpenseThisMonth";

    export default {
        name: 'Home',
        components: {
            CContractExpensesThisMonth,
            CContractHonorariesThisMonth, CContractEndDateThisMonth, CContractPaymentsThisMonth},
        data() {
            return {
                thisMonthPayments: 'Pagamento de Contratos',
                thisMonthEndDate: 'Contratos a Vencer',
                thisMonthHonoraries: 'Pagamento de Honorários',
                thisMonthExpenses: 'Pagamento de Despesas',
                thisMonthOthersPayments: 'Outros Pagamentos',
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