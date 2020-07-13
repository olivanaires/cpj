<template>
    <div :class="bsColValue">
        <ValidationProvider :name="labelValue" :rules="rolesValue" v-slot="{ errors }">
            <b-form-group :label-for="idValue" :label="label">
                <b-form-input :id="idValue" v-model="localValue" :type="inputType"
                              :disabled="disabled" v-capitalize="capitalize"/>
                <span class="c-erro-msg">{{ errors[0] }}</span>
            </b-form-group>
        </ValidationProvider>
    </div>
</template>

<script>
    export default {
        name: 'c-input-text',
        props: {
            value: {required: true, default: ''},
            labelValue: {type: String, required: true},
            bsColValue: {type: String, default: "col-md-12"},
            disabled: {type: Boolean, default: false},
            inputType: {type: String, default: "text"},
            rolesValue: String,
            capitalize: {
                default: false
            }
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