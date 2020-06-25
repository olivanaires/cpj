import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import NotFound from '../components/NotFound.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/home',
        component: Home
    },
    {
        path: '/login',
        component: Login
    },
    {
        path: '/userRegister',
        name: 'userRegister',
        component: () => import('../components/register/User.vue')
    },
    {
        path: '/userList',
        name: 'userList',
        component: () => import('../components/list/User.vue')
    },
    {
        path: '/clientRegister',
        name: 'clientRegister',
        component: () => import('../components/register/Client.vue')
    },
    {
        path: '/clientList',
        name: 'clientList',
        component: () => import('../components/list/Client.vue')
    },
    {
        path: '/profile',
        name: 'profile',
        component: () => import('../views/Profile.vue')
    },
    {
        path: '*',
        component: NotFound
    }
]

const router = new VueRouter({
    mode: 'history',
    routes
});

export default router

router.beforeEach((to, from, next) => {
    const publicPages = ['/login'];
    const authRequired = !publicPages.includes(to.path);
    const loggedIn = localStorage.getItem('user');

    if (authRequired && !loggedIn) {
        next('/login');
    } else {
        next();
    }
});