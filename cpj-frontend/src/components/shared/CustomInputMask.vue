<template>
    <div :class="bsColValue">
        <ValidationProvider :name="labelValue" :rules="roleValues" v-slot="{ errors }">
            <b-form-group :label-for="idValue" :label="label">
                <b-form-input :id="idValue" v-model="localValue" type="text" v-mask="maskValue" :disabled="disabled" v-on:change="chamaMetodo"/>
                <span class="c-erro-msg">{{ errors[0] }}</span>
            </b-form-group>
        </ValidationProvider>
    </div>
</template>

<script>
    export default {
        name: 'c-input-mask',
        props: {
            value: {required: true, default: ''},
            maskValue: {type: String, required: true},
            labelValue: {type: String, required: true},
            bsColValue: {type: String, default: "col-md-12"},
            disabled: {type: Boolean, default: false},
            required: {type: Boolean, default: false}
        },
        computed: {
            roleValues() {
                return {required: this.required, length: this.maskValue.length}
            },
            idValue() {
                return this.labelValue + '_' + Math.random().toString(36).substr(2, 9);
            },
            label() {
                return this.required ? this.labelValue + ' *' : this.labelValue;
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
        methods: {
            chamaMetodo() {
                console.log("muddeded")
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