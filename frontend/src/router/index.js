import {createRouter, createWebHistory} from 'vue-router'
import Login from '../components/Login.vue'
import Register from '../components/Register.vue'
import Home from "../components/Home.vue";
import Authenticate from "../components/Authenticate.vue";
import {getToken} from '../services/localStorageService';

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
        meta: {requiresAuth: true}
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path: '/authenticate',
        name: 'Authenticate',
        component: Authenticate,
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// Navigation guard
router.beforeEach((to, from, next) => {
    const token = getToken();

    if (to.meta.requiresAuth && !token) {
        next('/login');
    } else if (token && to.path === '/login') {
        next('/');
    } else {
        next();
    }
});

export default router 