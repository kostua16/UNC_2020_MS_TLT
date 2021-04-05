import AuthService from '@/services/auth/auth-service'
import ParseLocalStorage from "@/services/auth/parse-local-storage";

const user = ParseLocalStorage();
const initialState = user
    ? {status: {loggedIn: true}, user}
    : {status: {loggedIn: false}, user: null};

const auth = {
    namespaced: true,
    state: initialState,
    mutations: {
        LOGIN_SUCCESS(state, user) {
            state.status.loggedIn = true;
            state.user = user;
        },
        LOGIN_FAILURE(state) {
            state.status.loggedIn = false;
            state.user = null;
        },
        LOGOUT(state) {
            state.status.loggedIn = false;
            state.user = null;
        },
    },

    actions: {
        LOGIN({commit}, user) {
            return AuthService.login(user).then(
                user => {
                    commit('LOGIN_SUCCESS', user);
                    return Promise.resolve(user);
                },
                error => {
                    commit('LOGIN_FAILURE');
                    return Promise.reject(error);
                }
            );
        },

        LOGOUT({commit}) {
            AuthService.logout();
            commit('LOGOUT');
        },
    },
};

export default auth;