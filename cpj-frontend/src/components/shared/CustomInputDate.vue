<template>
    <div :class="bsColValue">
        <ValidationProvider :name="labelValue" :rules="rolesValue" v-slot="{ errors }">
            <b-form-group :label-for="idValue" :label="label">
                <b-datepicker :id="idValue" v-model="localValue"
                              today-button
                              reset-button
                              :disabled="disabled"
                              :value-as-date="true"
                              :date-format-options="{day: '2-digit', month: '2-digit', year: 'numeric'}"
                              label-close-button="Fechar"
                              label-today="Hoje"
                              label-today-button="Hoje"
                              label-reset-button="Limpar"
                              label-current-month="Mês Atual"
                              label-next-month="Prox. Mês" label-prev-month="Mês Anter."
                              label-next-year="Prox. Ano" label-prev-year="Ano Anter."
                              label-no-date-selected="Data não selecionada"
                              label-help="Use setas para navegar"
                              placeholder="Selecionar data"/>
                <input v-model="localValue" hidden/>
                <span class="c-erro-msg">{{ errors[0] }}</span>
            </b-form-group>
        </ValidationProvider>
    </div>
</template>

<script>
    export default {
        name: 'c-input-date',
        props: {
            value: {required: true, default: Date},
            labelValue: {type: String, required: true},
            bsColValue: {type: String, default: "col-md-12"},
            disabled: {type: Boolean, default: false},
            rolesValue: String
        },
        computed: {
            idValue() {
                return this.labelValue + '_' + Math.random().toString(36).substr(2, 9);
            },
            label() {
                return this.rolesValue && this.rolesValue.indexOf('required') >= 0 ? this.labelValue + ' *' : this.labelValue;
            },
            localValue: {
                get() {
                    return this.value
                },
                set(localValue) {
                    this.$emit('input', localValue)
                }
            }
        },
        watch: {
            localValue: {
                handler(newVal) {
                    this.$emit('input', newVal)
                },
                deep: true
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