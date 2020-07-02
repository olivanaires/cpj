import Vue from 'vue';

export default function authHeader() {
        let token = Vue.$cookies.get('token');
    if (token) {
        return { Authorization: token };
    } else {
        return {};
    }
}