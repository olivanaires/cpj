import Vue from 'vue'
import App from './App.vue'
import router from './router'
import BootstrapVue from 'bootstrap-vue'
import BootstrapVueIcons from "bootstrap-vue/dist/bootstrap-vue-icons.esm";
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import store from './store'
import {ValidationObserver, ValidationProvider, extend, localize} from 'vee-validate';
import pt from 'vee-validate/dist/locale/pt_BR.json';
import * as rules from 'vee-validate/dist/rules';
import { VueMaskDirective } from 'v-mask'
import CustomInputText from "./components/shared/CustomInputText";
import CustomInputMask from "./components/shared/CustomInputMask";
import CustomInputSelect from "./components/shared/CustomInputSelect";
import ComponentAddress from "./components/shared/ComponentAddress";
import {library} from '@fortawesome/fontawesome-svg-core';
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome';
import {
    faHome,
    faUser,
    faUserPlus,
    faSignInAlt,
    faSignOutAlt
} from '@fortawesome/free-solid-svg-icons';

library.add(faHome, faUser, faUserPlus, faSignInAlt, faSignOutAlt);
Object.keys(rules).forEach(rule => extend(rule, rules[rule]));

localize('pt_BR', pt);

Vue.component('ValidationObserver', ValidationObserver);
Vue.component('ValidationProvider', ValidationProvider);
Vue.component('font-awesome-icon', FontAwesomeIcon);

Vue.component('c-input-text', CustomInputText);
Vue.component('c-input-mask', CustomInputMask);
Vue.component('c-input-select', CustomInputSelect);
Vue.component('c-address', ComponentAddress);

Vue.directive('mask', VueMaskDirective);

Vue.config.productionTip = false

Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
