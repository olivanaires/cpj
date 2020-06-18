<template>
    <div>
        <b-form-row>
            <c-input-text label-value="Logradoureo" v-model="value.street" roles-value="required"
                          bs-col-value="col-md-10" :required="true"/>
            <c-input-text label-value="Número" v-model="value.number" roles-value="required"
                          bs-col-value="col-md-2" :required="true"/>
        </b-form-row>
        <b-form-row>
<!--            <c-input-text label-value="Bairro" v-model="value.street" roles-value="required"-->
<!--                          bs-col-value="col-md-4" :required="true"/>-->
<!--            <c-input-text label-value="Complemento" v-model="value.street" roles-value="required"-->
<!--                          bs-col-value="col-md-8" :required="true"/>-->
        </b-form-row>
        <b-form-row>
            <c-input-mask label-value="CEP" v-model="value.postalCode"
                          mask-value="###.###.###-##" bs-col-value="col-md-4" :required="true"/>
<!--            <c-input-text label-value="Cidade" v-model="value.city" roles-value="required"-->
<!--                          bs-col-value="col-md-4" :required="true"/>-->
<!--            <c-input-text label-value="Estado" v-model="value.street" roles-value="required"-->
<!--                          bs-col-value="col-md-4"/>-->
        </b-form-row>
    </div>
</template>

<script>
    export default {
        name: 'c-address',
        props: {
            value: {
                type: Object,
                required: true
            }
        },
        watch: {
            value() {
                this.$emit('input', this.value);
            }
        }
    }
</script>

<style scoped>

</style>