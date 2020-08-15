<template>
    <div>
        <b-form-row>
            <div class="col-md-3">
                <ValidationProvider name="CEP" rules="required|min:9" v-slot="{ errors }">
                    <b-form-group label-for="cep_address" label="CEP *">
                        <b-form-input id="cep_address" v-model="value.postalCode" type="text" v-mask="'#####-###'"
                                      required @focusout="findAddress"/>
                        <span class="c-erro-msg">{{ errors[0] }}</span>
                    </b-form-group>
                </ValidationProvider>
            </div>
        </b-form-row>
        <b-form-row>
            <c-input-text label-value="Logradouro" v-model="value.street" roles-value="required"
                          bs-col-value="col-md-10" :required="true" :capitalize="true"/>
            <c-input-text label-value="Número" v-model="value.number" roles-value="required"
                          bs-col-value="col-md-2" :required="true"/>
        </b-form-row>
        <b-form-row>
            <c-input-text label-value="Bairro" v-model="value.neighborhood" roles-value="required"
                          bs-col-value="col-md-4" :required="true" :capitalize="true"/>
            <c-input-text label-value="Cidade" v-model="value.city" roles-value="required"
                          bs-col-value="col-md-4" :required="true" :capitalize="true"/>
            <c-input-text label-value="Complemento" v-model="value.complement" :capitalize="true"
                          bs-col-value="col-md-4"/>
        </b-form-row>
    </div>
</template>

<script>
    import AddressService from '../../services/address.service'

    export default {
        name: 'c-address',
        props: {
            value: {
                street: '',
                neighborhood: '',
                city: ''
            }
        },
        methods: {
            findAddress() {
                if (this.value.postalCode.length === 9) {
                    AddressService.getAddressByCep(this.value.postalCode.replace("-", "")).then(
                        result => {
                            if (!result.data['erro']) {
                                this.value.street = result.data['logradouro'];
                                this.value.neighborhood = result.data['bairro'];
                                this.value.city = result.data['localidade'] + " - " + result.data['uf'];
                            } else {
                                this.value.street = '';
                                this.value.neighborhood = '';
                                this.value.city = '';
                            }
                        },
                        error => {
                            this.$swal({icon: 'error', title: error.response.data.message})
                        });
                }
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
    span.c-erro-msg {
        color: #cc0033;
        display: inline-block;
        font-size: 12px;
        line-height: 15px;
        margin: 0;
    }
</style>